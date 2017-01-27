package eu.around_me.rpgplugin.skills;

import net.md_5.bungee.api.ChatColor;

public abstract class Skill {
	private static int idcounter = 0;
	private int id = 0;
	protected int priority = 0;
	
	protected String skillName;
	protected String skillDesc;
	protected ChatColor skillColor;
	protected Skill[] req;
	protected boolean prevReq;
	protected int prevAmount;
	
	public Skill() {
		id = idcounter;
		idcounter++;
	}
	public int getID() {
		return id;
	}
	public String getName() {
		return skillName;
	}
	
	public String setName(String name) {
		this.skillName = name;
		return null;
	}
	
	public String getDescription() {
		return skillDesc;
	}
	
	public void setDescription(String desc) {
		this.skillDesc = desc;
	}
	
	public ChatColor getChatColor() {
		return skillColor;
	}
	
	public void setChatColor(ChatColor color) {
		this.skillColor = color;

	}
	
	public void setSkillRequirements(Skill[] req) {
		this.req = req;

	}
	
	public Skill[] getSkillRequirements() {
		return req;
	}
	public void setNodeRequirements(boolean prevReq, int prevAmount) {
		this.prevReq = prevReq;
		this.prevAmount = prevAmount;

	}
	public int getNodeRequirements() {
		if(prevReq) {
			return prevAmount;
		} else {
			return 0;
		}
	}

}
