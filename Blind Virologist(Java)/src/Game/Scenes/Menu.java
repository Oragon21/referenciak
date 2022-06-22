package Game.Scenes;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static Game.Scenes.SceneManager.SceneType.GAME;

public class Menu extends Scene {

	private JButton bStart, bHelp, bExit;

	public Menu() {
		bStart = new JButton("START");
		bHelp = new JButton("HELP");
		bExit = new JButton("EXIT");


		this.setSize(800, 600);


		setLayout(new BorderLayout());
		//háttér betöltése a res mappából
		JLabel background = new JLabel(new ImageIcon("src/Menu/o_page.png"));
		//JLabel background=new JLabel();
		add(background);
		background.setLayout(null);


		bStart.setBounds(300, 350, 200, 40);
		bStart.setBorderPainted(true);
		bStart.setFocusable(false);
		bStart.setFont(new Font("Arial Black", Font.BOLD, 30));
		bStart.setForeground(Color.white);
		bStart.setBackground(new java.awt.Color(240, 194, 56));
		background.add(bStart);

		bHelp.setBounds(300, 400, 200, 40);
		bHelp.setBorderPainted(true);
		bHelp.setFocusable(false);
		bHelp.setFont(new Font("Arial Black", Font.BOLD, 30));
		bHelp.setForeground(Color.white);
		bHelp.setBackground(new java.awt.Color(240, 194, 56));
		background.add(bHelp);

		bExit.setBounds(300, 450, 200, 40);
		bExit.setBorderPainted(true);
		bExit.setFocusable(false);
		bExit.setFont(new Font("Arial Black", Font.BOLD, 30));
		bExit.setForeground(Color.white);
		bExit.setBackground(new java.awt.Color(240, 194, 56));
		background.add(bExit);

		bStart.addActionListener(this);
		bHelp.addActionListener(this);
		bExit.addActionListener(this);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int) (screenSize.getWidth() / 2 - this.getSize().getWidth() / 2), (int) (screenSize.getHeight() / 2 - this.getSize().getHeight() / 2));
		this.setVisible(false);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void init() {
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String key = e.getActionCommand();
		if (key == null) System.exit(0);
		else switch (key) {
			case "START": {

				this.setVisible(false);
				SceneManager.getInstance().changeScene(GAME);
				break;
			}
			case "HELP": {
				JOptionPane.showMessageDialog(this, "No help :-)");
				break;
			}
			case "EXIT": {
				System.exit(0);
			}
		}
	}
}

