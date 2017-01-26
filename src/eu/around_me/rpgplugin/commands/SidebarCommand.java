package eu.around_me.rpgplugin.commands;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import eu.around_me.rpgplugin.playerstats.RPGPlayerStat;
import net.md_5.bungee.api.ChatColor;

public class SidebarCommand implements CommandExecutor{
	private Map<HumanEntity, RPGPlayerStat> playerStats;
	
	public SidebarCommand(Map<HumanEntity, RPGPlayerStat> playerStats) {
		this.playerStats = playerStats;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			RPGPlayerStat stat = playerStats.get((HumanEntity)p);
			
			stat.setShowSidebar(!stat.getShowSidebar());
			p.sendMessage(ChatColor.AQUA + "Statinfo is now " + (stat.getShowSidebar() ? "active" : "deactivated"));
			if(!stat.getShowSidebar()) {
				p.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
			} else {
				stat.getSb().sidebarRefresh();
			}
		}
		return true;
	}
}
