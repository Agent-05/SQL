import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    public static void main(String[] args){
        new Frame();
    }
    public Frame(){
        this.setLayout(null);
        this.setSize(800,500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topBar.setSize(800,100);

        JMenu file = new JMenu("File");
        JMenu view = new JMenu("View");
        JButton help = new JButton("Help");
        help.setFocusable(false);

        JMenuItem item = new JMenuItem("Text 1");
        file.add(item);

        topBar.add(file);
        topBar.add(view);
        topBar.add(help);

        this.add(topBar);




        this.setVisible(true);
    }
}
