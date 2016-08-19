package eu.around_me.rpgpluginskills;


public abstract class PassiveSkill extends Skill {
	public abstract void setSkillEffect(PassiveSkillEffects effect, int index);
	public abstract PassiveSkillEffects getSkillEffect();
}
