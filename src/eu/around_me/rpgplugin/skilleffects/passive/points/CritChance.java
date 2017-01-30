package eu.around_me.rpgplugin.skilleffects.passive.points;

import java.util.Map;

import org.bukkit.entity.HumanEntity;
import org.bukkit.plugin.Plugin;

import eu.around_me.rpgplugin.playerstats.RPGPlayerStat;
import eu.around_me.rpgplugin.skills.PassiveSkillEffects;
import net.md_5.bungee.api.ChatColor;

public class CritChance extends PassiveSkillEffects {

	private double critChanceAdd = 0.05;
	private Map<HumanEntity, RPGPlayerStat> playerStats;
	
	public CritChance(double chance, Map<HumanEntity, RPGPlayerStat> playerStats) {
		if(chance > 1) {
			System.out.println("CritChance can't be higher than 1!");
		}
		this.playerStats = playerStats;
		critChanceAdd = chance;
	}
	
	@Override
	public void executeEffect(HumanEntity p, Plugin plugin) {
		RPGPlayerStat stat = playerStats.get(p);
		stat.setCritChance(stat.getCritChance() + critChanceAdd);
		if(stat.getCritChance() > 1) {
			p.sendMessage(ChatColor.RED + "Critical Hit Chance limit reached!");
		}

	}

	@Override
	public void unloadEffect(HumanEntity p) {
		RPGPlayerStat stat = playerStats.get(p);
		stat.setCritChance(stat.getCritChance() - critChanceAdd);

	}

}
