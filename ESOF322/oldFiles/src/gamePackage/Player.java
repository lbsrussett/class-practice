package gamePackage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Player {
	
	private Scanner in = new Scanner( System.in );
	
	private Dice playDice;
	private String Token;
	private boolean inJail = false;
	private int money = 1500;
	private int position = 0;
	private static Tile[] gameTiles;
	private String name;
	private Tile currentTile;
	private ArrayList<Tile> ownedProperties = new ArrayList<Tile>();
	private boolean hasMonopoly = false;
	
	public Player(String playerName, Dice playDice, Tile[] gameTiles) throws FileNotFoundException, IOException {
		name = playerName;
		this.playDice = playDice;
		this.gameTiles = gameTiles;
	//	boardSetup();
	}
	
	
		// Method to take a turn, resolving jail then the roll and the results of the roll
	public void takeTurn() {
		int doubles = 0;
		boolean wasInJail = inJail;
		int turnsInJail = 0;

		System.out.println("It is now "+name+"'s turn.");
			// while loop to roll again unless 3 doubles are rolled
		do {
			int roll = playDice.rollPair();
				
			// if in jail, check for doubles then allow option to pay to get out of jail
			if (inJail == true) {

				System.out.println("You are currently in Jail.");
				printRoll();
				
				// if the player is in jail and rolled doubles
				if (playDice.isDouble() == true) {
					getOutOfJail();
					resolvePosition(roll);
				}
				
				// if three turns have passes player must pay
				else {
					if (turnsInJail == 3) {
						payMoney(50);
						getOutOfJail();
						turnsInJail = 0;
					}
					
					// if less than 3 turns have passed, allow option to pay $50
					else {
						System.out.println("Would you like to pay $50 to get out of Jail? Yes or No");
						String yesOrNo;
						do {
							yesOrNo = in.nextLine();
						}
						while ( yesOrNo.compareToIgnoreCase("Yes") != 0 && yesOrNo.compareToIgnoreCase("No") != 0);
						
						if (yesOrNo.compareToIgnoreCase("Yes") == 0) {
							getOutOfJail();
						}
					}				
				}
				
			}
			
			// if not in jail, resolve turn as normal
			else {
				printRoll();
				resolvePosition(roll);
			}
			
			// after roll is resolved, give menu option to player
			menu();
			
			// if player rolled double and wasn't in jail, prompt message to roll again
			if (playDice.isDouble() == true && !wasInJail) {
				doubles ++;
				System.out.println("You rolled doubles, roll again!");
			}
			
		}
		while ( (playDice.isDouble() == true && !wasInJail)  && doubles < 3 );
		
		// if 3 doubles are rolled in a row, player goes to jail
		if (doubles >= 3) {
			System.out.println("You rolled 3 doubles in a row, go to Jail.");
			inJail = true;
			position = 10;
			menu();
		}
		System.out.println("You end your turn.");
	}	// Turn end
	
	// method to return net worth of player at end of game
	public int getWorth() {
		int worth = money;
		for (int i = 0; i < ownedProperties.size(); i++) {
			if (ownedProperties.get(i).isMorgaged() == false) {
				worth += ownedProperties.get(i).getHousesValue();
				worth += ownedProperties.get(i).getMorgageValue();
			}
		}
		return worth;
	}
	
	// method to get player out of jail
	private void getOutOfJail() {
		inJail = false;
		System.out.println("You are no longer in Jail");		
		
	}


	// method to pay money, taking from money if there is enough and if not sell assets to pay
	private void payMoney(int payment) {
		if (money >= payment) money -= payment;
		else sellAssets(payment - money);
	}
	
	// method to sell off houses or properties
	private void sellAssets(int payment) {
		if (ownedProperties.isEmpty()) System.out.println("You do not own any properties.");
		else{
			for (int i = 0; i < ownedProperties.size(); i++) {
				Tile currentProperty = ownedProperties.get(i);
				if ( !(currentProperty.isMorgaged()) ) {
					System.out.println("Would you like to sell "+currentProperty.getName()+" for "+currentProperty.getMorgageValue()+"? Yes or No");
					String yesOrNo;
					do {
						yesOrNo = in.nextLine();
					}
					while ( yesOrNo.compareToIgnoreCase("Yes") != 0 && yesOrNo.compareToIgnoreCase("No") != 0);
					
					if (yesOrNo.compareToIgnoreCase("Yes") == 0) currentProperty.morgage();

				}
			}
		}
	}
	
	// method to resolve position and if player passed go 
	private void resolvePosition(int roll) {
		position += roll;
		if (position > 39) {
			position = position%40;
			System.out.println("You have passed GO, collect $200.");
			getPayed(200);
		}
		
		if (position == 30) {
			goToJail();
		}
		
		currentTile = gameTiles[position];
		if (currentTile.isProperty()) {
			Property p = currentTile.getProperty();
			if (p.isOwned() && p.getOwner() != this) {
				int tileRent = p.getRent();
				System.out.println(p.getName()+" is already owned by " + p.getOwner().getName()+ ". You must pay the rent, $" + tileRent);
				payMoney(tileRent);
				p.getOwner().payMoney(tileRent);
			}
		}
		
	}
	
	// player menu with options to pass turn, list properties, buy houses, sell assets, or trade with other players
	private void menu() {
		
		int menuChoice=0;
		while (menuChoice != 1) {
			printMenu();
			menuChoice = in.nextInt();
			switch (menuChoice) {
			case 1: 
				break;
			case 2:
				printProperties();
				break;
			case 3:
				hasMonopoly = checkMonopoly();
				if (hasMonopoly == true) 
					buyHouses();
				else
					System.out.println("You do not have a monopoly and cannot buy a house");
				break;
			case 4:
				buyProperty();
				break;
			case 5:
				sellAssets();
				break;
			default:
				System.out.println("Please enter a valid menu choice");
				break;
			}
		}
	}
	
	// print menu to display menu options available to player
	private void printMenu() {
		System.out.println("Current player: "+name+". Current tile: "+currentTile.getName());
		System.out.println("What would you like to do? "
				+ "\n 1. Pass "
				+ "\n 2. View Properties"
				+ "\n 3. Buy Houses "
				+ "\n 4. Buy Property "
				+ "\n 5. Sell Assets");
	}
	
	private void goToJail() {
		position = 10;
		inJail = true;
	}
	
	private boolean checkMonopoly() {
		// TODO: run a check through player's list of owned properties
		return hasMonopoly;
	}
	
	private void sellAssets() {
		if (ownedProperties.isEmpty()) 
			System.out.println("You do not own any properties.");
	}
	
	private void printProperties() {
		
		if (ownedProperties.isEmpty() || ownedProperties.size() == 0) 
			System.out.println("You do not own any properties.");
		else {
			
			for (int i = 0; i < ownedProperties.size(); i++) {
				System.out.println(ownedProperties.get(i).getName());				
			}
		}
	}
	
	
	private void buyProperty() {
		if (currentTile.isProperty()) {
			Property p = currentTile.getProperty();
			if (!p.isOwned()) {
				System.out.println(currentTile.getName()+"- Price: $"+ p.getPrice());
				System.out.println("Would you like to purchase? 1-Yes, 2-No");
				if (in.nextInt() == 1) {
					payMoney(p.getPrice());
					ownedProperties.add(p);
					p.setOwner(this);
				}
			}
			else
				System.out.println("This property is already owned by "+p.getOwner().name);
		}
		
		else if (currentTile.isUtility() ) {
			Utility u = currentTile.getUtility();
			if (!u.isOwned()) {
				System.out.println(currentTile.getName()+"- Price: "+ u.getPrice());
				System.out.println("Would you like to purchase? 1-Yes, 2-No");
				if (in.nextInt() == 1) {
					payMoney(u.getPrice());
					ownedProperties.add(u);
					u.setOwner(this);
				}
			}
			else
				System.out.println("This utility is already owned by "+u.getOwner().name);
		}
		
		else if (currentTile.isRailroad()) {
			Railroad r = currentTile.getRailroad();
			if (!r.isOwned()) {
				System.out.println(currentTile.getName()+"- Price: "+ r.getPrice());
				System.out.println("Would you like to purchase? 1-Yes, 2-No");
				if (in.nextInt() == 1) {
					payMoney(r.getPrice());
					ownedProperties.add(r);
					r.setOwner(this);
				}
			}
			else
				System.out.println("This Railroad is already owned by "+ r.getOwner().name);
		}
		else {
			System.out.println("This tile cannot be purchased");
		}
		
	}
	
	private void buyHouses() {
		Property p = currentTile.getProperty();
		if (p.isOwned()) {
			if (p.getOwner() == this) {
				System.out.println("You currently own"+p.getHousesValue()+" houses on this property.");
				if (p.getHouses() < 4) {
					//TODO: add price for purchasing a house
					System.out.println("Buy house for"+p.getHousesValue());
				}
				else {
					System.out.println("You already own 4 houses. Purchase a hotel for $ (VALUE)? 1-Yes, 2-No");
					if (in.nextInt() == 1) {
						//payMoney(price for hotel)
						p.addHotel();
					}
				}
			}
			else {
				System.out.println("You do not own this property");
			}
		}
	}
	
	public void getPayed(int payment) {
		money += payment;
	}
	
	private void printRoll() {
		System.out.println("You rolled "+playDice.getDie1()+" + "+playDice.getDie2()+" = "+playDice.getTotalRoll());
	}
	
	public Tile getTile() {
		return gameTiles[position%40];
	}
	
	public int getMoney() {
		return money;
	}
	
	public String getName() {
		return name;
	}
	
	
}
