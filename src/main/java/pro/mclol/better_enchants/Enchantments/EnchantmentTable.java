package pro.mclol.better_enchants.Enchantments;

import io.papermc.paper.registry.keys.tags.ItemTypeTagKeys;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentOffer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import pro.mclol.better_enchants.BetterEnchants;
import pro.mclol.better_enchants.ItemGroup;

import java.util.ArrayList;
import java.util.Random;

public class EnchantmentTable implements Listener {
    public EnchantmentTable (BetterEnchants plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void prepareEnchantEvent(PrepareItemEnchantEvent event) {
        if (ItemGroup.AXES.contains(event.getItem().getType())) {
            if (Math.random() < 0.15) {
                int offer = new Random().nextInt(3);
                event.getOffers()[offer] = new EnchantmentOffer(Enchantments.TOMOHAWK, 1, 25);
            }
        } else if (ItemGroup.SWORDS.contains(event.getItem().getType())) {
            if (Math.random() < 0.02) {
                int offer = new Random().nextInt(3);
                int level = new Random().nextInt(2);
                event.getOffers()[offer] = new EnchantmentOffer(Enchantments.FROST_ASPECT, level, 15*level);
            }
        }
    }


    // Fix Enchantment Table not enchanting with custom enchants
    @EventHandler
    public void itemEnchant(EnchantItemEvent event) {
        ArrayList<Enchantment> customEnchants = new ArrayList<>();
        customEnchants.add(Enchantments.FROST_ASPECT);
        customEnchants.add(Enchantments.TOMOHAWK);

        if (customEnchants.contains(event.getEnchantmentHint())) {
            event.setCancelled(true);

            event.getItem().addEnchantment(event.getEnchantmentHint(), 1);
        }
    }
}
