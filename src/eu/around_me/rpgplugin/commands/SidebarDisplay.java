package eu.around_me.rpgplugin.commands;

import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import eu.around_me.rpgplugin.playerstats.RPGPlayerStat;
import net.md_5.bungee.api.ChatColor;

public class SidebarDisplay implements CommandExecutor{
	
private Map<HumanEntity, RPGPlayerStat> playerStats;
	
	public SidebarDisplay(Map<HumanEntity, RPGPlayerStat> playerStats) {
		this.playerStats = playerStats;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			if(args.length == 0)
				return false;
			if(args[0].equals("toggleabs")) {
				Player p = (Player) sender;
				RPGPlayerStat stat = playerStats.get((HumanEntity)p);
				
				stat.setShowAbsValuesInSidebar(!stat.isShowAbsValuesInSidebar());
				p.sendMessage(ChatColor.AQUA + "Statinfo now shows " + (stat.isShowAbsValuesInSidebar() ? "absolute" : "relative") + " values");
				stat.getSb().sidebarRefresh();
			}
			
		}
		return true;
	}
}
