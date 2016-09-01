package eu.around_me.rpgplugin.libary.handlers;

import java.util.List;
import java.util.Map;

import org.bukkit.entity.HumanEntity;

import eu.around_me.rpgplugin.libary.userfiles.FileHandler;
import eu.around_me.rpgplugin.playerstats.RPGPlayerStat;
import eu.around_me.rpgplugin.skills.ActiveSkill;
import eu.around_me.rpgplugin.skills.PassiveSkill;
import eu.around_me.rpgplugin.skills.PassiveSkillEffects;
import eu.around_me.rpgplugin.skills.Skill;

public class QuitHandler {

	private QuitHandler() {
		// don't call me
	}
	
	public static void playerQuit(Map<HumanEntity, RPGPlayerStat> playerStats, HumanEntity p) {
		//Check if Player in Playerfiles
		List<Skill> learned = playerStats.get(p).getLearnedSkills();
		for(Skill s : learned) {
			if(s instanceof PassiveSkill) {
				PassiveSkill ps = (PassiveSkill) s;
				PassiveSkillEffects pse = ps.getSkillEffect();
				//remove all effects from player
				if(pse != null)
					pse.unloadEffect(p);
			} else if(s instanceof ActiveSkill) {
				ActiveSkill as = (ActiveSkill) s;
				if(as.needsUnload()) {
					as.unload();
				}
			}
		}
		
		//Save all new progress
		FileHandler fh = playerStats.get(p).getFh();
		
		if(fh != null){
			fh.setStats(playerStats.get(p));
			fh.save();
			
		}
	}

}
