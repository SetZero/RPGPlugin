package eu.around_me.rpgplugin.skilleffects.passive.points;

import java.util.Map;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import eu.around_me.rpgplugin.libary.ShieldRegenTypes;
import eu.around_me.rpgplugin.playerstats.RPGPlayerStat;
import eu.around_me.rpgplugin.skills.PassiveSkillEffects;

public class EnergyShield extends PassiveSkillEffects implements Listener{

	Plugin plugin;
	HumanEntity p;
	
	private ShieldRegenTypes type = ShieldRegenTypes.OFFBATTLE;
	Map<HumanEntity, RPGPlayerStat> playerStats;
	private int shieldBonus = 0;
	
	public EnergyShield(ShieldRegenTypes type, int shieldBonus, Map<HumanEntity, RPGPlayerStat> playerStats) {
		this.type = type;
		this.playerStats = playerStats;
		this.shieldBonus = shieldBonus;
	}

	@Override
	public void executeEffect(HumanEntity p, Plugin plugin) {
		this.plugin = plugin;
		this.p = p;
		
		RPGPlayerStat stat = playerStats.get(p);
		if(stat != null) {
			if(!stat.getHasShield()) {
				stat.setHasShield(true);
			}
			stat.setShieldRegenType(type);
			stat.setShield(stat.getShield() + shieldBonus);
			stat.setMaxShield(stat.getMaxShield() + shieldBonus);
		}
	}

	@Override
	public void unloadEffect(HumanEntity p) {
	}

}
