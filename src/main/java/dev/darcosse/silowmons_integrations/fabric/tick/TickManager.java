package dev.darcosse.silowmons_integrations.fabric.tick;

import abeshutt.staracademy.init.ModWorldData;
import dev.darcosse.silowmons_integrations.fabric.SilowmonsIntegrations;
import dev.darcosse.silowmons_integrations.fabric.config.ConfigManager;
import dev.darcosse.silowmons_integrations.fabric.network.SafariUpdateS2CPacket;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Box;

import java.util.List;

public class TickManager {
    private static int tickCounter = 0;
    public static int CHECK_INTERVAL = ConfigManager.getScoreboardRefreshInterval();

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            tickCounter++;

            if (tickCounter % 20 == 0) {
                syncSafariToPlayers(server);
            }

            if (tickCounter % CHECK_INTERVAL != 0) {
                return;
            }

            if (ConfigManager.isScoreboardHologramsEnabled()) {
                killArmorstands(server);
                ConfigManager.getObjectives().forEach(obj -> {
                    HandleScoreboard.setScoreboard(server, obj);
                });
            }
        });

        SilowmonsIntegrations.LOGGER.info("[TickManager] Successfully loaded Tick Events");
    }

    private static void syncSafariToPlayers(MinecraftServer server) {
        var safariMap = ModWorldData.SAFARI.getGlobal(server);

        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            safariMap.get(player.getUuid()).ifPresent(entry -> {
                ServerPlayNetworking.send(player, new SafariUpdateS2CPacket(player.getUuid(), entry.getTimeLeft()));
            });
        }
    }

    private static void killArmorstands(MinecraftServer server) {
        List<ArmorStandEntity> toRemove = server.getOverworld().getEntitiesByClass(
                ArmorStandEntity.class,
                new Box(-30000000, -64, -30000000, 30000000, 320, 30000000),
                entity -> entity.isInvisible() && entity.hasNoGravity() && !entity.shouldShowArms()
        );
        toRemove.forEach(Entity::discard);
    }
}