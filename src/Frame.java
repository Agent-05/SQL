import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class Frame extends JFrame {
    teacherPanel tp=new teacherPanel(this);
    studentPanel sp=new studentPanel(this);
    coursePanel cp=new coursePanel(this);
    sectionPanel secP =new sectionPanel(this);
    enrollmentManager enrollmentManager = new enrollmentManager();
    public static void main(String[] args){
        new Frame();
    }
    public Frame(){
        this.setLayout(null);
        this.setSize(815,400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("School Manager Program");

        JPanel mainPanel = new JPanel();
        aboutPanel ap =new aboutPanel();
        tp.getNames();
        sp.getNames();


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
            exportFile();
        });

        importItem.addActionListener(e -> {
            importFile();

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

    public void importFile()
    {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Import File");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);//only a file can be imported
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            System.out.println("getCurrentDirectory(): "
                    +  chooser.getCurrentDirectory());
            System.out.println("getSelectedFile() : "
                    +  chooser.getSelectedFile());
        }
        else {
            System.out.println("No Selection ");
        }
    }

    public void exportFile()
    {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Export File");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//only a folder can be chosen to be exported to
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try{
                File f = new File(chooser.getCurrentDirectory() + "\\School Manager Data1.txt");//sets path
                f.createNewFile();
                //sets path
                FileWriter myWriter = new FileWriter(f);
                myWriter.write(createTextFile());
                myWriter.close();
            }catch (Exception e){System.out.println("Error creating file");};
            System.out.println("getCurrentDirectory(): "
                    +  chooser.getCurrentDirectory());
            System.out.println("getSelectedFile() : "
                    +  chooser.getSelectedFile());
        }
        else {
            System.out.println("No Selection ");
        }
    }


    public studentPanel getStup() {
        return sp;
    }


    public String createTextFile(){
        String newFile = "";
        ArrayList<Enrollment> e = enrollmentManager.enrollment;
        ArrayList<Student> s = sp.students;
        ArrayList<Teacher> t = tp.teachers;
        ArrayList<Section> x = secP.sections;
        ArrayList<Course> c = cp.courses;
        for(Enrollment a : e)
        {
            newFile += "INSERT INTO enrollment (id, student_id) VALUES ("+a.getId()+", "+a.getStudentId()+")\n";
        }

        for(Student a : s)
        {
            newFile += "INSERT INTO student (first_name, last_name) VALUES ("+a.getId()+"\'"+a.getFn()+"\', \'"+a.getLn()+"\')\n";
        }

        for(Teacher a : t)
        {
            newFile += "INSERT INTO student (first_name, last_name) VALUES ("+a.getId()+"\'"+a.getFn()+"\', \'"+a.getLn()+"\')\n";
        }
        for(Section a : x)
        {
            newFile += "INSERT INTO section (id, course_id, teacher_id) VALUES ("+a.getId()+", "+a.getCourseId()+", "+a.getTeacherId()+")\n";
        }
        for(Course a : c)
        {
            newFile += "INSERT INTO student (first_name, last_name) VALUES ("+a.getId()+"\'"+a.getTitle()+"\', "+a.getDiff()+")\n";
        }
        return newFile;
    }

}
