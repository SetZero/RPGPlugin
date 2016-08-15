package playerstats;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerExpChangeEvent;

import eu.around_me.rpgplugin.libary.AdjacencyMatrix;
import eu.around_me.rpgplugin.libary.SkillPoints;
import net.md_5.bungee.api.ChatColor;
import skills.PassiveSkillPoint;
import skills.Skill;

public class RPGPlayerStat {
	private int str = 0;
	private int dex = 0;
	private int wis = 0;
	private int level = 0;
	private int skillpoints = 0;
	private int exp = 0;
	
	//TRYOUT: expToLevelUp -- Exp needed to levelup and get skillpoints
	pivate int expToLevelUp = 0;
	
	List<Skill> learned = new ArrayList<Skill>();
	AdjacencyMatrix skillTree;
	
	public RPGPlayerStat(int str, int dex, int wis, AdjacencyMatrix skillTree) {
		this.str = str;
		this.dex = dex;
		this.wis = wis;
		this.skillTree = skillTree;
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
	}
	
	//method to set levelup-cap -- place where we could put an algorithm
	public void setExpToLevelUp(int cap){
		this.expToLevelUp = cap;
	}
	//method to get expToLevelup
	public void getExpToLevelUp(){
		return expToLevelUp;
	}
	
	public void setEXP(int exp) {
		
		this.exp = exp;
	}
	
	public int getEXP() {
		return exp;
	}
	
	public AdjacencyMatrix getSkillTree() {
		return skillTree;
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
				Skill[] req = learn.getRequirements();
				//Check if all the requirements are fulfilled
				for(Skill reqskill : req) {
					if(!learned.contains(reqskill))
						return false;
				}
				//Learn Skill & remove Skillpoint
				learned.add(learn);
				skillpoints--;
				
				//TODO: Make this less static....
				if(learn instanceof PassiveSkillPoint) {
					PassiveSkillPoint p = (PassiveSkillPoint) learn;
					p.learnSkill(this);
				}
				return true;
			}
		}
		return false;
	}
	
	public void printStats(HumanEntity p) {
		p.sendMessage("-" + ChatColor.DARK_BLUE + p.getName() + ChatColor.WHITE + "-");
		p.sendMessage("Level: " + level);
		p.sendMessage("Exp: " + exp);
		p.sendMessage("Skillpoints: " + skillpoints);
		p.sendMessage("----------------");
		p.sendMessage(ChatColor.DARK_RED + "Strength: " + ChatColor.WHITE + str);
		p.sendMessage(ChatColor.DARK_AQUA + "Inteligence: " + ChatColor.WHITE + wis);
		p.sendMessage(ChatColor.DARK_GREEN + "Dexterity: " + ChatColor.WHITE + dex);
		p.sendMessage("----------------");
		
		
		
	}
	
	//CHECK FOR EXPCHANGE EXP LEVELUP
	@EventHandler  (priority = EventPriority.HIGHEST)
	private gainxp(PlayerExpChangeEvent event) {
		
		//set the new Exp with:
		setEXP(getEXP() + (int) (event.getAmount()/2));
		
		//check for levelup
		if(getEXP() >= getExpToLevelUp()) {
			//reset exp and execute levelUp() -- without that fancy methods: exp%=expToLevelUp;
			setEXP(getEXP%expToLevelUp);
			levelUp();
			
			//TODO: set new expToLevelUp cap!!!!
			//IDEAS: after executing levelUp() add a line (in levelUp()) to change the new expToLevelUp.
			//OR implement an algorithm which will be incremented every time setExpToLevelUp() is executed.
			expToLevelUp+= (i*20)/3;
		};
	}
		
		

	
}
