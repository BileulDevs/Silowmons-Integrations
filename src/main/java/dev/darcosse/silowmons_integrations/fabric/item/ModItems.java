package dev.darcosse.silowmons_integrations.fabric.item;

import dev.darcosse.silowmons_integrations.fabric.SilowmonsIntegrations;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Identifier SILOWMONS_GROUP_ID = Identifier.of(SilowmonsIntegrations.MOD_ID, "silowmons_integrations");
    public static ItemGroup SILOWMONS_INTEGRATIONS_GROUP;

    public static void registerModItems() {
        Registry.register(Registries.ITEM, BattleTowerCoin.BattleTowerCoinId, BattleTowerCoin.BattleTowerCoinItem);

        SILOWMONS_INTEGRATIONS_GROUP = Registry.register(
                Registries.ITEM_GROUP,
                SILOWMONS_GROUP_ID,
                FabricItemGroup.builder()
                        .icon(() -> new ItemStack(BattleTowerCoin.BattleTowerCoinItem))
                        .displayName(Text.literal("Silowmons Integrations"))
                        .entries((context, entries) -> {
                            entries.add(BattleTowerCoin.BattleTowerCoinItem);
                        }).build()
        );

        SilowmonsIntegrations.LOGGER.info("[ModItems] Registered Items");
    }
}
