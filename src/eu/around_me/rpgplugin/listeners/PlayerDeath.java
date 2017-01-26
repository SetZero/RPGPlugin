package eu.around_me.rpgplugin.listeners;

import java.util.Map;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import eu.around_me.rpgplugin.playerstats.RPGPlayerStat;
import net.md_5.bungee.api.ChatColor;

public class PlayerDeath implements Listener{
	private int exploss = 15; //EXP loss in %
	private Map<HumanEntity, RPGPlayerStat> playerStats;

	public PlayerDeath(Map<HumanEntity, RPGPlayerStat> playerStats) {
		this.playerStats = playerStats;
	}
	
	@EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent e) {
		HumanEntity p = (HumanEntity) e.getEntity();
		RPGPlayerStat stat = playerStats.get(p);

		double loss = (100-exploss) / 100D;
		int totalloss = (int) (stat.getEXP() * loss);
		stat.setEXP(totalloss);
		e.getEntity().sendMessage(ChatColor.RED + "You died and lost " + totalloss + " EXP!");
		if(stat.getHasShield()) {
			stat.setShield(stat.getMaxShield());
		}
		stat.setMana(stat.getMaxmana());
	}
}
