package eu.around_me.rpgplugin.listeners;

import java.util.Map;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import eu.around_me.rpgplugin.libary.AdjacencyMatrix;
import eu.around_me.rpgplugin.libary.JoinHandler;
import eu.around_me.rpgplugin.playerstats.RPGPlayerStat;

/**
 * Register all Players which join the Server and gives them a RPGPlayerStat
 * Object
 * 
 * @author Sebastian
 *
 */
public class PlayerJoin implements Listener {

	private Map<HumanEntity, RPGPlayerStat> playerStats;
	private AdjacencyMatrix skillTree;
	private Plugin plugin;

	public PlayerJoin(Map<HumanEntity, RPGPlayerStat> playerStats, AdjacencyMatrix skillTree, Plugin p) {
		this.playerStats = playerStats;
		this.skillTree = skillTree;
		this.plugin = p;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		JoinHandler.registerPlayer(p, playerStats, skillTree, plugin);
	}
}
