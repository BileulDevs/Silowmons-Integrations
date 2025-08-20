package dev.darcosse.silowmons_integrations.fabric.util;

import com.mojang.brigadier.context.CommandContext;
import dev.darcosse.silowmons_integrations.fabric.config.BattleTowerTrainer;
import dev.darcosse.silowmons_integrations.fabric.config.ConfigManager;
import net.minecraft.server.command.ServerCommandSource;

import java.util.List;
import java.util.Random;

public class TrainerUtils {
    static Random random = new Random();

    public static int summonTrainer(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        source.getServer().getCommandManager().executeWithPrefix(
                source, "rctmod trainer summon " + getRandomTrainer()
        );
        return 1;
    }

    public static String getRandomTrainer() {
        List<BattleTowerTrainer> trainers = ConfigManager.getBattleTowerTrainers();
        return trainers.get(random.nextInt(trainers.size())).getId();
    }
}
