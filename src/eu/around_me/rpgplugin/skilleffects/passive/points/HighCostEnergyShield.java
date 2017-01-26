package eu.around_me.rpgplugin.skilleffects.passive.points;

import java.util.Map;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import eu.around_me.rpgplugin.playerstats.RPGPlayerStat;
import eu.around_me.rpgplugin.skills.PassiveSkillEffects;

public class HighCostEnergyShield extends PassiveSkillEffects{
	private Map<HumanEntity, RPGPlayerStat> playerStats;
	private int shieldbonus = 20;
	private int flatamount = 20;

	public HighCostEnergyShield(Map<HumanEntity, RPGPlayerStat> playerStats, int shieldbonus, int flatamount) {
		this.playerStats = playerStats;
		this.shieldbonus = shieldbonus;
		this.flatamount = flatamount;
	}
	
	@Override
	public void executeEffect(HumanEntity p, Plugin plugin) {
		RPGPlayerStat stat = playerStats.get(p);
		double shield = (100+shieldbonus) / 100D;
		int totalshield = (int) ((stat.getMaxShield() + flatamount) * shield);
		
		stat.setMaxShield(totalshield);
		stat.setOmniShield(true);
		Player pl = (Player) p;
		AttributeInstance health = pl.getAttribute(Attribute.GENERIC_MAX_HEALTH);
		health.setBaseValue(1);
		
	}

	@Override
	public void unloadEffect(HumanEntity p) {
		if(p instanceof Player) {
			Player pl = (Player) p;
			AttributeInstance health = pl.getAttribute(Attribute.GENERIC_MAX_HEALTH);
			health.setBaseValue(20);
		}
		
		RPGPlayerStat stat = playerStats.get(p);
		double shield = (100-shieldbonus) / 100D;
		int totalshield = (int) ((stat.getMaxShield() - flatamount) * shield);
		stat.setMaxShield(totalshield);
	}

}
