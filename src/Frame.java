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
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setTitle("School Manager Program");

        JPanel mainPanel = new JPanel();
        teacherPanel tp=new teacherPanel();
        studentPanel sp=new studentPanel();
        coursePanel cp=new coursePanel();
        sectionPanel secP =new sectionPanel();
        aboutPanel ap =new aboutPanel();

        mainPanel.setBounds(0,25,784,437);
        JLabel text1 = new JLabel("Choose a View");
        mainPanel.add(text1);
        this.add(mainPanel);

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topBar.setBounds(0,0,800,25);
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenu viewMenu = new JMenu("View");
        JMenu helpMenu = new JMenu("Help");

        JMenuItem exportItem = new JMenuItem("Export Data");
        JMenuItem importItem = new JMenuItem("Import Data");
        JMenuItem purgeItem = new JMenuItem("Purge Data");
        JMenuItem exitItem = new JMenuItem("Exit Program");

        exportItem.addActionListener(e -> {
            mainPanel.setBackground(Color.cyan);
        });

        importItem.addActionListener(e -> {
            mainPanel.setBackground(Color.gray);

        });

        purgeItem.addActionListener(e -> {
            mainPanel.setBackground(Color.green);

        });
        exitItem.addActionListener(e -> {
            System.exit(0);
        });



        JMenuItem teacherItem = new JMenuItem("Teacher View");
        JMenuItem studentItem = new JMenuItem("Student View");
        JMenuItem courseItem = new JMenuItem("Course View");
        JMenuItem sectionItem = new JMenuItem("Section View");

        teacherItem.addActionListener(e -> {
            this.remove(mainPanel);
            this.remove(tp);
            this.remove(sp);
            this.remove(secP);
            this.remove(cp);
            this.remove(ap);

            this.add(tp);
            repaint();
        });

        studentItem.addActionListener(e -> {
            this.remove(mainPanel);
            this.remove(tp);
            this.remove(sp);
            this.remove(secP);
            this.remove(cp);
            this.remove(ap);

            this.add(sp);
            repaint();
        });

        courseItem.addActionListener(e -> {
            this.remove(mainPanel);
            this.remove(tp);
            this.remove(sp);
            this.remove(secP);
            this.remove(cp);
            this.remove(ap);

            this.add(cp);
            repaint();
        });

        sectionItem.addActionListener(e -> {
            this.remove(mainPanel);
            this.remove(tp);
            this.remove(sp);
            this.remove(secP);
            this.remove(cp);
            this.remove(ap);

            this.add(secP);
            repaint();
        });


        JMenuItem aboutItem = new JMenuItem("About");

        aboutItem.addActionListener(e -> {
            this.remove(mainPanel);
            this.remove(tp);
            this.remove(sp);
            this.remove(secP);
            this.remove(cp);
            this.remove(ap);

            this.add(ap);
            repaint();
        });





        fileMenu.add(exportItem);
        fileMenu.add(importItem);
        fileMenu.add(purgeItem);
        fileMenu.add(exitItem);

        viewMenu.add(teacherItem);
        viewMenu.add(studentItem);
        viewMenu.add(courseItem);
        viewMenu.add(sectionItem);

        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);
        menuBar.setBackground(new Color(162, 162, 162));

        topBar.add(menuBar);
        topBar.setBackground(new Color(162, 162, 162));


        this.add(topBar);



        this.setVisible(true);
    }
}
