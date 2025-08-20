package dev.darcosse.silowmons_integrations.fabric.tick;

import dev.darcosse.silowmons_integrations.fabric.SilowmonsIntegrations;
import dev.darcosse.silowmons_integrations.fabric.config.ConfigManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.Box;

import java.util.List;

public class TickManager {
    private static int tickCounter = 0;
    public static int CHECK_INTERVAL = ConfigManager.getScoreboardRefreshInterval();

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            tickCounter++;

            HandleAdvancement.checkAdvancements(server);

            // Tout ce qu'il y a en dessous du return, sera lancé une fois tous les CHECK_INTERVAL
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

    private static void killArmorstands(MinecraftServer server) {
        if (tickCounter % CHECK_INTERVAL != 0) {
            return;
        }

        List<ArmorStandEntity> toRemove = server.getOverworld().getEntitiesByClass(
                ArmorStandEntity.class,
                new Box(-30000000, -64, -30000000, 30000000, 320, 30000000), // scan tout le monde
                entity -> entity.isInvisible() && entity.hasNoGravity() && !entity.shouldShowArms()
        );
        toRemove.forEach(Entity::discard);
    }
}
