package eu.around_me.rpgplugin.skills;

import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class PassiveSkillAttribute extends PassiveSkill {

	Skill[] requirements;
	String name;
	String description;
	ChatColor color;
	ItemStack itemType;
	PassiveSkillEffects effect;
	
	public PassiveSkillAttribute(ItemStack item, Skill[] requirements) {
		// TODO Auto-generated constructor stub
		this.itemType = item;
		this.requirements = requirements;
	}

	@Override
	public void setSkillEffect(PassiveSkillEffects effect, int index) {
		this.effect = effect;
	}

	@Override
	public PassiveSkillEffects getSkillEffect() {
		return effect;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDescription(String desc) {
	}

	@Override
	public ChatColor getChatColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setChatColor(ChatColor color) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSkillRequirements(Skill[] req) {
		// TODO Auto-generated method stub

	}

	@Override
	public Skill[] getSkillRequirements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNodeRequirements(boolean prevReq, int prevAmount) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getNodeRequirements() {
		// TODO Auto-generated method stub
		return 0;
	}

	

}
