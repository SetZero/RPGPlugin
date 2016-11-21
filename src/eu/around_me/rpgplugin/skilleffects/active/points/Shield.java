package eu.around_me.rpgplugin.skilleffects.active.points;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import eu.around_me.rpgplugin.libary.WorldEditing;
import eu.around_me.rpgplugin.playerstats.RPGPlayerStat;
import eu.around_me.rpgplugin.skills.ActiveSkill;
import net.md_5.bungee.api.ChatColor;

public class Shield extends ActiveSkill {
	Plugin plugin;
	
	public Shield(Plugin plugin) {
		this.plugin = plugin;
		
		skillName = "Shield";
		skillDesc = "Generate a Shield around you";
		skillColor = ChatColor.GRAY;
		skillItem = new ItemStack(Material.SHIELD);
		req = null;
		prevReq = false;
		prevAmount = 0;
		cooldown = 4;
		manacost = 20;
	}

	@Override
	public boolean executeActive(RPGPlayerStat stat, HumanEntity p) {
		placeShield(p.getWorld(), Material.GLASS, p.getLocation(), stat, (int)(stat.getWis()/4)+2);
		return true;
	}

	@Override
	public boolean needsUnload() {
		return false;
	}

	@Override
	public void unload() {
		
	}
	
	protected void placeShield(World w, Material m, Location l, RPGPlayerStat stat, int r) {
		//Sphere Code
		int x = (int) (l.getX() - r);
		int y = (int) (l.getY() - r);
		int z = (int) (l.getZ() - r);
		for ( int i=0 ; i<r*2 ; i++ )
		{
			for ( int j=0 ; j<r*2 ; j++ )
			{
				
				for ( int k=0 ; k<r*2 ; k++ )
				{
					double distance = (r-i)*(r-i)
							  + (r-j)*(r-j)
							  + (r-k)*(r-k);
					
					if ( distance < (r*r) ) {
						if(i > 0 && j > 0 && k > 0) {
							int r2 = r-1;
							double distance2 = (r2-(i-1))*(r2-(i-1))
									  + (r2-(j-1))*(r2-(j-1))
									  + (r2-(k-1))*(r2-(k-1));
							if (!(distance2 < (r2*r2))) {
								WorldEditing.placeTempBlock(w, x, y, z, m, 5+stat.getWis()*4, plugin);
							}
						} else {
							WorldEditing.placeTempBlock(w, x, y, z, m, 5+stat.getWis()*4, plugin);
						}
					}
					x++;
				}
				y++;
				x = (int) (l.getX() - r);
				
			}
			z++;
			x = (int) (l.getX() - r);
			y = (int) (l.getY() - r);
		}
	}

	@Override
	public ItemStack getItem() {
		return this.skillItem;
	}

	@Override
	public int getCooldown() {
		return this.cooldown;
	}

	@Override
	public int getManacost() {
		return manacost;
	}

}
