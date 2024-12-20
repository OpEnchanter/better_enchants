package pro.mclol.better_enchants;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.bootstrap.PluginProviderContext;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.data.EnchantmentRegistryEntry;
import io.papermc.paper.registry.event.RegistryEvents;
import io.papermc.paper.registry.keys.EnchantmentKeys;
import io.papermc.paper.registry.keys.tags.ItemTypeTagKeys;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.plugin.java.JavaPlugin;

public class Bootstrap implements PluginBootstrap {
    @Override
    public void bootstrap(BootstrapContext context) {
        // Make Vanilla enchantments have more levels
        context.getLifecycleManager().registerEventHandler(RegistryEvents.ENCHANTMENT.entryAdd()
                .newHandler(event -> event.builder().maxLevel(10))
                .filter(EnchantmentKeys.SHARPNESS)
        );

        // Add custom enchantments
        context.getLifecycleManager().registerEventHandler(RegistryEvents.ENCHANTMENT.freeze().newHandler(event -> {

            // Register Frost Aspect Enchant
            event.registry().register(
                    TypedKey.create(RegistryKey.ENCHANTMENT, Key.key("frost_aspect")),
                    b -> b.description(Component.text("Frost Aspect"))
                            .supportedItems(event.getOrCreateTag(ItemTypeTagKeys.SWORDS))
                            .anvilCost(8)
                            .maxLevel(2)
                            .weight(3)
                            .minimumCost(EnchantmentRegistryEntry.EnchantmentCost.of(8, 1))
                            .maximumCost(EnchantmentRegistryEntry.EnchantmentCost.of(4, 1))
                            .activeSlots(EquipmentSlotGroup.ANY)
            );

            // Register Tomohawk Enchant
            event.registry().register(
                    TypedKey.create(RegistryKey.ENCHANTMENT, Key.key("tomohawk")),
                    b -> b.description(Component.text("Tomohawk"))
                            .supportedItems(event.getOrCreateTag(ItemTypeTagKeys.AXES))
                            .anvilCost(13)
                            .maxLevel(1)
                            .weight(3)
                            .minimumCost(EnchantmentRegistryEntry.EnchantmentCost.of(13, 1))
                            .maximumCost(EnchantmentRegistryEntry.EnchantmentCost.of(5, 1))
                            .activeSlots(EquipmentSlotGroup.ANY)
            );
        }));
    }

    @Override
    public JavaPlugin createPlugin(PluginProviderContext context) {
        return new BetterEnchants();
    }
}