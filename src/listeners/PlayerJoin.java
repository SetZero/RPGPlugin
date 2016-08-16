package listeners;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import eu.around_me.rpgplugin.libary.AdjacencyMatrix;
import playerstats.RPGPlayerStat;

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

	public PlayerJoin(Map<HumanEntity, RPGPlayerStat> playerStats, AdjacencyMatrix skillTree) {
		this.playerStats = playerStats;
		this.skillTree = skillTree;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		HumanEntity p = (HumanEntity) event.getPlayer();
		p.sendMessage(ChatColor.DARK_RED + p.getName() + ChatColor.YELLOW + " has joined the game!");
		RPGPlayerStat stat = playerStats.get(p);

		// Try to find the Player in the Players list...
		Iterator<Entry<HumanEntity, RPGPlayerStat>> it = playerStats.entrySet().iterator();
		System.out.println("Checking Player");
		System.out.println("SIZE: " + playerStats.size());
		while (it.hasNext()) {
			System.out.println("Start...");
			Map.Entry<HumanEntity, RPGPlayerStat> pair = (Map.Entry<HumanEntity, RPGPlayerStat>) it.next();
			HumanEntity h = (HumanEntity) pair.getKey();
			if (h.getName().equals(p.getName())) {
				playerStats.put(p, (RPGPlayerStat) pair.getValue());
				playerStats.remove(h);
				p.sendMessage(ChatColor.AQUA + "Welcome back, " + h.getName());
				return;
			} else {
				System.out.println(h.getName() + " != " + p.getName());
			}
		}

		if (stat == null) {
			p.sendMessage(ChatColor.AQUA + "Welcome new Player!");
			playerStats.put(p, new RPGPlayerStat(0, 0, 0, skillTree));
		} else {
			stat.setupSkillEffects(p);
		}
	}
}
