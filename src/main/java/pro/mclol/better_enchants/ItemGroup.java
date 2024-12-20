package pro.mclol.better_enchants;

import org.bukkit.Material;
import org.bukkit.inventory.ItemType;

import java.util.ArrayList;

public class ItemGroup {
    public static ArrayList<Material> AXES = new ArrayList<>();
    public static ArrayList<Material> SWORDS = new ArrayList<>();

    public static void registerGroups() {
        AXES.add(Material.NETHERITE_AXE);
        AXES.add(Material.DIAMOND_AXE);
        AXES.add(Material.GOLDEN_AXE);
        AXES.add(Material.IRON_AXE);
        AXES.add(Material.STONE_AXE);
        AXES.add(Material.WOODEN_AXE);

        SWORDS.add(Material.NETHERITE_SWORD);
        SWORDS.add(Material.DIAMOND_SWORD);
        SWORDS.add(Material.GOLDEN_SWORD);
        SWORDS.add(Material.IRON_SWORD);
        SWORDS.add(Material.STONE_SWORD);
        SWORDS.add(Material.WOODEN_SWORD);
    }
}
