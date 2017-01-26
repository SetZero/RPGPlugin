package eu.around_me.rpgplugin.menus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;
import org.bukkit.plugin.Plugin;

import eu.around_me.rpgplugin.libary.AdjacencyMatrix;
import eu.around_me.rpgplugin.libary.GlassColor;
import eu.around_me.rpgplugin.playerstats.RPGPlayerStat;
import eu.around_me.rpgplugin.skills.ActiveSkill;
import eu.around_me.rpgplugin.skills.PassiveSkill;
import eu.around_me.rpgplugin.skills.PassiveSkillPoint;
import eu.around_me.rpgplugin.skills.Skill;
import net.md_5.bungee.api.ChatColor;

public class SkillTreeMenu implements Listener {
	private Inventory inv;
	private Map<ItemStack, Skill> SkillItems = new HashMap<ItemStack, Skill>();
	private Map<HumanEntity, RPGPlayerStat> stat;
	private Plugin p;
	private SkillTreeMenu spawnedSkillTreeMenu;
	private int startingpoint;
	
	public SkillTreeMenu(int startingpoint, Map<HumanEntity, RPGPlayerStat> playerStats, RPGPlayerStat stats, Plugin p) {
		this.startingpoint = startingpoint;
		this.stat = playerStats;
		inv = Bukkit.getServer().createInventory(null, 54, "Skill Tree (#" + startingpoint + ")");
		List<Skill> connectedNodes = stats.getSkillTree().outEdgesSkills(startingpoint);
		this.p = p;
		
		List<String> error = new LinkedList<String>();
		error.add("An unknown error occured!");
		ItemStack last = createAttribute(DyeColor.RED, 1, ChatColor.RED + "ERROR", error);
		int pos = 0;
		for(Skill s: connectedNodes) {
			if(s instanceof PassiveSkillPoint) {
				PassiveSkillPoint ps = (PassiveSkillPoint) s;
				if(stats.getLearnedSkills().contains(s)) {
					List<String> descText = new LinkedList<String>();
					String[] desc = ps.getDescription().split("\\r?\\n");
					for(String d: desc) {
						descText.add(d);
					}
					descText.add(ps.getChatColor() + ps.getName().substring(0, 3).toUpperCase() + ChatColor.WHITE + " +1");
					last = createLearnedAttribute(ps.getItemColor(), 1, ps.getChatColor() + ps.getName(), descText);
				} else {
					
					List<String> descText = new LinkedList<String>();
					String[] desc = ps.getDescription().split("\\r?\\n");
					for(String d: desc) {
						descText.add(d);
					}
					descText.add(ps.getChatColor() + ps.getName().substring(0, 3).toUpperCase() + ChatColor.WHITE + " +1");
					
							
					last = createAttribute(ps.getItemColor(), 1, ps.getChatColor() + ps.getName(), descText);
				}
			} else if(s instanceof ActiveSkill) {
				ActiveSkill as = (ActiveSkill) s;
				if(stats.getLearnedSkills().contains(s)) {
					last = createActive(as.getItem(), 1, as.getChatColor() + as.getName(), as.getDescription(), ChatColor.DARK_RED + "Learned " + as.getChatColor() + as.getName());
				} else {
					last = createActive(as.getItem(), 1, as.getChatColor() + as.getName(), as.getDescription(), "Learn " + as.getChatColor() + as.getName());
				}
			}
			if(s.getID() != startingpoint || startingpoint == 0) {
				SkillItems.put(last, s);
				inv.setItem(pos, last);
				pos++;
			}
		}
		
		Bukkit.getServer().getPluginManager().registerEvents(this, p);
		
	}
	
	private ItemStack createAttribute(DyeColor dc, int skillcount, String name, List<String> desc) {
		//System.out.println(dc.toString() + " | " + skillcount + " | " + name);
		ItemStack i = new Wool(dc).toItemStack(skillcount);
		ItemMeta im = i.getItemMeta();
        im.setDisplayName(name);
		im.setLore(desc);
		i.setItemMeta(im);
		return i;
	}
	
	private ItemStack createLearnedAttribute(DyeColor dc, int skillcount, String name, List<String> desc) {
		ItemStack i = new ItemStack(Material.STAINED_GLASS, 1, (short) GlassColor.DyetoGlass(dc));
		ItemMeta im = i.getItemMeta();
        im.setDisplayName(name);
		im.setLore(desc);
		i.setItemMeta(im);
		return i;
	}
	
	private ItemStack createActive(ItemStack is, int skillcount, String name, String desc, String desc2) {
		ItemMeta im = is.getItemMeta();
        im.setDisplayName(name);
		im.setLore(Arrays.asList(desc, desc2));
		is.setItemMeta(im);
		return is;
	}
	
	/*private ItemStack createLearnedActive(ItemStack is, int skillcount, String name, String desc, String desc2) {
		ItemMeta im = is.getItemMeta();
        im.setDisplayName(name);
		im.setLore(Arrays.asList(desc, desc2));
		//im.addEnchant(Enchantment.DURABILITY, 1, true);
		is.setItemMeta(im);
		return is;
	}*/
	
	public void show(HumanEntity p) {
		p.openInventory(inv);
		
	}
	
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if(e.getInventory().getName() == null || e.getCurrentItem() == null) return;
		if (!e.getInventory().getName().equalsIgnoreCase(inv.getName())) return;
		if (e.getCurrentItem().getItemMeta() == null) return;
		RPGPlayerStat s = stat.get(e.getWhoClicked());
		//System.out.println("Called: " + s);
		Skill learn = SkillItems.get(e.getCurrentItem());
		if(s == null) return;
		if(learn != null) {
			HandlerList.unregisterAll(spawnedSkillTreeMenu);
			HandlerList.unregisterAll(this);
			e.setCancelled(true);
			//System.out.println("Called: " + s);
			if(s.learnSkill(learn)) {
				e.getWhoClicked().sendMessage("You learned: " + learn.getChatColor() + learn.getName());
				s.getSb().sidebarRefresh();
				if(learn instanceof PassiveSkill) {
					PassiveSkill ps = (PassiveSkill) learn;
					if(ps.getSkillEffect() != null) {
						ps.getSkillEffect().executeEffect(e.getWhoClicked(), p);
					}
				}
				SkillItems.remove(e.getWhoClicked());
			} else {
				if(s.getLearnedSkills().contains(learn)) {
					if(learn.getID() != startingpoint) {
						spawnedSkillTreeMenu = new SkillTreeMenu(learn.getID(), stat, stat.get(e.getWhoClicked()), p);
						e.getWhoClicked().closeInventory();
						spawnedSkillTreeMenu.show(e.getWhoClicked());
					} else {
						e.getWhoClicked().sendMessage(ChatColor.DARK_RED + "This is the same skill!");
					}
					return;
				} else {
					e.getWhoClicked().sendMessage(ChatColor.DARK_RED + "You can't learn this skill!");
				}
			}
			e.getWhoClicked().closeInventory();
		}
			
	}
	  
	public static void findNodes(AdjacencyMatrix am, Skill node, List<Integer> foundIDs) {
		
		//System.out.println("Connected Nodes to " + node.getName() + "(" + node.getID() + "): ");
    	List<Skill> connectedNodes = am.inEdgesSkills(node.getID());
    	/*int i = 0;
    	for (Skill temp : connectedNodes) {
    		i++;
			//System.out.println(i + ")" + temp.getName() + "(" + temp.getID() + ")");
		}
    	//System.out.println("");*/
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
