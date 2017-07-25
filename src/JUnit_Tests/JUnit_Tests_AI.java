package JUnit_Tests;

import org.junit.Test;

import Heros.SkeletonBoss;
import PartyContainers.AI;
import PartyContainers.HumanPlayer;

public class JUnit_Tests_AI {

	/**
	 * Get AI to scan the player
	 */
	@Test
	public void testAI_Turn() {
		HumanPlayer human = new HumanPlayer();
		AI ai = new AI();
		SkeletonBoss hero = new SkeletonBoss(AI.CONTROLLER);
		hero = (SkeletonBoss) ai.getCharacter(hero.getClass().getName());
		ai.aiTurn(hero, human);
		// Whatever the AI does in this situation test it
	}
	
	/**
	 * Test AI heal
	 */
	@Test
	public void testAI_Heal() {
		HumanPlayer human = new HumanPlayer();
		AI ai = new AI();
		SkeletonBoss hero = new SkeletonBoss(AI.CONTROLLER);
		hero = (SkeletonBoss) ai.getCharacter(hero.getClass().getName());
		hero.setHealth(2);
		ai.aiTurn(hero, human);
		// The AI should heal
	}
	
}
