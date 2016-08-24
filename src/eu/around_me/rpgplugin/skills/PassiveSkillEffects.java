package eu.around_me.rpgplugin.skills;

import org.bukkit.entity.HumanEntity;
import org.bukkit.plugin.Plugin;

public abstract class PassiveSkillEffects {
	
	public abstract void executeEffect(HumanEntity p, Plugin plugin);
	public abstract void unloadEffect(HumanEntity p);

}
