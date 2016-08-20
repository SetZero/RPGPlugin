package eu.around_me.rpgplugin.playerstats;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import eu.around_me.rpgplugin.libary.AdjacencyMatrix;
import eu.around_me.rpgplugin.libary.userfiles.FileHandler;
import eu.around_me.rpgplugin.scoreboard.Sidebar;
import eu.around_me.rpgplugin.skills.PassiveSkill;
import eu.around_me.rpgplugin.skills.PassiveSkillPoint;
import eu.around_me.rpgplugin.skills.Skill;
import net.md_5.bungee.api.ChatColor;

public class RPGPlayerStat {
	private int str = 0;
	private int dex = 0;
	private int wis = 0;
	private int level = 0;
	private int skillpoints = 0;
	private int exp = 0;
	//TRYOUT: expToLevelUp -- Exp needed to levelup and get skillpoints
	private int expToLevelUp = 0;
	private FileHandler fh;
	private Sidebar sb;
	private Map<Material, Skill> bindSkills;
	
	
	List<Skill> learned = new ArrayList<Skill>();
	AdjacencyMatrix skillTree;
	
	public RPGPlayerStat(int str, int dex, int wis, AdjacencyMatrix skillTree, FileHandler fh) {
		this.str = str;
		this.dex = dex;
		this.wis = wis;
		this.skillTree = skillTree;
		this.fh = fh;
		bindSkills = new HashMap<Material, Skill>();
	}
	
	public int getStr() {
		return str;
	}

	public void setStr(int str) {
		this.str = str;
	}

	public int getDex() {
		return dex;
	}

	public void setDex(int dex) {
		this.dex = dex;
	}

	public int getWis() {
		return wis;
	}

	public void setWis(int wis) {
		this.wis = wis;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getLevel() {
		return level;
	}

	public void levelUp() {
		skillpoints++; //TODO: not a static value
		level++;
		//insert algorithm for expToLevelUp variable here
	}
	
	//method to set levelup-cap -- place where we could put an algorithm
	public void setExpToLevelUp(int cap){
		this.expToLevelUp = cap;
	}
	//method to get expToLevelup
	public int getExpToLevelUp(){
		return expToLevelUp;
	}
	
	public void setEXP(int exp) {
		
		this.exp = exp;
	}
	
	public int getEXP() {
		return exp;
	}
	
	public List<Skill> getLearnedSkills() {
		return learned;
	}
	
	public AdjacencyMatrix getSkillTree() {
		return skillTree;
	}
	
	public FileHandler getFh() {
		return fh;
	}

	public void setFh(FileHandler fh) {
		this.fh = fh;
	}

	public Sidebar getSb() {
		return sb;
	}

	public void setSb(Sidebar sb) {
		this.sb = sb;
	}

	public int getSkillpoints() {
		return skillpoints;
	}
	
	public void setSkillpoints(int skillpoints) {
		this.skillpoints = skillpoints;
	}
	
	public void addSkillbind(Material m, Skill s) {
		bindSkills.put(m, s);
	}
	
	public Skill getSkillbind(Material m) {
		return bindSkills.get(m);
	}
	
	public boolean learnSkill(Skill learn) {
		if(skillpoints >= 1) {
			if(!learned.contains(learn)) {
				if(learn == null) return false;
				Skill[] req = learn.getSkillRequirements();
				if(learn.getNodeRequirements() > 0) {
					int[] position = skillTree.getPosition(learn);
					if(position.length < 1) return false;
					int connected = skillTree.inEdges(position[0]).size();
					if(connected < learn.getNodeRequirements()) return false;
				}
				for(Skill reqskill : req) {
					if(!learned.contains(reqskill))
						return false;
				}
				
				learned.add(learn);
				skillpoints--;
				if(learn instanceof PassiveSkillPoint) {
					PassiveSkillPoint p = (PassiveSkillPoint) learn;
					p.learnSkill(this);
				}
				return true;
			}
		}
		return false;
	}
	
	public boolean learnSkill(int skillid, int position) {
		if(skillpoints >= 1) {
			//Get the Skill Type at that position
			Skill learn = skillTree.getSkill(position, skillid);
			//Not learned yet
			if(!learned.contains(learn)) {
				//Skill does not exist
				if(learn == null) return false;
				//Get the needed requirements for that skill
				Skill[] req = learn.getSkillRequirements();
				
				if(learn.getNodeRequirements() > 0) {
					//Check if we have enough Nodes
					int connected = skillTree.inEdges(position).size();
					if(connected < learn.getNodeRequirements()) return false;
				}
				//Check if all the requirements are fulfilled
				for(Skill reqskill : req) {
					if(!learned.contains(reqskill))
						return false;
				}
				//Learn Skill & remove Skillpoint
				learned.add(learn);
				skillpoints--;
				
				//TODO: Make this less static....
				//Call learnSkill from skill class
				if(learn instanceof PassiveSkillPoint) {
					PassiveSkillPoint psp = (PassiveSkillPoint) learn;
					psp.learnSkill(this);
				}
				return true;
			}
		}
		return false;
	}
	
	public void setupSkillEffects(HumanEntity p) {
		for(Skill s: learned) {
			if(s instanceof PassiveSkill) {
				PassiveSkill ps = (PassiveSkill) s;
				ps.getSkillEffect().executeEffect(p);
			}
		}
	}
	
	public void learnByID(int id) {
		learned.add(skillTree.getSkillByID(id));
	}
	
	public String printLevelProgressBar(int bars) {
		String fancy = ChatColor.DARK_GREEN+"";
		boolean changed = false;
		int progressbars = (int) ((expToLevelUp == 0) ? 0 : Math.round((((double)exp / expToLevelUp) * 20)));
		for(int i=0;i<bars;i++) {
			if(i>=progressbars && !changed) {
				changed = true;
				fancy += ChatColor.DARK_RED;
			}
			fancy += "▏";
		}
		return fancy;
	}
	
	public String printFullLevelProgress() {
		String fancy = printLevelProgressBar(30);
		String progress = (expToLevelUp == 0) ? "0" : String.valueOf(Math.round((((double)exp / expToLevelUp) * 100)));
		return fancy + " (" + progress + "%)";
	}
	
	public void printStats(HumanEntity p) {
		p.sendMessage("-" + ChatColor.DARK_BLUE + p.getName() + ChatColor.WHITE + "-");
		p.sendMessage("Level: " + level);
		p.sendMessage("EXP: " + printFullLevelProgress());
		p.sendMessage("Skillpoints: " + skillpoints);
		p.sendMessage("----------------");
		p.sendMessage(ChatColor.DARK_RED + "Strength: " + ChatColor.WHITE + str);
		p.sendMessage(ChatColor.DARK_AQUA + "Intelligence: " + ChatColor.WHITE + wis);
		p.sendMessage(ChatColor.DARK_GREEN + "Dexterity: " + ChatColor.WHITE + dex);
		p.sendMessage("----------------");
		
		
		
	}


}
