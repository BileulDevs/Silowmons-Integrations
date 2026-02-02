package dev.darcosse.silowmons_integrations.fabric.command;

import com.mojang.brigadier.CommandDispatcher;
import dev.darcosse.silowmons_integrations.fabric.SilowmonsIntegrations;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class ModCommands {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            registerCommands(dispatcher);
        });

        SilowmonsIntegrations.LOGGER.info("[ModCommands] Successfully registered Commands");
    }

    private static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("silowmonsintegrations")
                .then(CommandManager.literal("reload")
                        .requires(source -> source.hasPermissionLevel(2))
                        .executes(SilowmonsIntegrationsCommands::reloadConfig))
        );
    }
}
