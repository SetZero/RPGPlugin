package eu.around_me.rpgplugin.skilleffects.passive.points;

import java.util.Map;

import org.bukkit.entity.HumanEntity;
import org.bukkit.plugin.Plugin;

import eu.around_me.rpgplugin.playerstats.RPGPlayerStat;
import eu.around_me.rpgplugin.skills.PassiveSkillEffects;
import net.md_5.bungee.api.ChatColor;

public class EvasionChance extends PassiveSkillEffects {
	private double evasionCap = 0.5;
	private double evasionChanceAdd = 0.05;
	private Map<HumanEntity, RPGPlayerStat> playerStats;
	
	public EvasionChance(double chance, Map<HumanEntity, RPGPlayerStat> playerStats) {
		if(chance > evasionCap) {
			System.out.println("Evasion Chance can't be higher than 1!");
		} else {
			this.playerStats = playerStats;
			evasionChanceAdd = chance;
		}
	}
	
	@Override
	public void executeEffect(HumanEntity p, Plugin plugin) {
		RPGPlayerStat stat = playerStats.get(p);
		stat.setEvasionRating(stat.getEvasionRating() + evasionChanceAdd);
		if(stat.getCritChance() > evasionCap) {
			p.sendMessage(ChatColor.RED + "Evasion Chance limit reached!");
		}

	}

	@Override
	public void unloadEffect(HumanEntity p) {
		RPGPlayerStat stat = playerStats.get(p);
		stat.setEvasionRating(stat.getEvasionRating() - evasionChanceAdd);

	}

}
