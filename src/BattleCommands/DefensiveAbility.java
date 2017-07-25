package BattleCommands;

import java.awt.Image;

import Heros.Hero;
import RPG_Exceptions.MaximumStatException;

/**
 * DefensiveAbility is an abstract class that extends Ability.
 * It provides an outline and constructor for all DefensiveAbilities.
 * Why abstract? We don't yet know exactly what an ability will do or what class
 * the ability belongs to.
 * @author Kevin
 *
 */
public abstract class DefensiveAbility extends Ability{
	private String statAffected;
	private int effectStrength;
	private Image animationImage;
	
	/**
	 * Constructor to set the defensive abilities point cost, which stat is effects and it's effect strength.
	 * @param animationImage TODO
	 * @param pointCost: how many abilities point will be consumed on use
	 * @param statAffected: which of the hero's statistics is affected
	 * @param effectStrength: how strong of an effect does this ability have
	 */
	public DefensiveAbility(int pointCost, String statAffected, int effectStrength, Image animationImage)
	{
		super.setPointCost(pointCost);
		this.setStatAffected(statAffected);
		this.effectStrength = effectStrength;
		this.animationImage = animationImage;
	}
	
	/**
	 * Returns the class string that owns these abilities.
	 * @return Class string that owns these abilities.
	 */
	public abstract String getClassOwner();
	
	/**
	 * Use the current ability. Don't know exactly what the ability does yet thus the method is abstract.
	 * Throws MaximumStatException if the defensive ability or item used cannot increase the statistic that it affects.
	 * @param hero: current acting hero
	 * @param target: target hero
	 * @throws MaximumStatException if the defensive ability or item used cannot increase the statistic that it affects.
	 */
	public abstract void useBattleCommand(Hero hero, Hero other) throws MaximumStatException;
	
	/**
	 * Get this defensive abilities effect strength.
	 * @return This defensive abilities effect strength.
	 */
	public int getEffectStrength()
	{
		return this.effectStrength;
	}

	/**
	 * Get the statistic affected by this defensive ability. 
	 * @return  The statistic affected by this defensive ability. 
	 */
	public String getStatAffected() {
		return statAffected;
	}

	/**
	 * Set which statistic this defensive ability will affect.
	 * @param statAffected: the name of the statistic that this ability will affect. 
	 */
	public void setStatAffected(String statAffected) {
		this.statAffected = statAffected;
	}
	
	/**
	 * Get this abilities animation image
	 * @return This abilities animation image. 
	 */
	public Image getAnimationImage() {
		return animationImage;
	}
	
}
