package skilleffects.points;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import skills.PassiveSkillEffects;

public class Dexterity extends PassiveSkillEffects{

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

}
