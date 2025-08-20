package dev.darcosse.silowmons_integrations.fabric.config;

import net.minecraft.util.math.Vec3d;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Configuration des scoreboards du mod
 */
public class ScoreboardConfig {
    public List<ScoreboardObjective> objectives = new ArrayList<>();

    public ScoreboardConfig() {
        objectives = Arrays.asList(
                new ScoreboardObjective(
                        "catch_count",
                        "\uD83D\uDC51 Pokémon Capturés \uD83D\uDC51",
                        new Vec3d(0, -60, 0)
                ),
                new ScoreboardObjective(
                        "registered",
                        "\uD83D\uDD25 Pokémon Enregistrés \uD83D\uDD25",
                        new Vec3d(10, -60, 10)
                )
        );
    }
}