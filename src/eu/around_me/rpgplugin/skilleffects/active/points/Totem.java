package eu.around_me.rpgplugin.skilleffects.active.points;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import eu.around_me.rpgplugin.playerstats.RPGPlayerStat;
import eu.around_me.rpgplugin.skills.ActiveSkill;
import net.md_5.bungee.api.ChatColor;

public class Totem extends ActiveSkill {


	
	//Custom
	private int range = 40;
	ArmorStand as;
	BukkitTask task;
	int lifetime = 0;
	int maxLifetime = 120;
	
	Plugin plugin;
	
	public Totem(Plugin plugin) {
		this.plugin = plugin;
		
		skillName = "Totem";
		skillDesc = "Place a Totem around you";
		skillColor = ChatColor.GOLD;
		skillItem = new ItemStack(Material.ARMOR_STAND);
		req = null;
		prevReq = false;
		prevAmount = 0;
		cooldown = 10;
		manacost = 25;
	}

	@Override
	public boolean executeActive(RPGPlayerStat stat, HumanEntity p) {
		placeTotem(p, stat);
		return true;
	}
	
	@Override
	public boolean needsUnload() {
		return true;
	}

	@Override
	public void unload() {
		if(task != null)
			task.cancel();
		if(as != null)
			as.remove();
	}
	
	public void placeTotem(HumanEntity p, RPGPlayerStat stat) {
		if(p instanceof Player) {
			Player pl = (Player) p;
			Set<Material> transparent = new HashSet<Material>();
			Block b = pl.getTargetBlock(transparent, range);
			Location l = b.getLocation();
			l.setDirection(pl.getLocation().getDirection());
			
			if(task != null)
				task.cancel();
			if(as != null)
				as.remove();
			lifetime = 0;
			as = l.getWorld().spawn(l, ArmorStand.class);
			
			ItemStack skullItem = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
	        SkullMeta meta = (SkullMeta) skullItem.getItemMeta();
	        meta.setOwner(pl.getName());
	        skullItem.setItemMeta(meta);
			
			as.setAI(true);
			as.setVisible(false);
			as.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
			as.setBoots(new ItemStack(Material.LEATHER_BOOTS));
			as.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
			as.setHelmet(new ItemStack(skullItem));
			as.setItemInHand(new ItemStack(Material.BOW));
			as.setCustomName(ChatColor.RED + pl.getName() + "'s Totem");
			as.setCustomNameVisible(true);
			as.setMaxHealth((stat.getWis()+1)*2);
			as.setHealth((stat.getWis()+1)*2);
			as.setCollidable(true);
			as.setLeashHolder(p);
			as.setRemoveWhenFarAway(true);
			as.setArms(true);
			as.setInvulnerable(false);
			
			task = new BukkitRunnable() {
		        
	            @Override
	            public void run() {
	            	attack(p);
	            	lifetime++;
	            	if(lifetime > maxLifetime) {
	            		as.remove();
	            		task.cancel();
	            		p.sendMessage("Your Totem has despawned!");
	            	}
	            	updateLifetime(p);
	            }
	            
	        }.runTaskTimer(this.plugin, 10, 20);
		}
	}
	
	public void attack(HumanEntity p) {
		List<Entity> mobs = as.getNearbyEntities(range, range, range);
		if(mobs.size() == 0) return;
		Entity current = mobs.get(0);
		double distance = current.getLocation().distance(as.getLocation());
		
		for (Entity e : mobs) {
			if(e instanceof Monster) {
				if(e.getLocation().distance(as.getLocation()) < distance) {
					distance = e.getLocation().distance(as.getLocation());
					if(as.hasLineOfSight(e)) current = e;
				}
			}
		}
		if(!(current instanceof Monster)) return;
		
		Vector fire = current.getLocation().toVector().subtract(as.getLocation().toVector());
		double dX = current.getLocation().getX() - as.getLocation().getX();
		double dZ = current.getLocation().getZ() - as.getLocation().getZ();
		
		double DistanceXZ = Math.sqrt(dX * dX + dZ * dZ);
		double newYaw = Math.acos(dX / DistanceXZ) * 180 / Math.PI;
		 if (dZ < 0.0)
	            newYaw = newYaw + Math.abs(180 - newYaw) * 2;
	        newYaw = (newYaw - 90);
		
		
		Location newl = as.getLocation();
		newl.setYaw((float) newYaw);
		as.teleport(newl);
		
		Location shooter = as.getLocation();
		shooter.setY(shooter.getY()+1);
		shooter.setZ(shooter.getZ()+1);
		
		//Arrow arrow = as.getWorld().spawnArrow(shooter, fire, 1, 10);
		//arrow.setShooter(p);
		//arrow.setVelocity(fire.normalize());
		SmallFireball fireball = (SmallFireball)as.getWorld().spawn(shooter, SmallFireball.class);
		fireball.setVelocity(fire);
		fireball.setShooter(p);
		fireball.setFireTicks(0);
		as.launchProjectile(SmallFireball.class, fire);
	
	}
	
	public void updateLifetime(HumanEntity p) {
		if(p instanceof Player) {
			Player pl = (Player) p;
			
			as.setCustomName(ChatColor.RED + pl.getName() + "'s Totem - " + Math.round(100 - ((double)lifetime / maxLifetime) * 100) + "%");
		}
	}


 
}
