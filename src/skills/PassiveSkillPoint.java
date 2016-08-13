package skills;

import java.util.Map;

import org.bukkit.DyeColor;
import org.bukkit.entity.HumanEntity;

import eu.around_me.rpgplugin.libary.SkillPoints;
import net.md_5.bungee.api.ChatColor;
import playerstats.RPGPlayerStat;

public class PassiveSkillPoint extends Skill {
	SkillPoints type;
	Skill[] requirements;
	String name;
	String description;
	ChatColor color;
	DyeColor itemColor;
	
	public PassiveSkillPoint(SkillPoints type, Skill[] requirements) {
		this.type = type;
		if(requirements == null) 
			this.requirements = new Skill[0];
		else
			this.requirements = new Skill[requirements.length];
	}

	@Override
	public String getName() {
		if(name == null || name.isEmpty()) {
			switch(type) {
				case STR:
					return "Strenght";
				case DEX:
					return "Dexterity";
				case INT:
					return "Inteligence";
				default:
					return "Unknown";
			}
		}
		return name;
	}

	@Override
	public String setName(String name) {
		this.name = name;
		return null;
	}

	@Override
	public String getDescription() {
		if(description == null || description.isEmpty()) {
			switch(type) {
				case STR:
					return "You are more Dexterous";
				case DEX:
					return "You become Stronger";
				case INT:
					return "You are more Inteligent";
				default:
					return "Unknown";
			}
		}
		return description;
	}

	@Override
	public String setDescription(String desc) {
		this.description = desc;
		return null;
	}
	
	@Override
	public ChatColor getChatColor() {
		if(color == null) {
			switch(type) {
				case STR:
					return ChatColor.DARK_RED;
				case DEX:
					return ChatColor.DARK_GREEN;
				case INT:
					return ChatColor.DARK_AQUA;
				default:
					return ChatColor.WHITE;
			}
		}
		return color;
	}

	@Override
	public void setChatColor(ChatColor color) {
		this.color = color;
	}
	
	@Override
	public void setRequirements(Skill[] requirements) {
		this.requirements = requirements;
	}

	@Override
	public Skill[] getRequirements() {
		return requirements;
	}
	
	public SkillPoints getType() {
		return type;
	}

	public DyeColor getItemColor() {
		if(color == null) {
			switch(type) {
				case STR:
					return DyeColor.RED;
				case DEX:
					return DyeColor.GREEN;
				case INT:
					return DyeColor.BLUE;
				default:
					return DyeColor.WHITE;
			}
		}
		return itemColor;
	}

	public void setItemColor(DyeColor color) {
		this.itemColor = color;
	}
	
	public void learnSkill(RPGPlayerStat stat) {
		if(type == SkillPoints.DEX) {
			stat.setDex(stat.getDex() + 1);
		} else if(type == SkillPoints.INT) {
			stat.setWis(stat.getWis() + 1);
		} else if(type == SkillPoints.STR) {
			stat.setStr(stat.getStr() + 1);
		} 
	}


}
