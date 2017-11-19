package gamePackage;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class MonopolyDriver {
	
	static Scanner in = new Scanner( System.in );
	static Dice playDice = new Dice();
	static Player[] gamePlayers = new Player[0];
	private static Tile[] gameTiles;
	
	static final int MINUTES = 60*1000; // 60 seconds per min * 1000 milliseconds per second
	static final int MIN_PLAYERS = 2;
	static final int MAX_PLAYERS = 4;
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
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
	
	private static void setup() throws IOException {
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
				Tile p = new Tile(property[0], Integer.parseInt(property[1]), rent, setUpHouseValue(x), Integer.parseInt(property[8]));
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
	
	private static void runGame() {
		
		int timeLimit = createTimer();
		long start = System.currentTimeMillis();
		long end = start + timeLimit*MINUTES; // time limit * 60 seconds * 1000 ms/sec
		while(System.currentTimeMillis() < end) {
			for (int i = 0; i < gamePlayers.length; i++) {
				gamePlayers[i].takeTurn();
				// update GUI
			}
			
		}
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
				+ "\n Congradulations!");
	}

}
