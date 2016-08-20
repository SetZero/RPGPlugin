package eu.around_me.rpgplugin.skilleffects.passive.points;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import eu.around_me.rpgplugin.skills.PassiveSkillEffects;

public class Dexterity extends PassiveSkillEffects implements Listener{

	float walkingModifier = 0.005f;
	
	@Override
	public void executeEffect(HumanEntity p) {
		// TODO Auto-generated method stub
		if(p instanceof Player) {
			Player pl = (Player) p;
			if((pl.getWalkSpeed() + walkingModifier) <= 1)
				pl.setWalkSpeed(pl.getWalkSpeed() + walkingModifier);
		}
	}

	@Override
	public void unloadEffect(HumanEntity p) {
		// TODO Auto-generated method stub
		
	}

}
