package Game.Scenes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class WinScene extends Scene{
    private JButton bExit;
    private JLabel lWinner;

    public WinScene(){
        bExit = new JButton("EXIT");
        lWinner = new JLabel();

        this.setSize(800,600);


        setLayout(new BorderLayout());
        //háttér betöltése res mappából
        //JLabel background=new JLabel(new ImageIcon("res/scenes/win_bg.jpg"));
        JLabel background=new JLabel();
        add(background);
        background.setLayout(null);

        bExit.setMnemonic(KeyEvent.VK_E);
        bExit.setBounds(300, 400, 200, 100);
        //gombok átláthatóvá tétele
        bExit.setOpaque(false);
        bExit.setContentAreaFilled(false);
        bExit.setBorderPainted(false);
        bExit.setBorder(null);
        bExit.setFocusable(false);
        background.add(bExit);

        bExit.addActionListener(this);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int)(screenSize.getWidth()/2 - this.getSize().getWidth()/2), (int)(screenSize.getHeight()/2 - this.getSize().getHeight()/2));
        this.setVisible(false);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Ablak láthatóvá tétele
     */
    @Override
    public void init() {
        this.setVisible(true);
    }

    /**
     * @param e gombok textjét tárolja
     * ""   = Exit  -> kilép a játékból
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String key = e.getActionCommand();
        if(key == null) System.exit(0);
        else switch (key){
            case "EXIT": {
                System.exit(0);
                break;
            }
            default: System.exit(0);
        }
    }
}
