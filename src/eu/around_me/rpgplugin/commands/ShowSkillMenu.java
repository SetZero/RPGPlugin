package eu.around_me.rpgplugin.commands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import eu.around_me.rpgplugin.menus.MainMenu;
import eu.around_me.rpgplugin.playerstats.RPGPlayerStat;

public class ShowSkillMenu implements CommandExecutor{
	private Map<HumanEntity, MainMenu> menu = new HashMap<HumanEntity, MainMenu>();
	private Map<HumanEntity, RPGPlayerStat> playerStats;
	private Plugin plugin;
	
	public ShowSkillMenu(Map<HumanEntity, RPGPlayerStat> playerStats, Plugin p) {
		this.playerStats = playerStats;
		this.plugin = p;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			HumanEntity hp = (HumanEntity) sender;
			MainMenu playermenu = menu.get(hp);
			if(playermenu == null) {
				playermenu = new MainMenu(playerStats, plugin);
				menu.put(hp, playermenu);
			}
			
			playermenu.show(p);
		}
		return true;
	}
}
