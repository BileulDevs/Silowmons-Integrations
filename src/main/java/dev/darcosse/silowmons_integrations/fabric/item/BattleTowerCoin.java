package dev.darcosse.silowmons_integrations.fabric.item;

import dev.darcosse.silowmons_integrations.fabric.SilowmonsIntegrations;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class BattleTowerCoin implements FabricItem {
    public static Identifier BattleTowerCoinId = Identifier.of(SilowmonsIntegrations.MOD_ID, "battle_tower_coin");
    public static final Item BattleTowerCoinItem = new Item(new Item.Settings().rarity(Rarity.RARE).fireproof().maxCount(64));
}
