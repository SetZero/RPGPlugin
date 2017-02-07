package eu.around_me.rpgplugin.menus;

import java.util.Arrays;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;
import org.bukkit.plugin.Plugin;

import eu.around_me.rpgplugin.playerstats.RPGPlayerStat;
import net.md_5.bungee.api.ChatColor;


/**
 * Creates the Main Menu
 * @author Sebastian
 *
 */
public class MainMenu implements Listener {
		private Inventory inv;
		private ItemStack c, s, i;
		//private ItemStack dbg_1;
		private Map<HumanEntity, RPGPlayerStat> stat;
		private Plugin p;
		private SkillTreeMenu spawnedSkillTreeMenu;
		
		public MainMenu(Map<HumanEntity, RPGPlayerStat> playerStats, Plugin p) {
			this.stat = playerStats;
			this.p = p;
			inv = Bukkit.getServer().createInventory(null, 54, "Main Menu");
			
			//All Menu Elements
			s = createAttribute(DyeColor.GREEN, 1, ChatColor.GREEN + "Player Status", "Get your current Player Progress", "");
			i = createAttribute(DyeColor.BLUE, 1, ChatColor.BLUE + "Skill Tree", "View your current Skill Tree", "");
			c = createAttribute(DyeColor.RED, 1, ChatColor.RED + "EXIT", "leave menu", "");
	
			//dbg_1 = createAttribute(DyeColor.BLACK, 1, ChatColor.RED + "Level Up", "Take me to Hell!", ChatColor.RED + "LVL " + ChatColor.WHITE + "+1");
			
			//Sets their position
			inv.setItem(2, c);
			inv.setItem(4, s);
			inv.setItem(6, i);
			//inv.setItem(11, dbg_1);
	
			Bukkit.getServer().getPluginManager().registerEvents(this, p);
		}
		
		/**
		 * Creates an Attribute
		 * @param dc The Color of the Attribute
		 * @param skillcount The Amount of the Skill (e.g. Level 0-3)
		 * @param name The Name of the Skill
		 * @param desc The Description of the Skill
		 * @param desc2 The Second row of the Skill description
		 * @return The created Item
		 */
		private ItemStack createAttribute(DyeColor dc, int skillcount, String name, String desc, String desc2) {
			ItemStack i = new Wool(dc).toItemStack(skillcount);
			ItemMeta im = i.getItemMeta();
	        im.setDisplayName(name);
			im.setLore(Arrays.asList(desc, desc2));
			i.setItemMeta(im);
			return i;
		}
		
		public void show(Player p) {
			p.openInventory(inv);
		}
	
	  @EventHandler
	  public void onInventoryClick(InventoryClickEvent e) {
		  if(e.getInventory().getName() == null || e.getCurrentItem() == null) return; //Checks if Player presses something nonexistent 
	      if (!e.getInventory().getName().equalsIgnoreCase(inv.getName())) return; //Check if right Inventory
	      if (e.getCurrentItem().getItemMeta() == null) return; //Check if Item exists
	      //Go trough all Menu Points
	      if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Player Status")) {
	    	  	//Cancel normal action (Drag&Drop)
	            e.setCancelled(true);
	            //Check if Player exists and has a RPGPlayerStat Object
	            if(stat.get(e.getWhoClicked()) == null)
	            	e.getWhoClicked().sendMessage(ChatColor.RED + "An Unknown error occured! (Player not in List)");
	            else {
	            	//Print Stats to Player
	            	stat.get(e.getWhoClicked()).printStats(e.getWhoClicked());
	            }
	            e.getWhoClicked().closeInventory();
	      }
		if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Skill Tree")) {
			e.setCancelled(true);
			//Delete all previous Skill Trees for Player
			HandlerList.unregisterAll(spawnedSkillTreeMenu);
			spawnedSkillTreeMenu = new SkillTreeMenu(0, stat, stat.get(e.getWhoClicked()), p);
			e.getWhoClicked().closeInventory();
			spawnedSkillTreeMenu.show(e.getWhoClicked());
		}
		if(e.getCurrentItem().getItemMeta().getDisplayName().contains("EXIT")) {
			e.setCancelled(true);
			e.getWhoClicked().closeInventory();
		}
	}
}
