package pro.mclol.better_enchants;

import org.bukkit.plugin.java.JavaPlugin;
import pro.mclol.better_enchants.Enchantments.EnchantmentTable;
import pro.mclol.better_enchants.Enchantments.FrostAspect;
import pro.mclol.better_enchants.Enchantments.Tomohawk;

public final class BetterEnchants extends JavaPlugin {

    @Override
    public void onEnable() {
        new FrostAspect(this);
        new Tomohawk(this);
        new EnchantmentTable(this);


        ItemGroup.registerGroups();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
