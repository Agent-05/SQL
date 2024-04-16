import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.*;

public class studentPanel extends JPanel {
    ArrayList<Student> students = new ArrayList<Student>();
    Frame parent = null;
    JList<Student> studentList = new JList<Student>();
    public studentPanel(Frame parent){
        this.parent = parent;
        this.setLayout(null);
        this.setBounds(0,25,800,437);
        getNames();
        studentList.setListData(toArr(students));
        JScrollPane studentFrame = new JScrollPane(studentList);
        studentFrame.setBounds(10, 10, 280, 275);
        this.add(studentFrame);

        JLabel ID = new JLabel("ID:");
        JLabel txt1 = new JLabel("First Name:");
        JLabel txt2 = new JLabel("Last Name:");
        JTextField idName = new JTextField();
        JTextField fName = new JTextField();
        JTextField lName = new JTextField();
        JButton save = new JButton();
        JButton clear = new JButton();
        JButton deselect = new JButton();
        JButton delete = new JButton();

        //Makes the Table Model Variables
        String[] columnTitles = {"Section ID", "Course Title", "Teacher ID"};
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






        save.addActionListener(e -> {
            if (!fName.getText().isEmpty() || !lName.getText().isEmpty()) {
                if (studentList.isSelectionEmpty()) {
                        Student student = new Student(fName.getText(),lName.getText());
                        students.add(student);
                        getNames();
                    }
                } else {
                    Student student = studentList.getSelectedValue();
                    student.updateStudent(student.getId(), fName.getText(), lName.getText());
            }
            studentList.setListData(toArr(students));
            fName.setText("");
            lName.setText("");
            deselect.setVisible(false);
            delete.setVisible(false);
        });

        clear.addActionListener(e -> {
            fName.setText("");
            lName.setText("");
        });

        deselect.addActionListener(e -> {
            studentList.clearSelection();
            fName.setText("");
            lName.setText("");
            deselect.setVisible(false);
            delete.setVisible(false);
        });

        delete.addActionListener(e -> {
            Student student = studentList.getSelectedValue();
            student.updateStudent(student.getId(), "-", "");
            students.remove(student);
            studentList.setListData(toArr(students));
            fName.setText("");
            lName.setText("");
            deselect.setVisible(false);
            delete.setVisible(false);
        });

        studentList.addListSelectionListener(e -> {
            Student student = studentList.getSelectedValue();
            if (student != null) {
                deselect.setVisible(true);
                delete.setVisible(true);
                fName.setText(student.getFn());
                lName.setText(student.getLn());
                String[] rowData = {"1", "2", "3"};
                tableModel.addRow(rowData);
            }
        });


    }
    public static Student[] toArr(ArrayList<Student> list){
        Student[] array = new Student[list.size()];
        for (int i = 0; i < list.size(); i++){
            array[i] = list.get(i);
        }
        return array;
    }

    public void getNames(){
        students.clear();
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_manager","root","password");
            Statement s =  con.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM student;");
            while(rs!=null&&rs.next())
            {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                int id = rs.getInt("id");
                Student existingStudent = new Student(id, firstName, lastName);//pass in an id to not create a new student but just get one instead
                students.add(existingStudent);
            }
            studentList.setListData(toArr(students));
            con.close();
        }catch(Exception e)
        {

        }
    }
    public void loadData(DefaultTableModel table){
        //I need you to pull every each course id and section id belonging to the student
        //Ill then put the data into a row. If you could make it into a loop, itd be perfect

    }

}
