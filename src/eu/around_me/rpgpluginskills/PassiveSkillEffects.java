package eu.around_me.rpgpluginskills;

import org.bukkit.entity.HumanEntity;

public abstract class PassiveSkillEffects {
	
	public abstract void executeEffect(HumanEntity p);
	public abstract void unloadEffect(HumanEntity p);

}