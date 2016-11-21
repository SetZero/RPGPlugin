package eu.around_me.rpgplugin.skilleffects.active.points;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Wolf;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import eu.around_me.rpgplugin.playerstats.RPGPlayerStat;
import eu.around_me.rpgplugin.skills.ActiveSkill;
import net.md_5.bungee.api.ChatColor;

public class Taunt extends ActiveSkill {
	
	//Custom
	private int distance = 40;
	Plugin plugin;
	
	public Taunt(Plugin plugin) {
		this.plugin = plugin;
		
		skillName = "Taunt";
		skillDesc = "Taunts nearby enemies";
		skillColor = ChatColor.DARK_RED;
		req = null;
		prevReq = false;
		prevAmount = 0;
		
		skillItem = new ItemStack(Material.GOLD_SWORD);
		cooldown = 10;
		manacost = 5;
	}

	@Override
	public boolean executeActive(RPGPlayerStat stat, HumanEntity p) {
		tauntEnemies(p);
		return true;
	}
	
	@Override
	public boolean needsUnload() {
		return false;
	}

	@Override
	public void unload() {
		
	}
	
	protected void tauntEnemies(HumanEntity p) {
		List<Entity> mobs = p.getNearbyEntities(distance, distance, distance);
		for (Entity e : mobs) {
			if ((e instanceof Monster)) {
				((Monster) e).setTarget(p);
			} else if (e instanceof Wolf) {
				Wolf w = (Wolf) e;
				w.setAngry(true);
				w.setTarget(p);
			}
		}
	}

}
