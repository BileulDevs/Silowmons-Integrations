package dev.darcosse.silowmons_integrations.fabric;

import dev.darcosse.silowmons_integrations.fabric.command.ModCommands;
import dev.darcosse.silowmons_integrations.fabric.config.ConfigManager;
import dev.darcosse.silowmons_integrations.fabric.event.BattleTowerEvents;
import dev.darcosse.silowmons_integrations.fabric.event.ModEvents;
import dev.darcosse.silowmons_integrations.fabric.item.ModItems;
import dev.darcosse.silowmons_integrations.fabric.tick.TickManager;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SilowmonsIntegrations implements ModInitializer {
    public static final String MOD_ID = "silowmons_integrations";
    public static final Logger LOGGER = LogManager.getLogger("SilowmonsIntegrations");

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Silowmons Integrations");
        ConfigManager.loadConfig();

        ModItems.registerModItems();
        BattleTowerEvents.register();
        ModEvents.register();
        ModCommands.register();
        TickManager.register();
        LOGGER.info("Initialized Silowmons Integrations");
    }
}