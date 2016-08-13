package skills;

import net.md_5.bungee.api.ChatColor;

public abstract class Skill {
	private static int idcounter = 0;
	private int id = 0;
	
	public Skill() {
		id = idcounter;
		idcounter++;
	}
	public int getID() {
		return id;
	}
	public abstract String getName();
	public abstract String setName(String name);
	public abstract String getDescription();
	public abstract String setDescription(String desc);
	public abstract ChatColor getChatColor();
	public abstract void setChatColor(ChatColor color);
	public abstract void setRequirements(Skill[] req);
	public abstract Skill[] getRequirements();
}