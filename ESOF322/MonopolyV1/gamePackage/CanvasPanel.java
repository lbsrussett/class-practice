package MonopolyV1.gamePackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CanvasPanel extends JPanel implements MouseListener {
	
	public static CanvasPanel instance;
	private int shape;
	
	public static CanvasPanel getInstance() {
		if (instance == null)
			instance = new CanvasPanel();
		
		return instance;
	}

	private CanvasPanel() {
		ImageIcon image = new ImageIcon("monopoly.jpg");
		JLabel label = new JLabel(image, JLabel.CENTER);
		this.add(label, BorderLayout.CENTER );
		setVisible(true);
		addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println(e.getX() + " and " + e.getY());
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		//System.out.println(e.getX() + " and " + e.getY());
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void setShape(int s) {
		this.shape = s;
	}
}
