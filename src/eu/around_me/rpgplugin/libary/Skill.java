package eu.around_me.rpgplugin.libary;

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
}
