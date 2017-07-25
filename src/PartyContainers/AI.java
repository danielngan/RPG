package PartyContainers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.TreeMap;

import BattleCommands.Ability;
import BattleCommands.BaseAttack;
import BattleCommands.HealthItem;
import BattleCommands.Item;
import BattleCommands.OffensiveAbility;
import GridGUI.BattleController;
import Heros.Hero;
import Heros.SkeletonArcher;
import Heros.SkeletonBoss;
import Heros.SkeletonSpearMan;
import Heros.SkeletonWarrior;
import RPG_Exceptions.MaximumStatException;
import RPG_Exceptions.NotAfflictedWithStatusException;
/**
 * Artificial Intelligence class, will scan the human opponent and make a decision as which move to make.
 * @author Kevin, Andrew 
 *
 */

public class AI extends Player{
	// Public constant which defines who to give control to in the battle system
	public static final String CONTROLLER = "AI";
	private BattleController controller;
	/**
	 * Constructor which creates the default party.
	 */
	public AI()
	{
		super();
		makeDefaultParty();
	}
	
	/**
	 * Creates the default AI party uses Heros specific to the AI.
	 */
	public void makeDefaultParty()
	{
		ArrayList<Hero> partyArray = new ArrayList<Hero>();
		partyArray.add(new SkeletonBoss(AI.CONTROLLER));
		partyArray.add(new SkeletonSpearMan(AI.CONTROLLER));
		partyArray.add(new SkeletonArcher(AI.CONTROLLER));
		partyArray.add(new SkeletonWarrior(AI.CONTROLLER));
		for(Hero hero : partyArray)
		{
			super.getParty().put(hero.getClass().getName(), hero);
		}
	}
	
	/**
	 * The function used to determine what the AI player is going to do. This is a multiple step process in which the AI....
	 * 1) Scans the player's party to determine which ability they have available that will do the most damage
	 * 2) If 1 would kill the current acting AI hero, heal or use a buff to prevent death
	 * 3) If not 1, pick the current acting AI hero's strongest available ability and attack the player character with the lowest health.
	 * @param hero: current acting hero
	 * @param player: enemy's player to access their party
	 * @return AiBattleReturnType: What is this? A class specifically made so that we can return two types of values by setting them as fields in this class.
	 * This class stores the target and AI ability used to be published to the view. 
	 * @throws MaximumStatException if the AI attempts to use a potion or ability when it would make no statistical difference to their hero 
	 * @throws NotAfflictedWithStatusException 
	 */
	public AiBattleReturnType scan(Hero hero, Player player) throws MaximumStatException
	{
		TreeMap<String, Hero> party = player.getParty();
		Collection<Hero> playerParty = party.values();
		
		OffensiveAbility playerHighestDamage = pickHighestDamage(playerParty);
		
		if(hero.getHealth() != hero.getMaxHealth())
		{
			if((playerHighestDamage.getDamage()-hero.getDefenseRating()) > hero.getHealth())
			{
				Item item = pickHealingItem();
				if(item != null)
				{
					System.out.println("AI using item: " + item.toString());
					Hero target = null;
					if(controller != null)
					{
						item.useBattleCommand(hero, controller);
					}
					else
					{
						item.useBattleCommand(hero, target);
					}
					AiBattleReturnType result = new AiBattleReturnType(null,item);
					return result;
				}
			}
		}
		// Attack if no need to heal
		OffensiveAbility AIhighestDamage = pickHighestDamage(hero);
		System.out.println("AI using ability: " + AIhighestDamage.toString());
		Hero target = selectTarget(playerParty);
		System.out.println(target.getClass());
		if(controller != null)
		{
			AIhighestDamage.useBattleCommand(hero, target,controller);
		}
		else
		{
			AIhighestDamage.useBattleCommand(hero, target);
		}
		// Return target and ability used 
		AiBattleReturnType result = new AiBattleReturnType(target,AIhighestDamage);
		return result;
	}
	
	/**
	 * Function used to start the AI turn, checks if the AI is crowd controlled, if it is will tick all current statuses on the acting hero and finish the AI's turn.
	 * If not will proceed to scan the player party to choose the best course of action.
	 * @param hero: current acting hero
	 * @param human: enemy party to scan
	 * AiBattleReturnType: What is this? A class specifically made so that we can return two types of values by setting them as fields in this class.
	 * This class stores the target and AI ability used to be published to the view.
	 * @throws NotAfflictedWithStatusException 
	 */
	public AiBattleReturnType aiTurn(Hero hero, Player human)
	{
		AiBattleReturnType target = new AiBattleReturnType(null,null);
		System.out.println("Enemy Turn!");
		boolean AIControlled;
		if(controller != null)
		{
			AIControlled = hero.updateStatuses(controller);
		}
		else
		{
			AIControlled = hero.updateStatuses();
		}
		
		if(!AIControlled)
		{
			try
			{
				target = this.scan(hero, human);
			}
    		catch(MaximumStatException e)
			{
    			e.printStackTrace();
			}
		}
		return target;
	}
	
	/**
	 * AI's method to select the enemy target with the lowest health.
	 * @param party: enemy party to scan
	 * @return Hero: the target to attack 
	 */
	private Hero selectTarget(Collection<Hero> party)
	{
		Hero target = null;
		int lowestHealth = Integer.MAX_VALUE;
		int count = 0;
		for(Hero playerHero : party)
		{
			int currentHealth = playerHero.getHealth();
			if((count == 0) && (currentHealth > 0))
			{
				lowestHealth = currentHealth;
				target = playerHero;
			}
			else if((currentHealth > 0) && (currentHealth < lowestHealth))
			{
				lowestHealth = playerHero.getHealth();
				target=playerHero;
			}
			count ++;
		}
		return target;
	}
	
	/**
	 * AI's method to scan it's the enemy's party to determine their strongest available attack to use.
	 * @param party: enemy's party
	 * @return Enemy's strongest available attack party wide
	 */
	private OffensiveAbility pickHighestDamage(Collection<Hero> party)
	{
		// Scans entire party 
		int currentHighestDamage = 0;
		OffensiveAbility cmd = null;
		for(Hero playerHero : party)
		{
			HashMap<String, Ability> abilities = playerHero.getAbilities();
			Collection<Ability> currentAbilities = abilities.values();
			for(Ability ability : currentAbilities)
			{
				if(ability instanceof OffensiveAbility)
				{
					int availableAB = playerHero.getAbilityPoints();
					OffensiveAbility offensive = (OffensiveAbility) ability;
					int currentDamage = offensive.getDamage();
					if((currentDamage > currentHighestDamage) && (offensive.getPointCost() < availableAB))
					{
						currentHighestDamage = currentDamage;
						cmd = offensive;
					}
				}
			}	
		}
	
		return cmd;
	}

	/**
	 * Picks current acting hero's highest damage attack.
	 * @param hero: Current acting hero.
	 * @return Current acting hero's highest damage attack.
	 */
	private OffensiveAbility pickHighestDamage(Hero hero)
	{
		// Pick Own best ability
		HashMap<String, Ability> abilities = hero.getAbilities();
		
		int availableAB = hero.getAbilityPoints();
		
		BaseAttack base = (BaseAttack) Ability.getAbility(hero,"BaseAttack");
		int currentHighestDamage = base.getDamage();
		OffensiveAbility cmd = base;
		for(Ability ability : abilities.values())
		{
			if(ability instanceof OffensiveAbility)
			{
				OffensiveAbility offensive = (OffensiveAbility) ability;
				int currentDamage = offensive.getDamage();
				if((currentDamage > currentHighestDamage) && (offensive.getPointCost() < availableAB))
				{
					currentHighestDamage = currentDamage;
					cmd = offensive;
				}
			}
		}
		return cmd;
	}

	/**
	 * Pick strongest healing item if need.
	 * @return Strongest healing item.
	 */
	private Item pickHealingItem()
	{
		Item bestItem = null;
		TreeMap<String, Item> inv = this.getInventory();
		int largestHeal = 0;
		if(!inv.isEmpty())
		{
			for(Item item : inv.values())
			{
				// Make sure to check for healing items 
				if (item instanceof HealthItem)
				{
					int currentHeal = item.getEffectStrength();
					if(currentHeal > largestHeal)
					{
						bestItem = item;
					}
				}
			}
		}
		return bestItem;
	}
	
	public void setController(BattleController controller)
	{
		this.controller = controller;
	}
}
