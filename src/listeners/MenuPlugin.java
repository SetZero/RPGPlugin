package listeners;

import java.util.Map;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

import menus.MainMenu;
import playerstats.RPGPlayerStat;

/**
 * Creates Listener for Main Menu
 * 
 * @author Sebastian
 *
 */
public class MenuPlugin implements Listener {
	private MainMenu menu;

	public MenuPlugin(Map<HumanEntity, RPGPlayerStat> playerStats, Plugin p) {
		menu = new MainMenu(playerStats, p);
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK))
			return;
		menu.show(e.getPlayer());
	}
}
