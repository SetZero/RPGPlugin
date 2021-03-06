package eu.around_me.rpgplugin.playerstats;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.plugin.Plugin;

import eu.around_me.rpgplugin.libary.AdjacencyMatrix;
import eu.around_me.rpgplugin.libary.Manatypes;
import eu.around_me.rpgplugin.libary.ShieldRegenTypes;
import eu.around_me.rpgplugin.libary.userfiles.FileHandler;
import eu.around_me.rpgplugin.scoreboard.Sidebar;
import eu.around_me.rpgplugin.skills.OverTimeSkill;
import eu.around_me.rpgplugin.skills.PassiveSkill;
import eu.around_me.rpgplugin.skills.PassiveSkillPoint;
import eu.around_me.rpgplugin.skills.Skill;
import net.md_5.bungee.api.ChatColor;

public class RPGPlayerStat {
	private int str = 0;													//The strenght of a Player
	private int dex = 0;													//The dexterity
	private int wis = 0;													//The Wisdom aka Intelligence
	private int level = 0;													//Player Level
	private int skillpoints = 0;											//The available Skillpoints
	private int exp = 0;													//The current Exp-Points
	//Mana
	private int mana = 50;													//The current Mana
	private int maxmana = 100;												//The Maximum Mana
	private int manaregen = 2;												//The Maximum Mana regeneration
	private int manaloss = 2;												//The Loss of Mana if Mana is aggro/rage
	private int outofcombattimer = 0;										//The Time in secounds a player hasn't fought
	private Manatypes manatype = Manatypes.MANA;							//The Type of Mana (/Aggro/Energy)
	private ChatColor manabarcolor = ChatColor.BLUE;						//The color of the Mana
	//Shield
	private boolean hasShield = false;										//Has the Player a Shield?
	private int maxShield = 0;												//Maximum Shield a Player can regenerate
	private int Shield = 0;													//The current shield amount
	private ShieldRegenTypes shieldRegenType = ShieldRegenTypes.OFFBATTLE;	//The Shield regeneration Type (On/Offbattle)
	private int shieldRegen = 20;											//The amount(percent) a shield regenerates per second/action
	private boolean OmniShield = false;										//EnergyShield applies to all damage
	//TRYOUT: expToLevelUp -- Exp needed to levelup and get skillpoints	
	private int expToLevelUp = 0;											//The amount of exp for the next Level
	private FileHandler fh;													//The File Handler for a player to save the data to
	private Sidebar sb;														//The Sidebar of a player
	private Map<Material, Skill> bindSkills;								//The bound Skills (to objects)
	private Map<Skill, Integer> cooldowns;									//The Cooldowns of all Skills
	
	private int playerMaxLife = 20;											//Max life a player can get
	private double evasionRating = 0.0;										//Evasion Rate of a Player
	private double critChance = 0.0;										//Crit Chance of a Player
	private double critMultiplier = 1.5;									//Crit Multi of a Player
	private double manaLeech = 0;											//Mana Regen by Damage dealt
	private double manaCostMulti = 1;									//Mana Cost Multiplier
	
	List<Skill> learned = new ArrayList<Skill>();							//The Learned Skills
	private List<OverTimeSkill> activeOverTimeSkills = new LinkedList<OverTimeSkill>(); //Current Active Skills
	AdjacencyMatrix skillTree;												//The Skill Tree
	
	//Settings
	private boolean showSidebar = false;
	private UUID playerid = null;
	private boolean ShowAbsValuesInSidebar = false;
	
	public RPGPlayerStat(int str, int dex, int wis, AdjacencyMatrix skillTree, FileHandler fh) {
		this.str = str;
		this.dex = dex;
		this.wis = wis;
		this.skillTree = skillTree;
		this.fh = fh;
		bindSkills = new HashMap<Material, Skill>();
		cooldowns = new HashMap<Skill, Integer>();
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
		sb.sidebarRefresh();
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
	
	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		sb.sidebarRefresh();
		this.mana = mana;
	}

	public int getMaxmana() {
		return maxmana;
	}

	public void setMaxmana(int maxmana) {
		this.maxmana = maxmana;
	}

	public int getManaregen() {
		return manaregen;
	}

	public void setManaregen(int manaregen) {
		this.manaregen = manaregen;
	}
	
	public String getManaName() {
		switch(manatype) {
			case MANA:
				return "Mana";
			case ENERGY:
				return "Energy";
			case AGGRO:
				return "Aggression";
			case LIFE:
				return "Life";
			default:
				return "Unknown";
		}
	}
	
	public Manatypes getManatype() {
		return manatype;
	}
	
	public void setManatype(Manatypes type) {
		this.manatype = type;
	}

	public int getManaloss() {
		return manaloss;
	}

	public void setManaloss(int manaloss) {
		this.manaloss = manaloss;
	}

	public int getOutofcombattimer() {
		return outofcombattimer;
	}

	public void setOutofcombattimer(int outofcombattimer) {
		this.outofcombattimer = outofcombattimer;
	}
	
	public ChatColor getManabarcolor() {
		switch(manatype) {
		case MANA:
			return ChatColor.BLUE;
		case ENERGY:
			return ChatColor.YELLOW;
		case AGGRO:
			return ChatColor.DARK_RED;
		case LIFE:
			return ChatColor.RED;
		default:
			return manabarcolor;
	}
	}

	public boolean getHasShield() {
		return hasShield;
	}

	public void setHasShield(boolean hasShield) {
		this.hasShield = hasShield;
	}

	public int getMaxShield() {
		return maxShield;
	}

	public void setMaxShield(int maxShield) {
		this.maxShield = maxShield;
	}

	public int getShield() {
		return Shield;
	}

	public void setShield(int shield) {
		Shield = shield;
	}

	public ShieldRegenTypes getShieldRegenType() {
		return shieldRegenType;
	}

	public void setShieldRegenType(ShieldRegenTypes shieldRegenType) {
		this.shieldRegenType = shieldRegenType;
	}

	public int getShieldRegen() {
		return shieldRegen;
	}

	public void setShieldRegen(int shieldRegen) {
		this.shieldRegen = shieldRegen;
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
	
	public int getCooldown(Skill s) {
		if(cooldowns.get(s) == null)
			return 0;
		else
			return cooldowns.get(s);
	}
	
	public Map<Skill, Integer> getCooldowns() {
		return cooldowns;
	}

	public void setCooldowns(Skill s, int cooldown) {
		cooldowns.put(s, cooldown);
	}

	public void addSkillbind(Material m, Skill s) {
		bindSkills.put(m, s);
	}
	
	public void removeSkillbind(Material m) {
		bindSkills.remove(m);
	}
	
	public Skill getSkillbind(Material m) {
		return bindSkills.get(m);
	}
	
	public boolean getShowSidebar() {
		return showSidebar;
	}

	public void setShowSidebar(boolean showSidebar) {
		this.showSidebar = showSidebar;
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
				if(req != null) {
					for(Skill reqskill : req) {
						if(!learned.contains(reqskill))
							return false;
					}
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
	
	public void setupSkillEffects(HumanEntity p, Plugin plugin) {
		for(Skill s: learned) {
			if(s instanceof PassiveSkill) {
				PassiveSkill ps = (PassiveSkill) s;
				ps.getSkillEffect().executeEffect(p, plugin);
			}
		}
	}
	
	public void learnByID(int id) {
		learned.add(skillTree.getSkillByID(id));
	}
	
	public String printProgressBar(int bars, int progressbars, ChatColor color) {
		String fancy = color+"";
		boolean changed = false;
		
		for(int i=0;i<bars;i++) {
			if(i>=progressbars && !changed) {
				changed = true;
				fancy += ChatColor.DARK_GRAY;
			}
			fancy += "▏";
		}
		return fancy;
	}
	
	public String printLevelProgressBar(int bars) {
		int progressbars = (int) ((expToLevelUp == 0) ? 0 : Math.round((((double)exp / expToLevelUp) * bars)));
		return printProgressBar(bars, progressbars, ChatColor.DARK_GREEN);
	}
	
	public String printManaBar(int bars, ChatColor color) {
		int progressbars = (int) ((maxmana == 0) ? 0 : Math.round((((double)mana / maxmana) * bars)));
		return printProgressBar(bars, progressbars, color);
	}
	
	public String printShieldBar(int bars, ChatColor color) {
		int progressbars = (int) ((maxShield == 0) ? 0 : Math.round((((double)Shield / maxShield) * bars)));
		return printProgressBar(bars, progressbars, color);
	}
	
	public String printFullLevelProgress() {
		String fancy = printLevelProgressBar(28);
		String progress = (expToLevelUp == 0) ? "0" : String.valueOf(Math.round((((double)exp / expToLevelUp) * 100)));
		return fancy + " (" + (ShowAbsValuesInSidebar ? exp : progress + "%") + ")";
	}
	
	public String printFullMana(ChatColor color) {
		String fancy = printManaBar(28, color);
		String progress = (maxmana == 0) ? "0" : String.valueOf(Math.round((((double)mana / maxmana) * 100)));
		return fancy + " (" + (ShowAbsValuesInSidebar ? mana : progress + "%") + ")";
	}
	

	public String printFullShield(ChatColor color) {
		String fancy = printShieldBar(28, color);
		String progress = (maxShield == 0) ? "0" : String.valueOf(Math.round((((double)Shield / maxShield) * 100)));
		return fancy + " (" + (ShowAbsValuesInSidebar ? Shield : progress + "%") + ")";
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
		p.sendMessage(ChatColor.DARK_GREEN + "Evasion Rating: " + ChatColor.WHITE + Math.round(evasionRating*100) + "%");
		p.sendMessage(ChatColor.DARK_RED + "Critical Strike Chance: " + ChatColor.WHITE +  Math.round(critChance*100) + "%");
		
		
		
	}

	public UUID getPlayerid() {
		return playerid;
	}

	public void setPlayerid(UUID playerid) {
		this.playerid = playerid;
	}

	public boolean isOmniShield() {
		return OmniShield;
	}

	public void setOmniShield(boolean omniShield) {
		OmniShield = omniShield;
	}

	public boolean isShowAbsValuesInSidebar() {
		return ShowAbsValuesInSidebar;
	}

	public void setShowAbsValuesInSidebar(boolean showAbsValuesInSidebar) {
		ShowAbsValuesInSidebar = showAbsValuesInSidebar;
	}

	public int getPlayerMaxLife() {
		return playerMaxLife;
	}

	public void setPlayerMaxLife(int playerMaxLife) {
		this.playerMaxLife = playerMaxLife;
	}

	public List<OverTimeSkill> getActiveOverTimeSkills() {
		return activeOverTimeSkills;
	}

	public void addActiveOverTimeSkills(OverTimeSkill OverTimeSkill) {
		this.activeOverTimeSkills.add(OverTimeSkill);
	}
	public void removeActiveOverTimeSkills(OverTimeSkill OverTimeSkill) {
		this.activeOverTimeSkills.remove(OverTimeSkill);
	}

	public double getEvasionRating() {
		return evasionRating;
	}

	public void setEvasionRating(double evasionRating) {
		this.evasionRating = evasionRating;
	}

	public double getCritChance() {
		return critChance;
	}

	public void setCritChance(double critChance) {
		this.critChance = critChance;
	}

	public double getCritMultiplier() {
		return critMultiplier;
	}

	public void setCritMultiplier(double critMultiplier) {
		this.critMultiplier = critMultiplier;
	}

	public double getManaLeech() {
		return manaLeech;
	}

	public void setManaLeech(double manaLeech) {
		this.manaLeech = manaLeech;
	}

	public double getManaCostMulti() {
		return manaCostMulti;
	}

	public void setManaCostMulti(double manaCostMulti) {
		this.manaCostMulti = manaCostMulti;
	}
}
