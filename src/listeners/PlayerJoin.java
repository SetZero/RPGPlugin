package listeners;

import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import eu.around_me.rpgplugin.libary.AdjacencyMatrix;
import playerstats.RPGPlayerStat;

/**
 * Register all Players which join the Server and gives them a RPGPlayerStat Object
 * @author Sebastian
 *
 */
public class PlayerJoin implements Listener{
	
	private Map<HumanEntity, RPGPlayerStat> playerStats;
	private AdjacencyMatrix skillTree;

	public PlayerJoin(Map<HumanEntity, RPGPlayerStat> playerStats, AdjacencyMatrix skillTree) {
		this.playerStats = playerStats;
		this.skillTree = skillTree;
	}
	 @EventHandler  (priority = EventPriority.HIGHEST)
     public void onPlayerJoin(PlayerJoinEvent event)
     {
		 HumanEntity p = (HumanEntity)event.getPlayer();
		 p.sendMessage(ChatColor.DARK_RED + p.getName() + ChatColor.YELLOW + " has joined the game!");
		 RPGPlayerStat stat = playerStats.get(p);
		 if(stat == null) {
			 p.sendMessage(ChatColor.AQUA + "Welcome new Player!");
			 playerStats.put(p, new RPGPlayerStat(0, 0, 0, skillTree));
		 }
     }
}
