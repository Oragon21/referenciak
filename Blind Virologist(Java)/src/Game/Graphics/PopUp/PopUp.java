package Game.Graphics.PopUp;

import Game.MapHandling.Map;
import Game.TopEntities.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PopUp {

    protected JDialog d;

    public PopUp(){

        d = new JDialog();
        d.setLayout(new FlowLayout());
        d.setSize(400,100);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        d.setLocation((int)(screenSize.getWidth()/2 - 820/2) + d.getWidth()/2, (int)(screenSize.getHeight()/2) + d.getHeight());
    }

    public void open(Map map, Player targetPlayer){

    }

    public void clear(JButton b){
        for (ActionListener a: b.getActionListeners()) {
            b.removeActionListener(a);
        }
    }

    public void dispose(){
        d.setVisible(false);
    }

    public JDialog getD() {
        return d;
    }

    public void setD(JDialog d) {
        this.d = d;
    }
}
