package pro.mclol.better_enchants;

import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.joml.Vector3f;

public class VecMath {
    public static Vector differenceVector(Vector3f v1, Vector3f v2) {
        return new Vector(v2.x - v1.x, v2.y - v1.y, v2.z - v1.z);
    }
}
