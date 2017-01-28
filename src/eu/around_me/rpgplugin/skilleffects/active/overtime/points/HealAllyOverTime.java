package eu.around_me.rpgplugin.skilleffects.active.overtime.points;

import java.util.ArrayList;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import eu.around_me.rpgplugin.playerstats.RPGPlayerStat;
import eu.around_me.rpgplugin.skills.OverTimeSkill;
import net.md_5.bungee.api.ChatColor;

public class HealAllyOverTime extends OverTimeSkill{

	//Custom
	private int healAmount = 1;
	private LivingEntity lockedTarget;
	
	public HealAllyOverTime() {
		skillName = "HealAlly";
		skillDesc = "Heals any Ally you are targeting";
		skillColor = ChatColor.GREEN;
		skillItem = new ItemStack(Material.POTION);
		req = null;
		prevReq = false;
		prevAmount = 0;
		cooldown = 1;
		manacost = 10;
		
		tickTime = 1;
		manaCostOverTime = true;
		activated = false;
	}
	
	@Override
	public void firstExecute(RPGPlayerStat stat, HumanEntity p) {
		Entity target = getNearestEntityInSight((Player) p, 3);
		if(target instanceof LivingEntity) {
			lockedTarget = (LivingEntity)target;
			p.sendMessage("Locked to " + lockedTarget.getName());
		}
		
	}
	
	@Override
	public boolean executeActive(RPGPlayerStat stat, HumanEntity p) {
		if(lockedTarget != null) {
			double maxHealth = lockedTarget.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
			if(maxHealth >= lockedTarget.getHealth() + healAmount) {
				lockedTarget.setHealth(lockedTarget.getHealth() + healAmount);
				p.sendMessage("Healed " + lockedTarget.getName() + " by " + healAmount);
			} else {
				lockedTarget.setHealth(maxHealth);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean needsUnload() {
		return false;
	}

	@Override
	public void unload() {
		
	}
	
	public static Entity getNearestEntityInSight(Player player, int range) {
	    ArrayList<Entity> entities = (ArrayList<Entity>) player.getNearbyEntities(range, range, range);
	    ArrayList<Block> sightBlock = (ArrayList<Block>) player.getLineOfSight( (Set<Material>) null, range);
	    ArrayList<Location> sight = new ArrayList<Location>();
	    for (int i = 0;i<sightBlock.size();i++)
	        sight.add(sightBlock.get(i).getLocation());
	    for (int i = 0;i<sight.size();i++) {
	        for (int k = 0;k<entities.size();k++) {
	            if (Math.abs(entities.get(k).getLocation().getX()-sight.get(i).getX())<1.3) {
	                if (Math.abs(entities.get(k).getLocation().getY()-sight.get(i).getY())<1.5) {
	                    if (Math.abs(entities.get(k).getLocation().getZ()-sight.get(i).getZ())<1.3) {
	                        return entities.get(k);
	                    }
	                }
	            }
	        }
	    }
	    return null; //Return null/nothing if no entity was found
	}

	@Override
	public void onDeactivation(RPGPlayerStat stat, HumanEntity p) {
		lockedTarget = null;
		
	}

}
