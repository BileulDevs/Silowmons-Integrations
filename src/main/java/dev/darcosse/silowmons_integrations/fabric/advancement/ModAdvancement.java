package dev.darcosse.silowmons_integrations.fabric.advancement;

import dev.darcosse.silowmons_integrations.fabric.SilowmonsIntegrations;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;

/**
 * Enum représentant les différents achievements du mod
 */
public enum ModAdvancement {
    COMPLETED_JOHTO_DEX("completed_johto_dex", "Completed Johto Dex"),
    COMPLETED_KANTO_DEX("completed_kanto_dex", "Completed Kanto Dex"),
    COMPLETED_HOENN_DEX("completed_hoenn_dex", "Completed Hoenn Dex"),
    COMPLETED_SINNOH_DEX("completed_sinnoh_dex", "Completed Sinnoh Dex"),
    COMPLETED_UNOVA_DEX("completed_unova_dex", "Completed Unova Dex"),
    COMPLETED_KALOS_DEX("completed_kalos_dex", "Completed Kalos Dex"),
    COMPLETED_ALOLA_DEX("completed_alola_dex", "Completed Alola Dex"),
    COMPLETED_GALAR_DEX("completed_galar_dex", "Completed Galar Dex"),
    COMPLETED_PALDEA_DEX("completed_paldea_dex", "Completed Paldea Dex"),
    COMPLETED_NATIONAL_DEX("complete_national_dex", "Complete National Dex");

    private final String displayName;
    private final Identifier identifier;

    ModAdvancement(String path, String displayName) {
        this.displayName = displayName;
        this.identifier = Identifier.of(SilowmonsIntegrations.MOD_ID, path);
    }

    /**
     * Retourne l'avancement associé à cette entrée, ou null s'il n'existe pas
     */
    public AdvancementEntry getAdvancement(MinecraftServer server) {
        return getAdvancement(server, this.identifier);
    }

    public static AdvancementEntry getAdvancement(MinecraftServer server, Identifier identifier) {
        if (server == null) return null;
        return server.getAdvancementLoader().get(identifier);
    }

    @Override
    public String toString() {
        return displayName + " (" + identifier + ")";
    }
}