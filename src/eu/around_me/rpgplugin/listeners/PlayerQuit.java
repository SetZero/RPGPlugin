package eu.around_me.rpgplugin.listeners;

import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import eu.around_me.rpgplugin.libary.handlers.QuitHandler;
import eu.around_me.rpgplugin.playerstats.RPGPlayerStat;

/**
 * Register all Players which join the Server and gives them a RPGPlayerStat
 * Object
 * 
 * @author Sebastian
 *
 */
public class PlayerQuit implements Listener {

	private Map<HumanEntity, RPGPlayerStat> playerStats;

	public PlayerQuit(Map<HumanEntity, RPGPlayerStat> playerStats) {
		this.playerStats = playerStats;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerQuit(PlayerQuitEvent event) {
		HumanEntity p = (HumanEntity) event.getPlayer();
		p.sendMessage(ChatColor.DARK_RED + p.getName() + ChatColor.YELLOW + " has left the game!");
		
		
		QuitHandler.playerQuit(playerStats, p);
	}
}
