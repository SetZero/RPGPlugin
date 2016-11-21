package eu.around_me.rpgplugin.skills;

import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;

import eu.around_me.rpgplugin.playerstats.RPGPlayerStat;

public abstract class ActiveSkill extends Skill {
	protected ItemStack skillItem;
	protected int cooldown;
	protected int manacost;

	public abstract boolean executeActive( RPGPlayerStat stat, HumanEntity p);
	public abstract boolean needsUnload();
	public abstract void unload();
	
	public ItemStack getItem() {
		return this.skillItem;
	}
	public int getCooldown() {
		return this.cooldown;
	}
	public int getManacost() {
		return manacost;
	}

}
