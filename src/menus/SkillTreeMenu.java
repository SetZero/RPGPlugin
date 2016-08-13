package menus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;
import org.bukkit.plugin.Plugin;

import eu.around_me.rpgplugin.libary.AdjacencyMatrix;
import eu.around_me.rpgplugin.libary.SkillPoints;
import net.md_5.bungee.api.ChatColor;
import playerstats.RPGPlayerStat;
import skills.PassiveSkillPoint;
import skills.Skill;

public class SkillTreeMenu implements Listener {
	private Inventory inv;
	private List<ItemStack> SkillItems = new ArrayList<ItemStack>();
	private Map<HumanEntity, RPGPlayerStat> stat;
	
	public SkillTreeMenu(Map<HumanEntity, RPGPlayerStat> playerStats, AdjacencyMatrix skillTree, Plugin p) {
		this.stat = playerStats;
		inv = Bukkit.getServer().createInventory(null, 54, "Skill Tree");
		List<Skill> connectedNodes = skillTree.outEdgesSkills(0);
		
		ItemStack last = createAttribute(DyeColor.RED, 6, ChatColor.RED + "Placeholder", "don't mind me", ChatColor.RED + "LOL " + ChatColor.WHITE + "+1");
		int pos = 0;
		for(Skill s: connectedNodes) {
			if(s instanceof PassiveSkillPoint) {
				PassiveSkillPoint ps = (PassiveSkillPoint) s;
				last = createAttribute(ps.getItemColor(), 6, ps.getChatColor() + ps.getName(), ps.getDescription(), ps.getChatColor() + ps.getName().substring(0, 3).toUpperCase() + ChatColor.WHITE + " +1");
			}
			SkillItems.add(last);
			inv.setItem(pos, last);
			pos++;
		}
		
	}
	
	private ItemStack createAttribute(DyeColor dc, int skillcount, String name, String... desc) {
		ItemStack i = new Wool(dc).toItemStack(skillcount);
		ItemMeta im = i.getItemMeta();
        im.setDisplayName(name);
		im.setLore(Arrays.asList(desc));
		i.setItemMeta(im);
		return i;
	}
	
	/*private ItemStack createActive(Material dc, int skillcount, String name, String desc, String desc2) {
		ItemStack i = new ItemStack(dc);
		ItemMeta im = i.getItemMeta();
        im.setDisplayName(name);
		im.setLore(Arrays.asList(desc, desc2));
		i.setItemMeta(im);
		return i;
	}*/
	
	public void show(HumanEntity p) {
		p.openInventory(inv);
		
	}
	
	  @EventHandler
  public void onInventoryClick(InventoryClickEvent e) {
          /*if (!e.getInventory().getName().equalsIgnoreCase(inv.getName())) return;
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
			e.getWhoClicked().closeInventory();
		}
		if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Charisma")) {
			e.setCancelled(true);
			e.getWhoClicked().sendMessage("You feel more charismatic now!");
			e.getWhoClicked().closeInventory();
		}*/
		if(e.getInventory().getName() == null || e.getCurrentItem() == null) return;
		if (!e.getInventory().getName().equalsIgnoreCase(inv.getName())) return;
		if (e.getCurrentItem().getItemMeta() == null) return;
		RPGPlayerStat s = stat.get(e.getWhoClicked());
		//s.learnSkill(skillid, position);
		
	}
	  
	public static void findNodes(AdjacencyMatrix am, Skill node, List<Integer> foundIDs) {
		
		System.out.println("Connected Nodes to " + node.getName() + "(" + node.getID() + "): ");
    	List<Skill> connectedNodes = am.inEdgesSkills(node.getID());
    	int i = 0;
    	for (Skill temp : connectedNodes) {
    		i++;
			System.out.println(i + ")" + temp.getName() + "(" + temp.getID() + ")");
		}
    	System.out.println("");
    	for (Skill temp : connectedNodes) {
			for (int id : foundIDs) {
				if(id == node.getID())
					return;
			}
			foundIDs.add(node.getID());
			findNodes(am, temp, foundIDs);
    	}
	}
}
