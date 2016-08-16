package skilleffects.points;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import skills.PassiveSkillEffects;

public class Strength extends PassiveSkillEffects{

	double strengthModifier = 1;
	
	@Override
	public void executeEffect(HumanEntity p) {
		// TODO Auto-generated method stub
		if(p instanceof Player) {
			Player pl = (Player) p;
			pl.setMaxHealth(pl.getMaxHealth() + strengthModifier);
		}
	}

}
