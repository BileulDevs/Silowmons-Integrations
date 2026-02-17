package dev.darcosse.silowmons_integrations.fabric.mixin;

import abeshutt.staracademy.StarAcademyMod;
import abeshutt.staracademy.world.data.save.SafariData;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity {
    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        ClientPlayerEntity player = (ClientPlayerEntity)(Object)this;
        if (player.getWorld().getRegistryKey().equals(StarAcademyMod.SAFARI)) {
            SafariData.CLIENT.get(player.getUuid()).ifPresent(entry -> {
                if (entry.getTimeLeft() > 0) {
                    entry.setTimeLeft(entry.getTimeLeft() - 1);
                }
            });
        }
    }
}