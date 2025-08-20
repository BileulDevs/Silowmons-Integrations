package dev.darcosse.silowmons_integrations.fabric.command;

import com.cobblemon.mod.common.pokemon.Species;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import dev.darcosse.silowmons_integrations.fabric.SilowmonsIntegrations;
import dev.darcosse.silowmons_integrations.fabric.util.PokedexRegionUtils;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.List;

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

        dispatcher.register(CommandManager.literal("silowmonsintegrations")
                .then(CommandManager.literal("check")
                        .then(CommandManager.argument("region", StringArgumentType.string())
                                .executes(context -> {
                                    ServerPlayerEntity player = context.getSource().getPlayerOrThrow();
                                    String region = StringArgumentType.getString(context, "region");
                                    PokedexRegionUtils.checkPlayerRegionProgress(player, region);
                                    return 1;
                                })
                        )
                        .executes(context -> {
                            ServerPlayerEntity player = context.getSource().getPlayerOrThrow();
                            PokedexRegionUtils.checkAllRegionsProgress(player);
                            return 1;
                        })
                )
                .then(CommandManager.literal("missing")
                        .then(CommandManager.argument("region", StringArgumentType.string())
                                .executes(context -> {
                                    ServerPlayerEntity player = context.getSource().getPlayerOrThrow();
                                    String region = StringArgumentType.getString(context, "region");

                                    List<Species> missing = PokedexRegionUtils.getMissingPokemon(player, region);
                                    if (missing.isEmpty()) {
                                        player.sendMessage(Text.literal("§aVous avez capturé tous les Pokémon de " + region + " !"));
                                    } else {
                                        player.sendMessage(Text.literal("§6Pokémon manquants en " + region + " (" + missing.size() + "):"));
                                        for (Species species : missing) {
                                            player.sendMessage(Text.literal("§7- #" + species.getNationalPokedexNumber() + " " + species.getName()));
                                        }
                                    }
                                    return 1;
                                })
                        )
                )
                .then(CommandManager.literal("completion")
                        .then(CommandManager.argument("region", StringArgumentType.string())
                                .executes(context -> {
                                    ServerPlayerEntity player = context.getSource().getPlayerOrThrow();
                                    String region = StringArgumentType.getString(context, "region");

                                    PokedexRegionUtils.RegionProgress progress = PokedexRegionUtils.getRegionProgress(player, region);
                                    if (progress != null) {
                                        player.sendMessage(Text.literal("§6=== " + region.toUpperCase() + " ==="));
                                        player.sendMessage(Text.literal("§7Implémentés: §f" + progress.totalImplemented()));
                                        player.sendMessage(Text.literal("§7Vus: §f" + progress.totalSeen()));
                                        player.sendMessage(Text.literal("§7Capturés: §f" + progress.totalCaught()));
                                        player.sendMessage(Text.literal("§7Completion: §f" + String.format("%.1f%%", progress.completionPercentage())));
                                        player.sendMessage(Text.literal("§7Status: " + (progress.isCompleted() ? "§aComplété" : "§eEn cours")));
                                    } else {
                                        player.sendMessage(Text.literal("§cRégion non reconnue: " + region));
                                    }
                                    return 1;
                                })
                        )
                )
        );

    }
}
