package eu.around_me.rpgplugin.skills;


public abstract class PassiveSkill extends Skill {
	public abstract void setSkillEffect(PassiveSkillEffects effect, int index);
	public abstract PassiveSkillEffects getSkillEffect();
}
