import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.*;

public class teacherPanel extends JPanel {
    ArrayList<Teacher> teachers = new ArrayList<Teacher>();
    JList<Teacher> teacherList = new JList<Teacher>();
    Frame parent = null;
    public teacherPanel(Frame parent){
        this.parent = parent;
        this.setLayout(null);
        this.setBounds(0,25,800,437);
        /*getNames();
        teacherList.setListData(toArr(teachers));
        */JScrollPane teacherFrame = new JScrollPane(teacherList);
        teacherFrame.setBounds(10, 10, 280, 275);
        this.add(teacherFrame);

        JLabel ID = new JLabel("ID:");
        JLabel txt1 = new JLabel("First Name:");
        JLabel txt2 = new JLabel("Last Name:");
        JTextField fName = new JTextField();
        JTextField idName = new JTextField();
        JTextField lName = new JTextField();
        JButton save = new JButton();
        JButton clear = new JButton();
        JButton deselect = new JButton();
        JButton delete = new JButton();

        //Makes the Table Model Variables
        String[] columnTitles = {"Section ID", "Course Title"};
        String[][] data = {};
        DefaultTableModel tableModel = new DefaultTableModel(data, columnTitles){
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        //Makes the Jtable and sets the basic functions
        JTable table = new JTable(tableModel);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.setFocusable(false);
        //Adds a scroll panel to the tabel
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(510, 10, 280, 275);
        scrollPane.setViewportView(table);
        this.add(scrollPane);



        txt1.setBounds(300, 60, 100, 25);
        txt2.setBounds(300, 100, 100, 25);
        ID.setBounds(300, 20, 100, 25);


        fName.setBounds(400, 62, 100, 17);
        idName.setBounds(400, 22, 100, 17);
        lName.setBounds(400, 102, 100, 17);
        deselect.setBounds(300, 260, 200, 25);
        save.setBounds(300, 170, 200, 25);
        clear.setBounds(300, 200, 200, 25);
        delete.setBounds(300, 230, 200, 25);

        idName.setFocusable(false);

        this.add(txt1);
        this.add(ID);
        this.add(idName);
        this.add(txt2);
        this.add(fName);
        this.add(lName);
        this.add(save);
        this.add(clear);
        this.add(deselect);
        this.add(delete);

        deselect.setText("Deselect");
        save.setText("Save");
        clear.setText("Clear");
        delete.setText("Delete");

        deselect.setVisible(false);
        delete.setVisible(false);

        //All the buttons and things are now on the Panel, now the logic is to be coded




        save.addActionListener(e -> {
            if (!fName.getText().isEmpty() && !lName.getText().isEmpty()) {
                if (teacherList.isSelectionEmpty()) {
                        Teacher teacher = new Teacher(fName.getText(),lName.getText());
                        teachers.add(teacher);
                        teacherList.setListData(toArr(teachers));
                        getNames();
                } else {
                    Teacher teacher = teacherList.getSelectedValue();
                    teacher.updateTeacher(teacher.getId(), fName.getText(), lName.getText());
                    teacherList.setListData(toArr(teachers));
                    fName.setText("");
                    lName.setText("");
                    deselect.setVisible(false);
                    delete.setVisible(false);
                }
            }
        });

        clear.addActionListener(e -> {
            fName.setText("");
            lName.setText("");
        });

        deselect.addActionListener(e -> {
            teacherList.clearSelection();
            fName.setText("");
            lName.setText("");
            deselect.setVisible(false);
            delete.setVisible(false);
        });

        delete.addActionListener(e -> {
            Teacher teacher = teacherList.getSelectedValue();
            teacher.updateTeacher(teacher.getId(), "-", "");
            teachers.remove(teacher);
            teacherList.setListData(toArr(teachers));
            fName.setText("");
            lName.setText("");
            deselect.setVisible(false);
            delete.setVisible(false);
        });

        teacherList.addListSelectionListener(e -> {
            Teacher teacher = teacherList.getSelectedValue();
            if (teacher != null) {
                loadData(teacherList.getSelectedValue().id);
                deselect.setVisible(true);
                delete.setVisible(true);
                fName.setText(teacher.getFn());
                lName.setText(teacher.getLn());
                String[] rowData = {"1", "2"};
                tableModel.addRow(rowData);
            }
        });



        table.setVisible(true);


    }
    public static Teacher[] toArr(ArrayList<Teacher> list){
        Teacher[] array = new Teacher[list.size()];
        for (int i = 0; i < list.size(); i++){
            array[i] = list.get(i);
        }
        return array;
    }

    public void getNames(){
        teachers.clear();
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_manager","root","password");
            Statement s =  con.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM teacher;");
            while(rs!=null&&rs.next())
            {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                int id = rs.getInt("id");
                Teacher existingTeacher = new Teacher(id, firstName, lastName);//pass in an id to not create a new teacher but just get one instead
                teachers.add(existingTeacher);
            }
            teacherList.setListData(toArr(teachers));
            con.close();
        }catch(Exception e)
        {

        }
    }

    public void loadData(/*DefaultTableModel table,*/ int teacherID){
        int sectionID = -1;
        int courseID = -1;
        String courseName = null;

        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_manager","root","password");
            Statement s =  con.createStatement();

            ResultSet rs = s.executeQuery("SELECT * FROM section;");
            while(rs!=null && rs.next())
            {
                if(rs.getInt("teacher_id") == teacherID)
                {
                    sectionID = rs.getInt("section_id");
                    courseID = rs.getInt("course_id");
                }
            }
            rs = s.executeQuery("SELECT * FROM course;");
            while(rs!=null && rs.next())
            {
                if(rs.getInt("section_id") == sectionID)
                {
                    courseName = rs.getString("title");
                }
            }

            //use sectionID, course title
            con.close();

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
