package eu.around_me.rpgplugin.skilleffects.passive.points;

import java.util.Map;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import eu.around_me.rpgplugin.libary.Manatypes;
import eu.around_me.rpgplugin.playerstats.RPGPlayerStat;
import eu.around_me.rpgplugin.skills.PassiveSkillEffects;

public class ManaType extends PassiveSkillEffects implements Listener{

	Plugin plugin;
	HumanEntity p;
	
	private Manatypes type = Manatypes.MANA;
	Map<HumanEntity, RPGPlayerStat> playerStats;
	
	public ManaType(Manatypes type, Map<HumanEntity, RPGPlayerStat> playerStats) {
		this.type = type;
		this.playerStats = playerStats;
	}

	@Override
	public void executeEffect(HumanEntity p, Plugin plugin) {
		this.plugin = plugin;
		this.p = p;
		
		RPGPlayerStat stat = playerStats.get(p);
		if(stat != null) {
			stat.setManatype(type);
			if(type == Manatypes.LIFE) {
				int maxhealth = (int) p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
				stat.setMaxmana(maxhealth);
				stat.setMana((int) p.getHealth());
			}
		}
	}

	@Override
	public void unloadEffect(HumanEntity p) {
	}

}
