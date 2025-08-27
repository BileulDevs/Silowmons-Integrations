package dev.darcosse.silowmons_integrations.fabric.config;

import net.minecraft.util.math.BlockPos;

public class BattleTowerArena {
    public int arenaID;
    public BlockPos triggerPos;
    public BlockPos trainerSpawnPos;
    public boolean isOccupied;

    public BattleTowerArena(){}

    public BattleTowerArena(int arenaID, BlockPos triggerPos, BlockPos trainerSpawnPos, boolean isOccupied){
        this.arenaID = arenaID;
        this.triggerPos = triggerPos;
        this.trainerSpawnPos = trainerSpawnPos;
        this.isOccupied = isOccupied;
    }

    public int getArenaID() {
        return arenaID;
    }

    public BlockPos getTriggerPos() {
        return triggerPos;
    }

    public BlockPos getTrainerSpawnPos() {
        return trainerSpawnPos;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public boolean setOccupied(boolean occupied) {
        return isOccupied = occupied;
    }
}
