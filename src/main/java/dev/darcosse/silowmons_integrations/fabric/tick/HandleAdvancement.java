package dev.darcosse.silowmons_integrations.fabric.tick;

import dev.darcosse.silowmons_integrations.fabric.advancement.ModAdvancement;
import dev.darcosse.silowmons_integrations.fabric.util.AdvancementUtils;
import dev.darcosse.silowmons_integrations.fabric.util.PokedexRegionUtils;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

/**
 * Handler générique pour les advancements
 */
public class HandleAdvancement {

    public static void checkAdvancements(MinecraftServer server) {
        server.getPlayerManager().getPlayerList().forEach(HandleAdvancement::checkJohtoDex);
    }

    private static void checkJohtoDex(ServerPlayerEntity player) {
        if (PokedexRegionUtils.getRegionProgress(player, "johto").isCompleted()) {
            AdvancementEntry advancement = ModAdvancement.COMPLETED_JOHTO_DEX.getAdvancement(player.server);
            if (advancement != null) {
                AdvancementUtils.grantAdvancement(player, advancement);
            }
        }
    }
}