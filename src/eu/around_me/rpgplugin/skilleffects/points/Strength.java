package eu.around_me.rpgplugin.skilleffects.points;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import eu.around_me.rpgplugin.skills.PassiveSkillEffects;

public class Strength extends PassiveSkillEffects implements Listener{

	double strengthModifier = 1;
	
	@Override
	public void executeEffect(HumanEntity p) {
		// TODO Auto-generated method stub
		if(p instanceof Player) {
			Player pl = (Player) p;
			pl.setMaxHealth(pl.getMaxHealth() + strengthModifier);
		}
	}

	@Override
	public void unloadEffect(HumanEntity p) {
		// TODO Auto-generated method stub
		
	}

}
