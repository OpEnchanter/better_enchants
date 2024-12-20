package pro.mclol.better_enchants;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Damages {
    private static ArrayList<Material> AXES = new ArrayList<>(List.of(new Material[]{Material.NETHERITE_AXE, Material.DIAMOND_AXE, Material.GOLDEN_AXE, Material.IRON_AXE, Material.STONE_AXE, Material.WOODEN_AXE}));
    private static ArrayList<Float> AXES_DAMAGE = new ArrayList<>(List.of(new Float[]{10.0f, 9.0f, 7.0f, 9.0f, 9.0f, 7.0f}));
    public static float getDamageFromMaterial(Material material) {
        if (AXES.contains(material)) {
            return AXES_DAMAGE.get(AXES.indexOf(material));
        }
        return -1f;
    }
}
