package dev.darcosse.silowmons_integrations.fabric.event;

import dev.darcosse.silowmons_integrations.fabric.SilowmonsIntegrations;
import dev.darcosse.silowmons_integrations.fabric.util.TrainerUtils;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class ModEvents {
    public static void register() {
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
            TrainerUtils.removeAllTrainers(server.getOverworld());
        });

        SilowmonsIntegrations.LOGGER.info("[ModEvents] Registered ModEvents");
    }
}
