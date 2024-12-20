package pro.mclol.better_enchants;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class EntityGroup {
    public static ArrayList<EntityType> ARTHROPODS = new ArrayList<>(List.of(new EntityType[]{
            EntityType.BEE,
            EntityType.CAVE_SPIDER,
            EntityType.ENDERMITE,
            EntityType.SILVERFISH,
            EntityType.SPIDER
    }));

    public static ArrayList<EntityType> UNDEAD = new ArrayList<>(List.of(new EntityType[]{
            EntityType.BOGGED,
            EntityType.DROWNED,
            EntityType.HUSK,
            EntityType.ZOMBIE_VILLAGER,
            EntityType.PHANTOM,
            EntityType.SKELETON,
            EntityType.SKELETON_HORSE,
            EntityType.STRAY,
            EntityType.WITHER,
            EntityType.WITHER_SKELETON,
            EntityType.ZOGLIN,
            EntityType.ZOMBIE,
            EntityType.ZOMBIE_HORSE,
            EntityType.ZOMBIFIED_PIGLIN,
    }));
}
