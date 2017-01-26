package eu.around_me.rpgplugin.skilleffects.passive.points;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import eu.around_me.rpgplugin.skills.PassiveSkillEffects;

public class Strength extends PassiveSkillEffects implements Listener{

	double strengthModifier = 1;
	
	@Override
	public void executeEffect(HumanEntity p, Plugin plugin) {
		// TODO Auto-generated method stub
		if(p instanceof Player) {
			Player pl = (Player) p;
			AttributeInstance health = pl.getAttribute(Attribute.GENERIC_MAX_HEALTH);
			health.setBaseValue(health.getValue() + strengthModifier);
		}
	}

	@Override
	public void unloadEffect(HumanEntity p) {
		// TODO Auto-generated method stub
		if(p instanceof Player) {
			Player pl = (Player) p;
			AttributeInstance health = pl.getAttribute(Attribute.GENERIC_MAX_HEALTH);
			health.setBaseValue(20);
		}
	}

}
