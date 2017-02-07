package eu.around_me.rpgplugin.skills;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.DyeColor;

import eu.around_me.rpgplugin.libary.SkillPoints;
import eu.around_me.rpgplugin.playerstats.RPGPlayerStat;
import eu.around_me.rpgplugin.skilleffects.passive.points.Dexterity;
import eu.around_me.rpgplugin.skilleffects.passive.points.Strength;
import net.md_5.bungee.api.ChatColor;

public class PassiveSkillPoint extends PassiveSkill {
	SkillPoints type;
	Skill[] requirements;
	String name;
	String description;
	ChatColor color;
	DyeColor itemColor;
	boolean requiresPreviousSkills;
	int previousSkillAmount;
	private List<PassiveSkillEffects> skillEffects;
	
	public PassiveSkillPoint(SkillPoints type, Skill[] requirements) {
		this.type = type;
		if(requirements == null) 
			this.requirements = new Skill[0];
		else
			this.requirements = new Skill[requirements.length];
	}
	
	public PassiveSkillPoint(PassiveSkillEffects effect, SkillPoints type, Skill[] requirements) {
		skillEffects = new ArrayList<PassiveSkillEffects>();
		this.type = type;
		skillEffects.add(0, effect);
		if(requirements == null) 
			this.requirements = new Skill[0];
		else
			this.requirements = new Skill[requirements.length];
	}

	public PassiveSkillPoint(String name, String desc, ChatColor color,
			PassiveSkillEffects effect, SkillPoints type, Skill[] requirements) {
		this(effect, type, requirements);
		this.name = name;
		this.description = desc;
		this.color = color;
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
					return "You are more Intelligent";
				default:
					return "Unknown";
			}
		}
		return description;
	}

	@Override
	public void setDescription(String desc) {
		this.description = desc;
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
	public void setSkillRequirements(Skill[] requirements) {
		this.requirements = requirements;
	}

	@Override
	public Skill[] getSkillRequirements() {
		return requirements;
	}
	
	public SkillPoints getType() {
		return type;
	}

	public DyeColor getItemColor() {
		if(itemColor == null) {
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

	@Override
	public void setSkillEffect(PassiveSkillEffects effect, int index) {
		this.skillEffects.set(index, effect);
	}
	
	@Override
	public PassiveSkillEffects getSkillEffect() {
		if(skillEffects == null) {
			skillEffects = new ArrayList<PassiveSkillEffects>();
		} 
		if(skillEffects.isEmpty()) {
			PassiveSkillEffects e;
			switch(type) {
				case STR:
					e = new Strength();
					skillEffects.add(e);
					return e;
				case DEX:
					e = new Dexterity();
					skillEffects.add(e);
					return e;
				case INT:
					return null;
				default:
					return skillEffects.get(0);
			}
		} else {
			return skillEffects.get(0);
		}
	}

	@Override
	public void setNodeRequirements(boolean prevReq, int prevAmount) {
		this.requiresPreviousSkills = prevReq;
		this.previousSkillAmount = prevAmount;
		
	}

	@Override
	public int getNodeRequirements() {
		if(!requiresPreviousSkills)
			return 0;
		else
			return previousSkillAmount;
	}




}
