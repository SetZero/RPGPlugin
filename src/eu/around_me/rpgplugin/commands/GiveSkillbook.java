package eu.around_me.rpgplugin.commands;

import java.util.LinkedList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class GiveSkillbook implements CommandExecutor{
	
	public GiveSkillbook() {
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			
			ItemStack epic = new ItemStack(Material.BOOK);
	        ItemMeta bm = epic.getItemMeta();
	        bm.setDisplayName("Skill Book");
	        List<String> lore = new LinkedList<String>();
	        lore.add("Use this book to view you stats!");
	        bm.setLore(lore);
	        epic.setItemMeta(bm);
			
			final int size = p.getInventory().getContents().length;
			if(size < 54){
                p.getInventory().addItem(epic);
                return true;
            }
			for(ItemStack item : p.getInventory().getContents())
                if(item != null && item == epic && item.getAmount() < 64){
                    p.getInventory().addItem(epic);
                    return true;
                }
			p.getWorld().dropItem(p.getLocation(), epic);
			p.sendMessage(ChatColor.GRAY + "You  got your Book!" );
		}
		return true;
	}
}
