package dev.darcosse.silowmons_integrations.fabric.mixin;

import abeshutt.staracademy.StarAcademyMod;
import abeshutt.staracademy.world.data.save.SafariData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class MixinInGameHud {

    @Shadow @Final private MinecraftClient client;
    @Unique private static final Identifier SAFARI_ICON = Identifier.of("staracademy", "textures/gui/safari_timer.png");

    @Inject(method = "render", at = @At("TAIL"))
    private void renderSafariHUD(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (client.player == null || client.world == null) return;
        if (!client.world.getRegistryKey().equals(StarAcademyMod.SAFARI)) return;

        // On cherche d'abord dans les données reçues par paquet (Multi)
        SafariData.Entry entry = SafariData.CLIENT.get(client.player.getUuid()).orElse(null);

        // Si vide et qu'on est en solo, on pioche dans le serveur interne
        if (entry == null && client.getServer() != null) {
            entry = abeshutt.staracademy.init.ModWorldData.SAFARI.getGlobal(client.getServer())
                    .get(client.player.getUuid()).orElse(null);
        }

        if (entry != null) {
            long ticks = entry.getTimeLeft();

            // Fix pour le bug du "30" (conversion secondes vers ticks)
            if (ticks > 0 && ticks <= 60) {
                ticks = ticks * 20;
            }

            if (ticks > 0) {
                renderSafariDesign(context, ticks);
            }
        }
    }

    @Unique
    private void renderSafariDesign(DrawContext context, long ticks) {
        TextRenderer tr = client.textRenderer;
        int x = 92;
        int y = client.getWindow().getScaledHeight() - 25;

        context.getMatrices().push();
        context.getMatrices().translate(x, y, 0);

        String timeText = formatTimeString(ticks);
        int color = (ticks < 1200 && ticks % 10 < 5) ? 0xFFE85959 : 0xFFFFFFFF;

        context.getMatrices().push();
        context.getMatrices().scale(1.1F, 1.1F, 1.1F);
        context.drawText(tr, timeText, -tr.getWidth(timeText) / 2, 12, color, true);
        context.getMatrices().pop();

        float wobble = (float)Math.sin(Math.PI * 2.0F / 80.0F * ticks * (ticks <= 1200 ? 5.0F : 1.0F));
        context.getMatrices().push();
        context.getMatrices().multiply(RotationAxis.POSITIVE_Z.rotationDegrees(wobble * 30.0F));
        context.drawTexture(SAFARI_ICON, -13, -14, 3, 4, 27, 24, 32, 32);
        context.getMatrices().pop();

        context.getMatrices().pop();
    }

    @Unique
    private String formatTimeString(long ticks) {
        long s = (ticks / 20) % 60;
        long m = ((ticks / 20) / 60) % 60;
        long h = (ticks / 20) / 3600;
        return h > 0 ? String.format("%02d:%02d:%02d", h, m, s) : String.format("%02d:%02d", m, s);
    }
}