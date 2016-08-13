package menus;

import java.util.Arrays;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;
import org.bukkit.plugin.Plugin;

import net.md_5.bungee.api.ChatColor;
import playerstats.RPGPlayerStat;

public class MainMenu implements Listener {
		private Inventory inv;
		private ItemStack c, s, i;
		private Map<HumanEntity, RPGPlayerStat> stat;
		private Plugin p;
		
		public MainMenu(Map<HumanEntity, RPGPlayerStat> playerStats, Plugin p) {
			this.stat = playerStats;
			this.p = p;
			inv = Bukkit.getServer().createInventory(null, 54, "Main Menu");
			
			s = createAttribute(DyeColor.GREEN, 2, ChatColor.GREEN + "Player Status", "Get your current Player Progress", "");
			i = createAttribute(DyeColor.BLUE, 4, ChatColor.BLUE + "Skill Tree", "View your current Skill Tree", "");
			c = createAttribute(DyeColor.RED, 6, ChatColor.RED + "Placeholder", "don't mind me", ChatColor.RED + "LOL " + ChatColor.WHITE + "+1");
	
			inv.setItem(2, c);
			inv.setItem(4, s);
			inv.setItem(6, i);
	
			Bukkit.getServer().getPluginManager().registerEvents(this, p);
		}
		
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
		  if(e.getInventory().getName() == null || e.getCurrentItem() == null) return;
	      if (!e.getInventory().getName().equalsIgnoreCase(inv.getName())) return;
	      if (e.getCurrentItem().getItemMeta() == null) return;
	      if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Player Status")) {
	            e.setCancelled(true);
	            if(stat.get(e.getWhoClicked()) == null)
	            	e.getWhoClicked().sendMessage(ChatColor.RED + "An Unknown error occured!");
	            else {
	            	stat.get(e.getWhoClicked()).printStats(e.getWhoClicked());
	            }
	            e.getWhoClicked().closeInventory();
	      }
		if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Skill Tree")) {
			e.setCancelled(true);
			e.getWhoClicked().sendMessage("...");
			SkillTreeMenu menu = new SkillTreeMenu(stat, stat.get(e.getWhoClicked()).getSkillTree(), p);
			e.getWhoClicked().closeInventory();
			menu.show(e.getWhoClicked());
		}
		if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Charisma")) {
			e.setCancelled(true);
			e.getWhoClicked().sendMessage("You feel more charismatic now!");
			e.getWhoClicked().closeInventory();
		}
	}
}
