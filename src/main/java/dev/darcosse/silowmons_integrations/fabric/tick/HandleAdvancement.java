package dev.darcosse.silowmons_integrations.fabric.tick;

import dev.darcosse.silowmons_integrations.fabric.advancement.ModAdvancement;
import dev.darcosse.silowmons_integrations.fabric.util.AdvancementUtils;
import dev.darcosse.silowmons_integrations.fabric.util.PokedexRegionUtils;
import dev.darcosse.silowmons_integrations.fabric.util.RegionUtils;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

/**
 * Handler générique pour les advancements
 */
public class HandleAdvancement {

    public static void checkAdvancements(MinecraftServer server) {
        server.getPlayerManager().getPlayerList().forEach(player -> {
            grantRootAdvancement(player);
            for (RegionUtils region : RegionUtils.values()) {
                checkDex(player, region.toString());
            }
        });
    }

    private static void grantRootAdvancement(ServerPlayerEntity player) {
        AdvancementUtils.grantAdvancement(player, ModAdvancement.ROOT.getAdvancement(player.server));
    }

    private static void checkDex(ServerPlayerEntity player, String dex) {
        if (PokedexRegionUtils.getRegionProgress(player, dex.toLowerCase()).isCompleted()) {
            AdvancementEntry advancement = ModAdvancement.getAdvancement(player.server, dex);
            if (advancement != null) {
                AdvancementUtils.grantAdvancement(player, advancement);
            }
        }
    }
}