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
import eu.around_me.rpgplugin.skills.Skill;
import net.md_5.bungee.api.ChatColor;

public class Taunt extends ActiveSkill {

	String skillName = "Taunt";
	String skillDesc = "Taunts nearby enemies";
	ChatColor skillColor = ChatColor.DARK_RED;
	ItemStack skillItem = new ItemStack(Material.GOLD_SWORD);
	Skill[] req = {};
	boolean prevReq = false;
	int prevAmount = 0;
	private int cooldown = 10;
	private int manacost = 5;
	
	//Custom
	private int distance = 40;
	Plugin plugin;
	
	public Taunt(Plugin plugin) {
		this.plugin = plugin;
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

	@Override
	public String getName() {
		return skillName;
	}

	@Override
	public String setName(String name) {
		this.skillName = name;
		return null;
	}

	@Override
	public String getDescription() {
		return skillDesc;
	}

	@Override
	public void setDescription(String desc) {
		this.skillDesc = desc;
	}

	@Override
	public ChatColor getChatColor() {
		return skillColor;
	}

	@Override
	public void setChatColor(ChatColor color) {
		this.skillColor = color;

	}

	@Override
	public void setSkillRequirements(Skill[] req) {
		this.req = req;

	}

	@Override
	public Skill[] getSkillRequirements() {
		return req;
	}

	@Override
	public void setNodeRequirements(boolean prevReq, int prevAmount) {
		this.prevReq = prevReq;
		this.prevAmount = prevAmount;

	}

	@Override
	public int getNodeRequirements() {
		if(prevReq) {
			return prevAmount;
		} else {
			return 0;
		}
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

	@Override
	public ItemStack getItem() {
		return this.skillItem;
	}

	@Override
	public int getCooldown() {
		return this.cooldown;
	}

	@Override
	public int getManacost() {
		return manacost;
	}

}
