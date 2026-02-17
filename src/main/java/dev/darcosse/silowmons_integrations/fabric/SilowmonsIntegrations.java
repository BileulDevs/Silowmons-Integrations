package dev.darcosse.silowmons_integrations.fabric;

import dev.darcosse.silowmons_integrations.fabric.command.ModCommands;
import dev.darcosse.silowmons_integrations.fabric.config.ConfigManager;
import dev.darcosse.silowmons_integrations.fabric.network.SafariUpdateS2CPacket;
import dev.darcosse.silowmons_integrations.fabric.tick.TickManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SilowmonsIntegrations implements ModInitializer {
    public static final String MOD_ID = "silowmons_integrations";
    public static final Logger LOGGER = LogManager.getLogger("SilowmonsIntegrations");

    @Override
    public void onInitialize() {
        ConfigManager.loadConfig();

        ModCommands.register();
        TickManager.register();

        PayloadTypeRegistry.playS2C().register(SafariUpdateS2CPacket.ID, SafariUpdateS2CPacket.CODEC);

        LOGGER.info("Initialized Silowmons Integrations");
    }
}