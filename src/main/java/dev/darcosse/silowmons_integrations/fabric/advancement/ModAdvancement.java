package dev.darcosse.silowmons_integrations.fabric.advancement;

import dev.darcosse.silowmons_integrations.fabric.SilowmonsIntegrations;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;

/**
 * Enum représentant les différents achievements du mod
 */
public enum ModAdvancement {
    ROOT("root", "Welcome to Silowmons", "root"),

    COMPLETED_JOHTO_DEX("completed_johto_dex", "Completed Johto Dex", "johto"),
    COMPLETED_KANTO_DEX("completed_kanto_dex", "Completed Kanto Dex", "kanto"),
    COMPLETED_HOENN_DEX("completed_hoenn_dex", "Completed Hoenn Dex",  "hoenn"),
    COMPLETED_SINNOH_DEX("completed_sinnoh_dex", "Completed Sinnoh Dex",  "sinnoh"),
    COMPLETED_UNOVA_DEX("completed_unova_dex", "Completed Unova Dex", "unova"),
    COMPLETED_KALOS_DEX("completed_kalos_dex", "Completed Kalos Dex", "kalos"),
    COMPLETED_ALOLA_DEX("completed_alola_dex", "Completed Alola Dex",  "alola"),
    COMPLETED_GALAR_DEX("completed_galar_dex", "Completed Galar Dex", "galar"),
    COMPLETED_PALDEA_DEX("completed_paldea_dex", "Completed Paldea Dex", "paldea"),

    COMPLETED_NATIONAL_DEX("completed_national_dex", "Complete National Dex", "national");

    private final Identifier identifier;
    private final String key;

    ModAdvancement(String path, String displayName, String key) {
        this.identifier = Identifier.of(SilowmonsIntegrations.MOD_ID, path);
        this.key = key;
    }

    public AdvancementEntry getAdvancement(MinecraftServer server) {
        return getAdvancement(server, this.identifier);
    }

    public static AdvancementEntry getAdvancement(MinecraftServer server, Identifier identifier) {
        if (server == null) return null;
        return server.getAdvancementLoader().get(identifier);
    }

    public static AdvancementEntry getAdvancement(MinecraftServer server, String dex) {
        for (ModAdvancement modAdv : values()) {
            if (modAdv.key.equalsIgnoreCase(dex)) {
                return modAdv.getAdvancement(server);
            }
        }
        return null;
    }
}