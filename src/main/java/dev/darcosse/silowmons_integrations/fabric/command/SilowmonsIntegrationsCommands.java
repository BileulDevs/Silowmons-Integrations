package dev.darcosse.silowmons_integrations.fabric.command;

import com.mojang.brigadier.context.CommandContext;
import dev.darcosse.silowmons_integrations.fabric.config.ConfigManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class SilowmonsIntegrationsCommands {
    public static int reloadConfig(CommandContext<ServerCommandSource> context) {
        try {
            ConfigManager.reloadConfig();

            context.getSource().sendFeedback(
                    () -> Text.translatable("command.silowmons_integrations.reload.success"),
                    true
            );

            return 1;
        } catch (Exception e) {
            context.getSource().sendError(
                    Text.translatable("command.silowmons_integrations.reload.error", e.getMessage())
            );
            return 0;
        }
    }
}
