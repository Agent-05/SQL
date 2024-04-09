import javax.swing.*;
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
    JList<Student> studentList = new JList<Student>();
    public studentPanel(){
        this.setLayout(null);
        this.setBounds(0,25,784,437);
        getNames();
        studentList.setListData(toArr(students));
        JScrollPane studentFrame = new JScrollPane(studentList);
        studentFrame.setBounds(10, 10, 280, 275);
        this.add(studentFrame);


        JLabel txt1 = new JLabel("First Name:");
        JLabel txt2 = new JLabel("Last Name:");
        JTextField fName = new JTextField();
        JTextField lName = new JTextField();
        JButton save = new JButton();
        JButton clear = new JButton();
        JButton deselect = new JButton();
        JButton delete = new JButton();




        txt1.setBounds(300, 20, 250, 25);
        txt2.setBounds(300, 60, 250, 25);


        fName.setBounds(400, 22, 100, 17);
        lName.setBounds(400, 62, 100, 17);
        deselect.setBounds(300, 260, 200, 25);
        save.setBounds(300, 170, 200, 25);
        clear.setBounds(300, 230, 200, 25);
        delete.setBounds(300, 200, 200, 25);

        this.add(txt1);
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





        save.addActionListener(e -> {
            if (studentList.isSelectionEmpty()) {
                if (!fName.getText().isEmpty() || !lName.getText().isEmpty()) {
                    Student student = new Student(fName.getText(),lName.getText());
                    students.add(student);
                    studentList.setListData(toArr(students));
                }
            } else {
                Student student = studentList.getSelectedValue();
                student.updateStudent(student.getId(), fName.getText(), lName.getText());
                studentList.setListData(toArr(students));
                fName.setText("");
                lName.setText("");
                deselect.setVisible(false);
                delete.setVisible(false);
            }
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

}
