package Game.Scenes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class Scene extends JFrame implements ActionListener {

    public abstract void init();
    public abstract void actionPerformed(ActionEvent e);

}
