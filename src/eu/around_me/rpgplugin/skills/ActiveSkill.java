package eu.around_me.rpgplugin.skills;

import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;

import eu.around_me.rpgplugin.playerstats.RPGPlayerStat;

public abstract class ActiveSkill extends Skill {

	public abstract boolean executeActive( RPGPlayerStat stat, HumanEntity p);
	public abstract ItemStack getItem();
	public abstract int getCooldown();
	public abstract int getManacost();

}
