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
import eu.around_me.rpgplugin.skills.Skill;
import net.md_5.bungee.api.ChatColor;

public class Shield extends ActiveSkill {

	String skillName = "Shield";
	String skillDesc = "Generate a Shield around you";
	ChatColor skillColor = ChatColor.GRAY;
	ItemStack skillItem = new ItemStack(Material.SHIELD);
	Skill[] req = {};
	boolean prevReq = false;
	int prevAmount = 0;
	Plugin plugin;
	
	public Shield(Plugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean executeActive(RPGPlayerStat stat, HumanEntity p) {
		placeShield(p.getWorld(), Material.GLASS, p.getLocation(), stat, stat.getWis()+3);
		return false;
	}

	@Override
	public String getName() {
		return skillName;
	}

	@Override
	public String setName(String name) {
		this.skillName = name;
		return null;
	}

	@Override
	public String getDescription() {
		return skillDesc;
	}

	@Override
	public void setDescription(String desc) {
		this.skillDesc = desc;
	}

	@Override
	public ChatColor getChatColor() {
		return skillColor;
	}

	@Override
	public void setChatColor(ChatColor color) {
		this.skillColor = color;

	}

	@Override
	public void setSkillRequirements(Skill[] req) {
		this.req = req;

	}

	@Override
	public Skill[] getSkillRequirements() {
		return req;
	}

	@Override
	public void setNodeRequirements(boolean prevReq, int prevAmount) {
		this.prevReq = prevReq;
		this.prevAmount = prevAmount;

	}

	@Override
	public int getNodeRequirements() {
		if(prevReq) {
			return prevAmount;
		} else {
			return 0;
		}
	}
	
	protected void placeShield(World w, Material m, Location l, RPGPlayerStat stat, int r) {
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

}
