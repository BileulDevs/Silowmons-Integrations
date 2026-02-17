package dev.darcosse.silowmons_integrations.fabric.mixin;

import com.cobblemon.mod.common.item.gimmicks.megaevolution.MegaStoneUtils;
import com.cobblemon.mod.common.pokemon.Pokemon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MegaStoneUtils.class, remap = false)
public class MixinMegaStoneUtils {

    @Inject(method = "removeMegaEvolution", at = @At("HEAD"), cancellable = true)
    private void onRemoveMegaEvolution(Pokemon pokemon, CallbackInfo ci) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        for (StackTraceElement element : stackTrace) {
            if (element.getMethodName().contains("initialise$lambda$1")) {
                ci.cancel();
                return;
            }
        }
    }
}