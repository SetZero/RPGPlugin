package eu.around_me.rpgplugin.skilleffects.active.overtime.points;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import eu.around_me.rpgplugin.playerstats.RPGPlayerStat;
import eu.around_me.rpgplugin.skills.OverTimeSkill;
import net.md_5.bungee.api.ChatColor;

public class ArrowFall extends OverTimeSkill {

	private int maxrange = 5;
	private Location location;
	private double overground = 10;
	private double variance = 2.5;
	private double radius = 2;
	private int projectilesPerWave = 7;
	
	public ArrowFall() {
		
		skillName = "ArrowFall";
		skillDesc = "Create an Arrow Storm, which hits Enemies";
		skillColor = ChatColor.WHITE;
		skillItem = new ItemStack(Material.ARROW);
		req = null;
		prevReq = false;
		prevAmount = 0;
		cooldown = 15;
		manacost = 5;
		
		tickTime = 1;
		manaCostOverTime = true;
		activated = false;
	}
	
	@Override
	public void firstExecute(RPGPlayerStat stat, HumanEntity p) {

		
		Set<Material> transparent = new HashSet<Material>();
		Block b = p.getTargetBlock(transparent, maxrange);
		Location l = b.getLocation();
		l.setDirection(p.getLocation().getDirection());
		location = l;

	}

	@Override
	public void onDeactivation(RPGPlayerStat stat, HumanEntity p) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean executeActive(RPGPlayerStat stat, HumanEntity p) {
		for(int i=0;i<projectilesPerWave;i++) {
			Location firestart = new Location(location.getWorld(), 
					location.getX() + ThreadLocalRandom.current().nextDouble(-radius, radius + 1), 
					location.getY() + ThreadLocalRandom.current().nextDouble(overground-variance, overground+variance + 1),
					location.getZ() + ThreadLocalRandom.current().nextDouble(-radius, radius + 1));
			Location target = new Location(location.getWorld(), 
					location.getX() + ThreadLocalRandom.current().nextDouble(-radius, radius + 1), 
					location.getY(),
					location.getZ() + ThreadLocalRandom.current().nextDouble(-radius, radius + 1));
			Vector fire = target.toVector().subtract(firestart.toVector());
			
			Arrow arrow = p.getWorld().spawnArrow(firestart, fire, 1, 10);
			arrow.setShooter(p);
			arrow.setVelocity(fire.normalize());
		}
		
		
		
		return false;
	}

	@Override
	public boolean needsUnload() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void unload() {
		// TODO Auto-generated method stub

	}

}
