package eu.around_me.rpgplugin.libary;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class WorldEditing {

	private WorldEditing() {
		// TODO Auto-generated constructor stub
	}

	public static void placeBlock(World w, Location l, Material m) {
		if(l.getBlock().getType() != Material.BEDROCK) {
			l.getBlock().setType(m);
		}
	}
	
	public static void placeBlock(World w, double x, double y, double z, Material m) {
		Location l = new Location(w, x, y, z);
		placeBlock(w, l, m);
	}
	
	public static void placeTempBlock(World w, double x, double y, double z, Material m, int time, Plugin p) {
		Location l = new Location(w, x, y, z);
		Block b = l.getBlock();
		Material prevm = b.getType();
		if(b.getType() == Material.AIR)
		{
			placeBlock(w, l, m);
			
			new BukkitRunnable() {
	        
				@Override
				public void run() {
					b.setType(prevm);
				}
            
			}.runTaskLater(p, time);
		}
	}
}
