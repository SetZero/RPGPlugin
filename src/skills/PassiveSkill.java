package skills;


public abstract class PassiveSkill extends Skill {
	public abstract void setSkillEffect(PassiveSkillEffects effect);
	public abstract PassiveSkillEffects getSkillEffect();
}
