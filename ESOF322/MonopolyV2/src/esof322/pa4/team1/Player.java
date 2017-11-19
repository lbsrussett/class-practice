package esof322.pa4.team1;

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
	private int roll;
	private int[] ownedGroups = new int[9];
	private final int[] tilesNeededForMonopoly = {40,2,3,3,3,3,3,3,2};
	private boolean[] groupHasMonopoly = {false,false,false,false,false,false,false,false,false};
	
	public Player(String playerName, Dice playDice, Tile[] gameTiles) throws FileNotFoundException, IOException {
		name = playerName;
		this.playDice = playDice;
		this.gameTiles = gameTiles;
	//	boardSetup();
	}
	
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
	public void getOutOfJail() {
		inJail = false;
		System.out.println("You are no longer in Jail");		
		
	}


	// method to pay money, taking from money if there is enough and if not sell assets to pay
	public void payMoney(int payment) {
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
					System.out.println("Would you like to sell "+currentProperty.getName()+" for $"+currentProperty.getMorgageValue()+"? Yes or No");
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
	
	public void setRoll(int roll) {
		this.roll = roll;
	}
	
	// method to resolve position and if player passed go 
	public void resolvePosition() {
		position += roll;
		if (position > 39) {
			position = position%40;
			System.out.println("You have passed GO, collect $200.");
			payPlayer(200);
		}
		
		if (position == 30) {
			goToJail();
		}
		
		currentTile = gameTiles[position];
		if (currentTile.isProperty()) {
			Property p = currentTile.getProperty();
			if (p.isOwned() && (p.getOwner() != this)) {
				int tileRent = p.getRent();
				System.out.println(p.getName()+" is already owned by " + p.getOwner().getName()+ ". You must pay the rent, $" + tileRent);
				payMoney(tileRent);
				p.getOwner().payPlayer(tileRent);
			}
		}
		
		if (currentTile.isRailroad()) {
			Railroad p = currentTile.getRailroad();
			if (p.isOwned() && (p.getOwner() != this)) {
				int tileRent = p.getRent();
				System.out.println(p.getName()+" is already owned by " + p.getOwner().getName()+ ". You must pay the rent, $" + tileRent);
				payMoney(tileRent);
				p.getOwner().payPlayer(tileRent);
			}
		}
		
		if (currentTile.isUtility()) {
			Utility p = currentTile.getUtility();
			if (p.isOwned() && (p.getOwner() != this)) {
				int tileRent = p.getRent();
				System.out.println(p.getName()+" is already owned by " + p.getOwner().getName()+ ". You must pay the rent, $" + tileRent);
				payMoney(tileRent);
				p.getOwner().payPlayer(tileRent);
			}
		}
		
	}
	
	private void goToJail() {
		position = 10;
		inJail = true;
	}
	
	public boolean checkMonopoly() {

		for (int i = 0; i < ownedGroups.length; i++) {
			if (ownedGroups[i] == tilesNeededForMonopoly[i]) { 
				hasMonopoly = true;
				groupHasMonopoly[i] = true;
			}
		}
		
		return hasMonopoly;
	}
		
	public void printProperties() {
		
		if (ownedProperties.isEmpty() || ownedProperties.size() == 0) 
			System.out.println("You do not own any properties.");
		else {
			for (int i = 0; i < ownedProperties.size(); i++) {
				System.out.println(ownedProperties.get(i).getName());				
			}
		}
	}
	
	public void printMoney() {
		System.out.println("You currently have $"+ money);
	}
	
	public void buyProperty() {
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
			
		ownedGroups[currentTile.getPropGroup()] ++;
		checkMonopoly();
		
		}
		else {
			System.out.println("This tile cannot be purchased");
		}
		
	}
	
	public void buyHouses() {
		for (int i = 0; i < ownedProperties.size(); i++) {
			System.out.println("1");
			if ( groupHasMonopoly[ownedProperties.get(i).getPropGroup()]) {
				System.out.println("2");
				System.out.print("Would you like to buy a house on ");
				System.out.print(ownedProperties.get(i).getName());
				System.out.println("for $"+ ownedProperties.get(i).getHousesValue() +"? 1-Yes 2-No");
				if(in.nextInt() == 1) {
					ownedProperties.get(i).getProperty().addHouse();
					payMoney(ownedProperties.get(i).getProperty().getHousesValue());
				}
			}
		}
	}
	
	public int getNumRailroads() {
		int numRailroads = 0;
		for (int i = 0; i < ownedProperties.size(); i++) {
			if (ownedProperties.get(i).isRailroad() == true) numRailroads ++;
		}
		return numRailroads;
	}
	
	public void sellAssets() {
		
	}
	
	public int getNumUtilities() {
		int numUtilities = 0;
		for (int i = 0; i < ownedProperties.size(); i++) {
			if (ownedProperties.get(i).isUtility() == true) numUtilities ++;
		}
		return numUtilities;
	}
	
	public int getRoll() {
		return playDice.getTotalRoll();
	}
	
	public void setPosition(int position) {
		this.position = position;
	}
	
	public void payPlayer(int payment) {
		money += payment;
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
	
	public boolean isInJail() {
		return inJail;
	}
	
public Player getPlayer() {
		return this;
	}
	
}