package dev.darcosse.silowmons_integrations.fabric.event;

import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.battles.model.actor.BattleActor;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.battles.actor.PlayerBattleActor;
import com.gitlab.srcmc.rctmod.api.RCTMod;
import dev.darcosse.silowmons_integrations.fabric.SilowmonsIntegrations;
import dev.darcosse.silowmons_integrations.fabric.config.BattleTowerArena;
import dev.darcosse.silowmons_integrations.fabric.config.ConfigManager;
import dev.darcosse.silowmons_integrations.fabric.registry.BattleTowerRegistry;
import dev.darcosse.silowmons_integrations.fabric.util.TrainerUtils;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.Blocks;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class BattleTowerEvents {
    public static void register() {
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            for (BattleTowerArena arena : ConfigManager.getBattleTowerArenas()) {
                handleTriggerSpawnPos(world, arena);
            }
        });

        CobblemonEvents.BATTLE_VICTORY.subscribe(Priority.NORMAL, event -> {
            List<BattleActor> losers = event.getLosers();
            List<BattleActor> winners = event.getWinners();

            BattleActor loser = losers.getFirst();
            BattleActor winner = winners.getFirst();

            if (BattleTowerRegistry.isTrainer(loser.getUuid())) {
                if (winner instanceof PlayerBattleActor playerActor) {
                    ServerWorld world = (ServerWorld) playerActor.getEntity().getWorld();

                    BattleTowerArena arena = BattleTowerRegistry.getArena(loser.getUuid());
                    TrainerUtils.removeTrainer(world, loser.getUuid(), arena);
                    BattleTowerRegistry.removeTrainer(loser.getUuid());
                }
            }

            return null;
        });

        SilowmonsIntegrations.LOGGER.info("Battle Tower Events Registered");
    }

    public static void handleTriggerSpawnPos(ServerWorld world, BattleTowerArena arena) {
        for (ServerPlayerEntity player : world.getPlayers()) {
            BlockPos pos = player.getBlockPos().down();

            if (pos.equals(arena.getTriggerPos()) && world.getBlockState(pos).isOf(Blocks.LODESTONE)) {
                if (!arena.isOccupied) {
                    TrainerUtils.summonTrainer(player, arena);
                }
            }
        }
    }
}
