package eu.around_me.rpgplugin.libary;

public class PassiveSkill extends Skill {
	SkillPoints type;
	public PassiveSkill(SkillPoints type) {
		this.type = type;
	}

	@Override
	public String getName() {
		switch(type) {
			case STR:
				return "Strenght";
			case DEX:
				return "Dexterity";
			case INT:
				return "Inteligence";
			default:
				return "NaN";
		}
	}

}
