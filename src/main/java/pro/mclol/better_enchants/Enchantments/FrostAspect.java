package pro.mclol.better_enchants.Enchantments;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pro.mclol.better_enchants.BetterEnchants;

public class FrostAspect implements Listener {
    public FrostAspect(BetterEnchants plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntityAttack(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            ItemStack weapon = player.getInventory().getItemInMainHand();


            if (weapon.getType() != Material.AIR  && weapon.getItemMeta().hasEnchant(Enchantments.FROST_ASPECT)) {
                Integer level = weapon.getEnchantmentLevel(Enchantments.FROST_ASPECT);;

                Entity entity = event.getEntity();

                entity.setFreezeTicks(40 * level);
                PotionEffect effect = new PotionEffect(PotionEffectType.SLOWNESS, 40 * level, 2, false, false, false);
                effect.apply((LivingEntity) entity);
            }
        }
    }
}
