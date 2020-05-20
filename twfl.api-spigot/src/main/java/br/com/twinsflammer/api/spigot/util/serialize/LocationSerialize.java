package br.com.twinsflammer.api.spigot.util.serialize;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * Created by @SrGutyerrez
 */
public class LocationSerialize {
    public static String toString(Location location){
        JSONObject object = new JSONObject();
        object.put("world", location.getWorld().getName());
        object.put("x", location.getX());
        object.put("y", location.getY());
        object.put("z", location.getZ());
        object.put("yaw", location.getYaw());
        object.put("pitch", location.getPitch());
        return object.toString();
    }

    public static Location toLocation(String str){
        JSONObject object = (JSONObject) JSONValue.parse(str);
        String worldName = (String) object.get("world");
        World world = Bukkit.getWorld(worldName);
        Double x = (Double) object.get("x");
        Double y = (Double) object.get("y");
        Double z = (Double) object.get("z");
        Float pitch = ((Double) object.get("pitch")).floatValue();
        Float yaw = ((Double) object.get("yaw")).floatValue();
        return new Location(world, x,y,z,yaw, pitch);
    }
}
