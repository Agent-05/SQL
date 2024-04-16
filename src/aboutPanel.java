import javax.swing.*;
import java.awt.*;

public class aboutPanel extends JPanel {
    public aboutPanel(){
        this.setLayout(null);
        this.setBounds(0,25,800,437);
        JLabel txt = new JLabel("Created By Christopher and Daniel");
        JLabel txt2 = new JLabel("Version 1.0.0");
        txt.setBounds(300, 50, 1000, 100);
        txt2.setBounds(300, 100, 100, 100);

        this.add(txt);
        this.add(txt2);


    }
}
