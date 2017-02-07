package eu.around_me.rpgplugin.commands;

import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import eu.around_me.rpgplugin.playerstats.RPGPlayerStat;

public class AdminCMD implements CommandExecutor{
	private Map<HumanEntity, RPGPlayerStat> playerStats;
	
	public AdminCMD(Map<HumanEntity, RPGPlayerStat> playerStats) {
		this.playerStats = playerStats;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg3) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			RPGPlayerStat stat = playerStats.get((HumanEntity)p);
			if(arg3[0].equals("crit")) {
				stat.setCritChance(Integer.valueOf(arg3[1])/100D);
				p.sendMessage("Crit now @" + stat.getCritChance());
			} else if(arg3[0].equals("evade")) {
				stat.setEvasionRating(Integer.valueOf(arg3[1])/100D);
				p.sendMessage("Evasion now @" + stat.getEvasionRating());
			}
		}
		return false;
	}

}
