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
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenu viewMenu = new JMenu("View");
        JButton help = new JButton("Help");

        help.setFocusable(false);

        JMenuItem newItem = new JMenuItem("Title");
        JMenuItem newItem2 = new JMenuItem("Title");
        JMenuItem newItem3 = new JMenuItem("Title");
        JMenuItem newItem4 = new JMenuItem("Title");
        JMenuItem newItem5 = new JMenuItem("Title");

        fileMenu.add(newItem);
        fileMenu.add(newItem2);
        fileMenu.add(newItem3);

        menuBar.add(fileMenu);
        menuBar.add(viewMenu);
        menuBar.add(help);

        topBar.add(menuBar);


        this.add(topBar);




        this.setVisible(true);
    }
}
