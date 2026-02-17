package dev.darcosse.silowmons_integrations.fabric.mixin;

import abeshutt.staracademy.data.adapter.Adapters;
import abeshutt.staracademy.data.bit.BitBuffer;
import abeshutt.staracademy.net.UpdateSafariS2CPacket;
import abeshutt.staracademy.world.data.save.SafariData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mixin(value = UpdateSafariS2CPacket.class, remap = false)
public abstract class MixinUpdateSafariS2CPacket {

    @Shadow private long timeLeft;
    @Shadow private boolean paused;
    @Shadow private Map<UUID, SafariData.Entry> entries;

    @Inject(method = "readBits", at = @At("HEAD"), cancellable = true)
    private void fixReadBits(BitBuffer buffer, CallbackInfo ci) {
        this.timeLeft = Adapters.LONG.readBits(buffer).orElseThrow();
        this.paused = Adapters.BOOLEAN.readBits(buffer).orElseThrow();

        if (Adapters.BOOLEAN.readBits(buffer).orElseThrow()) {
            this.entries = null;
        } else {
            this.entries = new HashMap<>();
            int size = Adapters.INT_SEGMENTED_3.readBits(buffer).orElseThrow();

            for (int i = 0; i < size; ++i) {
                UUID uuid = Adapters.UUID.readBits(buffer).orElseThrow();
                SafariData.Entry entry = new SafariData.Entry();
                entry.readBits(buffer);
                this.entries.put(uuid, entry);
            }
        }

        // Injection forcée pour le multijoueur
        if (this.entries != null) {
            SafariData.CLIENT.getEntries().putAll(this.entries);
        }
        SafariData.CLIENT.setTimeLeft(this.timeLeft);

        ci.cancel();
    }
}