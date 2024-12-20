package pro.mclol.better_enchants.Enchantments;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.RayTraceResult;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import pro.mclol.better_enchants.BetterEnchants;
import pro.mclol.better_enchants.Damages;
import pro.mclol.better_enchants.EntityGroup;
import pro.mclol.better_enchants.VecMath;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Tomohawk implements Listener {

    int animationDuration = 10;
    float axeSpeed = 0.9f;

    BetterEnchants plugin;

    public Tomohawk(BetterEnchants plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    private Float getItemDamage(ItemStack item, Entity entity) {
        float damage = Damages.getDamageFromMaterial(item.getType());
        Map<Enchantment, Integer> e = item.getEnchantments();
        ArrayList<Enchantment> enchantments = new ArrayList<>(e.keySet());

        if (enchantments.contains(Enchantment.SHARPNESS)) {
            damage += (float) (0.5 * e.get(Enchantment.SHARPNESS) + 0.5);
        } else if (enchantments.contains(Enchantment.BANE_OF_ARTHROPODS) && EntityGroup.ARTHROPODS.contains(entity.getType())) {
            damage += (float) (2.5 * e.get(Enchantment.BANE_OF_ARTHROPODS));
        } else if (enchantments.contains(Enchantment.SMITE) && EntityGroup.UNDEAD.contains(entity.getType())) {
            damage += (float) (2.5 * e.get(Enchantment.SMITE));
        }

        return damage;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction().isRightClick()) {
            Player player = event.getPlayer();
            ItemStack item = player.getInventory().getItemInMainHand();


            if (item.getType() != Material.AIR && item.getItemMeta().hasEnchant(Enchantments.TOMOHAWK)) {
                event.setCancelled(true);

                player.getInventory().setItem(player.getInventory().getHeldItemSlot(), ItemStack.of(Material.AIR));

                Matrix4f transform = new Matrix4f().rotateLocalY((float) Math.toRadians(-90));
                Location location = player.getEyeLocation();

                AtomicInteger taskTime = new AtomicInteger();

                ItemDisplay itemDisplay = player.getWorld().spawn(player.getEyeLocation().add(player.getEyeLocation().getDirection().multiply(0.5)), ItemDisplay.class, entity -> {
                    entity.setItemStack(event.getItem());
                    entity.setTransformationMatrix(
                            transform
                    );
                });

                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EGG_THROW, 1, 0.8f);

                AtomicInteger remainingHits = new AtomicInteger(1);

                Bukkit.getScheduler().runTaskTimer(plugin, task -> {
                    itemDisplay.setTransformationMatrix(
                            transform
                    );
                    itemDisplay.setInterpolationDelay(0);
                    itemDisplay.setInterpolationDuration(1);

                    itemDisplay.teleport(location);

                    if (taskTime.get() < animationDuration && remainingHits.get() > 0) {
                        transform.rotateLocalX((float) Math.toRadians(36));
                        location.add(location.getDirection().multiply(axeSpeed));
                    } else {
                        Location playerEyeLocation = player.getEyeLocation();
                        location.setDirection(VecMath.differenceVector(
                                new Vector3f((float) playerEyeLocation.getX(), (float) playerEyeLocation.getY(), (float) playerEyeLocation.getZ()),
                                new Vector3f((float) location.getX(), (float) location.getY(), (float) location.getZ())
                        ).normalize());
                        transform.rotateLocalX((float) Math.toRadians(-36));
                        location.add(location.getDirection().multiply(-axeSpeed));

                        // Allow player to grab axe
                        if (itemDisplay.getNearbyEntities(0.25, 0.25, 0.25).contains(player)) {
                            PlayerInventory inv = player.getInventory();
                            final Boolean[] inventoryFull = {true};

                            for (int i = 0; i < 36; i++) {
                                if (inv.getItem(i) == null || inv.getItem(i).getType() == Material.AIR) {
                                    inventoryFull[0] = false;
                                }
                            }

                            if (!inventoryFull[0]) {
                                player.getInventory().addItem(item);
                                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
                            } else {
                                player.getWorld().spawn(player.getEyeLocation(), Item.class, droppedItem -> {
                                    droppedItem.setItemStack(item);
                                });
                                player.getWorld().playSound(player.getLocation(), Sound.BLOCK_WOOL_STEP, 1, 1);
                            }




                            itemDisplay.remove();

                            task.cancel();
                            return;
                        }

                        // Drop axe as item if player leaves
                        if (!Bukkit.getServer().getOnlinePlayers().contains(player)) {
                            itemDisplay.remove();
                            player.getWorld().spawn(location, Item.class, entity -> {
                                entity.setItemStack(item);
                            });
                            task.cancel();
                        }
                    }

                    // Hit entities
                    RayTraceResult entityTraceResult = player.getWorld().rayTraceEntities(location, location.getDirection(), 0.1f);
                    if (entityTraceResult != null) {
                        try{
                            if (entityTraceResult.getHitEntity() != player) {
                                LivingEntity livingEntity = (LivingEntity) entityTraceResult.getHitEntity();
                                livingEntity.damage(getItemDamage(item, livingEntity) * 0.75f, player);

                                player.getWorld().playSound(location, Sound.BLOCK_ANVIL_HIT, 1, 1);
                                remainingHits.getAndDecrement();
                                item.damage(1, player);
                            }
                        } catch (Exception e){
                            return;
                        }
                    }

                    RayTraceResult blockTraceResult = player.getWorld().rayTraceBlocks(location, location.getDirection(), 0.1f);
                    if (blockTraceResult != null) {
                        player.getWorld().playSound(location, Sound.BLOCK_ANVIL_HIT, 1, 1);
                        remainingHits.getAndDecrement();
                        item.damage(1, player);
                    }

                    if (taskTime.get() % 4 == 0) {
                        player.getWorld().playSound(location, Sound.ENTITY_BREEZE_LAND, 0.3f, 1);
                    }

                    taskTime.getAndIncrement();
                }, 1, 1);
            }


        }
    }
}
