package eu.around_me.rpgplugin.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import eu.around_me.rpgplugin.playerstats.RPGPlayerStat;
import net.md_5.bungee.api.ChatColor;

public class Sidebar {
	RPGPlayerStat stat;
	HumanEntity player;
	
	public Sidebar(RPGPlayerStat stat, HumanEntity player) {
		this.stat = stat;
		this.player = player;
		if(stat != null && stat.getShowSidebar()) {
			sidebarRefresh();
		}
	}
	
	public void sidebarRefresh() {
		Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective objective = sb.registerNewObjective(player.getName(), "dummy");
		objective.setDisplayName(ChatColor.BOLD + "" + ChatColor.GOLD + player.getName());
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.getScore(ChatColor.WHITE + "   ").setScore(9);
		
		objective.getScore("Level: " + stat.getLevel()).setScore(8);
		objective.getScore(stat.printFullLevelProgress()).setScore(7);
		objective.getScore(stat.printFullMana(stat.getManabarcolor())).setScore(7);
		if(stat.getHasShield())
			objective.getScore(stat.printFullShield(ChatColor.LIGHT_PURPLE)).setScore(7);
		objective.getScore(ChatColor.WHITE + "    ").setScore(6);
		objective.getScore(ChatColor.DARK_GREEN + "Dexterity: " + ChatColor.WHITE + stat.getDex()).setScore(5);
		objective.getScore(ChatColor.DARK_RED + "Strength: " + ChatColor.WHITE + stat.getStr()).setScore(4);
		objective.getScore(ChatColor.DARK_AQUA + "Intelligence: " + ChatColor.WHITE + stat.getWis()).setScore(3);
		
		if(player instanceof Player) {
			Player p = (Player) player;
			if(stat != null && stat.getShowSidebar()) {
				p.setScoreboard(sb);
			}
		}
	}
}
