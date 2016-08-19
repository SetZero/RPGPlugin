package eu.around_me.rpgplugin.libary;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import eu.around_me.rpgplugin.libary.userfiles.FileHandler;
import eu.around_me.rpgplugin.playerstats.RPGPlayerStat;
import eu.around_me.rpgplugin.scoreboard.Sidebar;

public class JoinHandler {

	private JoinHandler() {
		//Don't call this
	}
	
	public static void registerPlayer(Player player, Map<HumanEntity, RPGPlayerStat> playerStats, AdjacencyMatrix skillTree, Plugin plugin) {
		HumanEntity p = (HumanEntity) player;
		p.sendMessage(ChatColor.DARK_RED + p.getName() + ChatColor.YELLOW + " has joined the game!");
		RPGPlayerStat stat = playerStats.get(p);

		// Try to find the Player in the Players list...
		Iterator<Entry<HumanEntity, RPGPlayerStat>> it = playerStats.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<HumanEntity, RPGPlayerStat> pair = (Map.Entry<HumanEntity, RPGPlayerStat>) it.next();
			HumanEntity h = (HumanEntity) pair.getKey();
			if (h.getName().equals(p.getName())) {
				//Check if Player is already in list...
				playerStats.put(p, (RPGPlayerStat) pair.getValue());
				playerStats.remove(h);
				p.sendMessage(ChatColor.AQUA + "Welcome back, " + h.getName());
				Sidebar sb = new Sidebar(playerStats.get(p), p);
				playerStats.get(p).setSb(sb);
				return;
			}
		}
		//Check if Player in Playerfiles
		FileHandler fh = new FileHandler(playerStats.get(p), p, plugin);
		RPGPlayerStat ps;
		try {
			ps = fh.load(skillTree);
		
			if(ps != null) {
				p.sendMessage(ChatColor.AQUA + "Welcome back, " + p.getName());
				playerStats.put(p, ps);
				
				Sidebar sb = new Sidebar(playerStats.get(p), p);
				playerStats.get(p).setSb(sb);
				return;
			}
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		if (stat == null) {
			p.sendMessage(ChatColor.AQUA + "Welcome new Player!");
			playerStats.put(p, new RPGPlayerStat(0, 0, 0, skillTree, fh));
			Sidebar sb = new Sidebar(playerStats.get(p), p);
			playerStats.get(p).setSb(sb);
		} else {
			stat.setupSkillEffects(p);
		}
	}

}
