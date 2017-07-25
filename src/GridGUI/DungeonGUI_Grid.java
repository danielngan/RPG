package GridGUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

public class DungeonGUI_Grid extends JFrame {

	//===========================================================================================
	
	BackGroundPane contentPane;
	
	public DungeonGUI_Grid() throws IOException
	{
		// 1. Construct JFrame
		super();
		String filename = "/Dungeon_Background_49.png";
		contentPane = new BackGroundPane(ImageIO.read(this.getClass().getResource(filename)));
		
		// 2. Choose what happens when the frame closes
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// 3. Create components and put them in the frame
		contentPane.setLayout(new GridBagLayout());

		// 3. Create components and put them in the frame
		createAnimationPane();

		// 4. Size the frame
//		this.setSize(600, 600);
//		this.setLocationRelativeTo( null );
		this.pack();
		
		// 5. Show it
		this.setVisible(true);
	}
	
	//===========================================================================================
	
	private void createAnimationPane() throws IOException
	{

		// Load background
	
		ImageIcon ii = new ImageIcon(this.getClass().getResource("/paladin.gif"));
		Image image = ii.getImage(); // transform it 
		Image newimg = image.getScaledInstance((int) (1024 / 9.0), 250, java.awt.Image.SCALE_DEFAULT); // scale it the smooth way  
		ii = new ImageIcon(newimg);  // transform it back
		JLabel imageLbl = new JLabel(ii);

		ImageIcon enemy = new ImageIcon(this.getClass().getResource("/boss_skele.gif"));
		Image enemyImage = enemy.getImage(); // transform it 
		Image newEnemyimg = enemyImage.getScaledInstance((int) (1024 / 9.0), 250, java.awt.Image.SCALE_DEFAULT); // scale it the smooth way  
		enemy = new ImageIcon(newEnemyimg);  // transform it back
		JLabel enemyLbl = new JLabel(enemy);

		ImageIcon door = new ImageIcon(this.getClass().getResource("/Door.png"));
		Image doorImage = door.getImage(); // transform it 
		Image newDoorimg = doorImage.getScaledInstance((int) (1024 / 9.0), 250, java.awt.Image.SCALE_DEFAULT); // scale it the smooth way  
		door = new ImageIcon(newDoorimg);  // transform it back
		JLabel doorLbl = new JLabel(door);
		
		// Add animation to JLabel 
		KeyboardAnimation animation = new KeyboardAnimation(imageLbl, 24);
		animation.addAction("LEFT", -3,  0);
		animation.addAction("RIGHT", 3,  0);

		animation.addAction("control LEFT", -5,  0);
		animation.addAction("V",  5,  5);
		
		// Add characters to layout
		GridBagConstraints c = makeGbc(0,0);
		contentPane.add(imageLbl, c);
	    c = makeGbc(4,0);
		contentPane.add(enemyLbl, c);
		c = makeGbc(5,0);
		contentPane.add(doorLbl, c);
		
		// Add to JPanel with back ground
		this.getContentPane().add(contentPane);
//			this.getContentPane().add(enemyLbl, BorderLayout.EAST);
		this.repaint();
	}
	
	public GridBagConstraints makeGbc(int x, int y)
	{
		GridBagConstraints gbc = new GridBagConstraints();
		
	    gbc.gridx = x;
	    gbc.gridy = y;
	    gbc.weightx = 0.5;
	    gbc.weighty = 0.5;
	    
    	gbc.gridwidth=1;
    	gbc.gridheight=1;
    	gbc.fill = GridBagConstraints.BOTH;
    	
    	return gbc;
	}
			
	//=================================================================================================
	
	public static void main(String[] args) throws IOException
	{
		new DungeonGUI_Grid();
	}
	
}
