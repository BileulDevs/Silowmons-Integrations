package dev.darcosse.silowmons_integrations.fabric.util;

import com.gitlab.srcmc.rctmod.api.RCTMod;
import com.gitlab.srcmc.rctmod.world.entities.TrainerMob;
import com.mojang.brigadier.context.CommandContext;
import dev.darcosse.silowmons_integrations.fabric.config.BattleTowerArena;
import dev.darcosse.silowmons_integrations.fabric.config.BattleTowerTrainer;
import dev.darcosse.silowmons_integrations.fabric.config.ConfigManager;
import dev.darcosse.silowmons_integrations.fabric.registry.BattleTowerRegistry;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class TrainerUtils {
    static Random random = new Random();

    public static int summonTrainerByCommand(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        source.getServer().getCommandManager().executeWithPrefix(
                source, "rctmod trainer summon " + getRandomTrainer()
        );
        return 1;
    }

    public static void summonTrainer(ServerPlayerEntity player, BattleTowerArena arena) {
        World world = player.getWorld();

        var trainer = TrainerMob.getEntityType().create(world);
        trainer.setPos(arena.trainerSpawnPos.getX(), arena.trainerSpawnPos.getY() + 2, arena.trainerSpawnPos.getZ());
        trainer.setTrainerId(getRandomTrainer());
        trainer.setPersistent(true);

        world.spawnEntity(trainer);

        RCTMod.getInstance().getTrainerSpawner().register(trainer);

        arena.isOccupied = true;

        BattleTowerRegistry.addTrainer(trainer.getUuid(), arena);
    }

    public static void removeTrainer(ServerWorld world, UUID trainerUUID, BattleTowerArena arena) {
        var trainer = world.getEntity(trainerUUID);

        if (trainer instanceof TrainerMob tm) {
            tm.discard();
            RCTMod.getInstance().getTrainerSpawner().unregister(tm);
        }

        if (arena != null) {
            arena.isOccupied = false;
        }
    }

    public static void removeAllTrainers(ServerWorld world) {
        BattleTowerRegistry.getTrainers().forEach(trainerUUID -> {
            var currentTrainer = world.getEntity(trainerUUID);

            if (currentTrainer instanceof TrainerMob tm) {
                tm.discard();
                RCTMod.getInstance().getTrainerSpawner().unregister(tm);
            }

            ConfigManager.getBattleTowerArenas().forEach(battleTowerArena -> {
                if (battleTowerArena.isOccupied) battleTowerArena.isOccupied = false;
            });
        });

    }

    public static String getRandomTrainer() {
        List<BattleTowerTrainer> trainers = ConfigManager.getBattleTowerTrainers();
        return trainers.get(random.nextInt(trainers.size())).getId();
    }


}
