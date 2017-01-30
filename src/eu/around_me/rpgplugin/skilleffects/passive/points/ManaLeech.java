package eu.around_me.rpgplugin.skilleffects.passive.points;

import java.util.Map;

import org.bukkit.entity.HumanEntity;
import org.bukkit.plugin.Plugin;

import eu.around_me.rpgplugin.playerstats.RPGPlayerStat;
import eu.around_me.rpgplugin.skills.PassiveSkillEffects;

public class ManaLeech extends PassiveSkillEffects {
	private double manaLeechAdd = 0.1;
	private Map<HumanEntity, RPGPlayerStat> playerStats;
	
	public ManaLeech(double chance, Map<HumanEntity, RPGPlayerStat> playerStats) {
		this.playerStats = playerStats;
		manaLeechAdd = chance;
	}
	
	@Override
	public void executeEffect(HumanEntity p, Plugin plugin) {
		RPGPlayerStat stat = playerStats.get(p);
		stat.setManaLeech(stat.getManaLeech() + manaLeechAdd);

	}

	@Override
	public void unloadEffect(HumanEntity p) {
		RPGPlayerStat stat = playerStats.get(p);
		stat.setManaLeech(stat.getManaLeech() - manaLeechAdd);

	}

}
