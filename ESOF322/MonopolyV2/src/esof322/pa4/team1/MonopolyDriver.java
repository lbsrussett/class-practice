package esof322.pa4.team1;

import java.io.*;
import java.util.*;

import javax.swing.JFrame;

public class MonopolyDriver {
	
	public MonopolyDriver(){}

	static Scanner in = new Scanner( System.in );
	static Dice playDice = new Dice();
	static Player[] gamePlayers = new Player[0];
	private static Tile[] gameTiles;
	private static boolean runGUI = false;

	static final int MINUTES = 60*1000; // 60 seconds per min * 1000 milliseconds per second
	static final int MIN_PLAYERS = 2;
	static final int MAX_PLAYERS = 4;
	
	public static void main(String[] args) throws IOException {
		
		System.out.println("Would you like to use the GUI? 1-Yes 2-No");
		if (in.nextInt() == 1) runGUI = true;
		if (runGUI) MyFrame.getInstance();
		
		setup();
		runGame();
		displayWinner(calculateWinner());

	}

	private static int createTimer() {
		int time = 0;

		try {
			do {
				System.out.println("After how many minutes would you like the game to end:");
				time = in.nextInt();
				in.nextLine(); // clear line if text was entered after integer
				if (time < 0) System.out.println("Please enter a positive integer");
			}
			while (time < 0);
		}
		catch (InputMismatchException e) {
			System.out.println("Error, please try again and enter an integer.");
		}
		return time;
	}

	protected static void setup() throws IOException {
		//Set up board with tiles and properties
		gameTiles = new Tile[40];
		try (BufferedReader br = new BufferedReader(new FileReader("monopoly_tiles.txt"))) {
			String line;
			String property[];
			int x = 0;
			while ((line = br.readLine()) != null) {

				property = line.split("/");
				int[] rent = new int[6];
				for(int i = 0; i<6; i++) rent[i] = Integer.parseInt(property[i+2]);
				Tile p = new Tile(property[0], Integer.parseInt(property[1]), rent, setUpHouseValue(x), Integer.parseInt(property[8]),Integer.parseInt(property[9]));
				gameTiles[x] = p;

				x++;
			}
		}

		try {
			int players = 0;
			do {
				System.out.println("Please enter the number of players:");
				players = in.nextInt();
				if (players < MIN_PLAYERS || players > MAX_PLAYERS) System.out.println("Please enter between 2 and 4 players.");
			}
			while (players < MIN_PLAYERS || players > MAX_PLAYERS);
			in.nextLine(); // clear line if text was entered after integer
			gamePlayers= new Player[players];
			for(int i = 0; i < players; i++) {
				System.out.println("Enter player "+ (i+1) +"'s name:");
				String name = in.nextLine();
				gamePlayers[i] = new Player(name, playDice, gameTiles);
				// add token selection
			}
		}
		catch (InputMismatchException e) {
			System.out.println("Error, please try again and enter an integer.");
		}


	}

	private static int setUpHouseValue(int x) {
		if ( x<10 ) return 50;
		else if ( x< 20 ) return 100;
		else if ( x<30 ) return 150;
		else return 200;
	}

	private static void runGame() throws IOException {

		int timeLimit = createTimer();
		long start = System.currentTimeMillis();
		long end = start + timeLimit*MINUTES; // time limit * 60 seconds * 1000 ms/sec
		
		while(System.currentTimeMillis() < end) {
			for (int i = 0; i < gamePlayers.length; i++) {
				takeTurn(gamePlayers[i]);
				// update GUI
			}

		}
	}


	// Method to take a turn, resolving jail then the roll and the results of the roll
	protected static void takeTurn(Player currentPlayer) throws IOException {
		MyFrame myFrame;
		if(runGUI) {
			myFrame = MyFrame.getInstance();
			myFrame.resetButtons(currentPlayer);
		}
		int doubles = 0;
		boolean inJail = currentPlayer.isInJail();
		boolean wasInJail = inJail;
		int turnsInJail = 0;

		System.out.println("It is now "+currentPlayer.getName()+"'s turn.");
		// while loop to roll again unless 3 doubles are rolled
		do {
			int roll = playDice.rollPair();
			currentPlayer.setRoll(roll);
			
			// if in jail, check for doubles then allow option to pay to get out of jail
			if (inJail == true) {

				System.out.println("You are currently in Jail.");
				printRoll();

				// if the player is in jail and rolled doubles
				if (playDice.isDouble() == true) {
					currentPlayer.getOutOfJail();
					currentPlayer.resolvePosition();
				}

				// if three turns have passes player must pay
				else {
					if (turnsInJail == 3) {
						currentPlayer.payMoney(50);
						currentPlayer.getOutOfJail();
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
							currentPlayer.getOutOfJail();
						}
					}
				}

			}

			// if not in jail, resolve turn as normal
			else {
				printRoll();
				currentPlayer.resolvePosition();
			}

			// after roll is resolved, give menu option to player
			menu(currentPlayer);

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
			currentPlayer.setPosition(10);
			menu(currentPlayer);
		}
		System.out.println("You end your turn.");
	}	// Turn end

	public static void printRoll() {
		System.out.println("You rolled "+playDice.getDie1()+" + "+playDice.getDie2()+" = "+playDice.getTotalRoll());
	}

	// player menu with options to pass turn, list properties, buy houses, sell assets, or trade with other players
	private static void menu(Player currentPlayer) {

		int menuChoice=0;
		while (menuChoice != 1) {
			printMenu(currentPlayer);
			menuChoice = in.nextInt();
			switch (menuChoice) {
				case 1:
					break;
				case 2:
					currentPlayer.printProperties();
					break;
				case 3:
					currentPlayer.printMoney();
					break;
				case 4:
					if (currentPlayer.checkMonopoly())
						currentPlayer.buyHouses();
					else
						System.out.println("You do not have a monopoly and cannot buy any houses.");
					break;
				case 5:
					currentPlayer.buyProperty();
					break;
				case 6:
					currentPlayer.sellAssets();
					break;
				default:
					System.out.println("Please enter a valid menu choice");
					break;
			}
		}
	}

	// print menu to display menu options available to player
	private static void printMenu(Player currentPlayer) {
		System.out.println("Current player: "+currentPlayer.getName()+". Current tile: "+currentPlayer.getTile().getName());
		System.out.println("What would you like to do? "
				+ "\n 1. Pass "
				+ "\n 2. View Properties"
				+ "\n 3. View Money"
				+ "\n 4. Buy Houses "
				+ "\n 5. Buy Property "
				+ "\n 6. Sell Assets");
	}

	private static Player calculateWinner() {
		Player highestWorth = gamePlayers[0];
		for(int i = 1; i < gamePlayers.length; i++) {
			int worth = gamePlayers[i].getWorth();
			if (worth > highestWorth.getWorth()) highestWorth = gamePlayers[i];
		}
		return highestWorth;
	}

	private static void displayWinner(Player winner) {
		System.out.println(winner.getName()+" won!"
				+ "\nCongradulations!");
	}

}