package esof322.pa4.team1;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;

import esof322.pa4.team1.ButtonPanel;
import esof322.pa4.team1.CanvasPanel;
import esof322.pa4.team1.MyFrame;

public class MyFrame extends JFrame {
	
	public static MyFrame instance;
	private ButtonPanel buttons;
	private Player newPlayer = new Player("newPlayer", new Dice(), createTiles());
	public static MyFrame getInstance() throws IOException {
		if (instance == null) 
			instance = new MyFrame();
		
		return instance;
	}
	
	private MyFrame() throws IOException {
		super("Monopoly");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		buttons = new ButtonPanel(newPlayer);
		getContentPane().add(buttons, BorderLayout.WEST);
		getContentPane().add(CanvasPanel.getInstance(), BorderLayout.CENTER);
		setSize(1000, 1000);
		setVisible(true);
	}
	 public void resetButtons(Player player) {
		 buttons.revalidate();
	 }
	 private Tile[] createTiles() throws FileNotFoundException, IOException {
		Tile[] gameTiles = new Tile[40];
		try (BufferedReader br = new BufferedReader(new FileReader("monopoly_tiles.txt"))) {
			String line;
			String property[];
			int x = 0;
			while ((line = br.readLine()) != null) {
				property = line.split("/");
				int[] rent = new int[6];
				for(int i = 0; i<6; i++) rent[i] = Integer.parseInt(property[i+2]);
				Tile p = new Tile(property[0], Integer.parseInt(property[1]), rent, 1, Integer.parseInt(property[8]),Integer.parseInt(property[9]));
				gameTiles[x] = p;

				x++;
			}
		}
		return gameTiles;
	 }
}
