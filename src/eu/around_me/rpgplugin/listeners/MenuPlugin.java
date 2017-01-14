package eu.around_me.rpgplugin.listeners;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

import eu.around_me.rpgplugin.menus.MainMenu;
import eu.around_me.rpgplugin.playerstats.RPGPlayerStat;

/**
 * Creates Listener for Main Menu
 * 
 * @author Sebastian
 *
 */
public class MenuPlugin implements Listener {
	private MainMenu menu;
	private Material buildTool;
	private String itemName;

	public MenuPlugin(Map<HumanEntity, RPGPlayerStat> playerStats, Plugin p) {
		menu = new MainMenu(playerStats, p);
	}

	@EventHandler
	public void onPlayerInteractAtEntity(PlayerInteractEvent e) {
	    Action a = e.getAction();
		
		if(buildTool == null) buildTool = Material.BOOK;
		if(itemName == null) itemName = "Skill Book";
		if ((a == Action.PHYSICAL) || (e.getItem() == null) || (e.getItem().getType() != buildTool) ||
				!(e.getItem().getItemMeta().getDisplayName().equals(itemName))) return;
		menu.show(e.getPlayer());
	}
}
