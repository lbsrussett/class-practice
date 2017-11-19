package MonopolyV1.gamePackage;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ButtonPanel extends JPanel implements ActionListener {
	//private Tile[] = new Tile[1];
	private Player p; //= new Player("dummyPlayer", new Dice(), new Tile[1]);
	
	ButtonPanel(Player player) {
	JButton one = new JButton("Add Players");
	JButton two = new JButton("Take Turn");
	JButton three = new JButton("Manage Property");
	JButton four = new JButton("End Game");
	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	add(one);
	add(two);
	add(three);
	add(four);
	setBackground(Color.white);
	one.addActionListener(this);
	two.addActionListener(this);
	three.addActionListener(this);
	four.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		System.out.println(e.toString());
		if (command.equals("Add Players")) {
			// call the canvas and tell it shape = 1
			CanvasPanel.getInstance().setShape(1);
		}
		else if(command.equals("Take Turn")) {
		}
		else if(command.equals("Manage Properties")) {
			
		}
		else if(command.equals("End Game")) {
			
		}
	}
}
