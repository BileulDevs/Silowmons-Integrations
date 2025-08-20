package dev.darcosse.silowmons_integrations.fabric.tick;

import dev.darcosse.silowmons_integrations.fabric.config.ScoreboardObjective;
import dev.darcosse.silowmons_integrations.fabric.util.ScoreboardUtils;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import java.util.List;

/**
 * Handler générique pour les scoreboards non spécialisés
 */
public class HandleScoreboard {

    public static void setScoreboard(MinecraftServer server, ScoreboardObjective objectiveConfig) {
        Scoreboard scoreboard = server.getScoreboard();
        net.minecraft.scoreboard.ScoreboardObjective objective = scoreboard.getNullableObjective(objectiveConfig.getName());
        if (objective == null) return;

        List<ScoreboardEntry> top5 = scoreboard.getScoreboardEntries(objective).stream()
                .sorted((a, b) -> Integer.compare(b.value(), a.value()))
                .limit(5)
                .toList();

        ServerWorld world = server.getOverworld();

        Vec3d basePos = objectiveConfig.getPosition();
        double spacing = 0.3;

        ArmorStandEntity title = new ArmorStandEntity(world, basePos.x, basePos.y + 0.3, basePos.z);
        title.setInvisible(true);
        title.setNoGravity(true);
        title.setShowArms(false);
        title.setSilent(true);
        title.noClip = true;

        title.setCustomName(Text.literal("§6" + objectiveConfig.getDisplayName()));
        title.setCustomNameVisible(true);

        world.spawnEntity(title);

        for (int i = 0; i < top5.size(); i++) {
            ScoreboardEntry score = top5.get(i);
            String playerName = score.owner();
            int value = score.value();

            Vec3d linePos = basePos.add(0, -i * spacing, 0);

            ArmorStandEntity armorStand = new ArmorStandEntity(world, linePos.x, linePos.y, linePos.z);

            armorStand.setInvisible(true);
            armorStand.setNoGravity(true);
            armorStand.setShowArms(false);
            armorStand.setSilent(true);
            armorStand.noClip = true;

            armorStand.setCustomName(Text.literal(ScoreboardUtils.getPodiumColor(i + 1) + " §b" + playerName + " §7- §c" + value));
            armorStand.setCustomNameVisible(true);

            world.spawnEntity(armorStand);
        }
    }
}