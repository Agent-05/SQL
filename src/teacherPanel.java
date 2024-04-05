import javax.swing.*;
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
    public teacherPanel(){
        this.setLayout(null);
        this.setBounds(0,25,784,437);
        getNames();
        teacherList.setListData(toArr(teachers));
        JScrollPane teacherFrame = new JScrollPane(teacherList);
        teacherFrame.setBounds(10, 10, 280, 275);
        this.add(teacherFrame);


        JLabel txt1 = new JLabel("First Name:");
        JLabel txt2 = new JLabel("Last Name:");
        JLabel txt3 = new JLabel("Phone Number:");
        JLabel txt4 = new JLabel("Address:");
        JTextField fName = new JTextField();
        JTextField lName = new JTextField();
        JTextField phoneNumber = new JTextField();
        JTextField address = new JTextField();
        JButton save = new JButton();
        JButton clear = new JButton();
        JButton deselect = new JButton();
        JButton delete = new JButton();




        txt1.setBounds(300, 20, 250, 25);
        txt2.setBounds(300, 60, 250, 25);
        txt3.setBounds(300, 100, 250, 25);
        txt4.setBounds(300, 140, 250, 25);


        fName.setBounds(400, 22, 100, 17);
        lName.setBounds(400, 62, 100, 17);
        phoneNumber.setBounds(400, 102, 100, 17);
        address.setBounds(400, 142, 100, 17);
        deselect.setBounds(300, 260, 200, 25);
        save.setBounds(300, 170, 200, 25);
        clear.setBounds(300, 230, 200, 25);
        delete.setBounds(300, 200, 200, 25);

        this.add(txt1);
        this.add(txt2);
        this.add(txt3);
        this.add(txt4);
        this.add(fName);
        this.add(lName);
        this.add(phoneNumber);
        this.add(address);
        this.add(save);
        this.add(clear);
        this.add(deselect);
        this.add(delete);

        deselect.setText("Deselect");
        save.setText("Save");
        clear.setText("Clear");
        delete.setText("Delete");





        save.addActionListener(e -> {
            if (teacherList.isSelectionEmpty()) {
                if (!fName.getText().isEmpty() || !lName.getText().isEmpty()) {
                    Teacher teacher = new Teacher(fName.getText(),lName.getText());
                    teachers.add(teacher);
                    teacherList.setListData(toArr(teachers));
                }
            } else {
                Teacher teacher = teacherList.getSelectedValue();
                teacher.updateTeacher(teacher.getId(), fName.getText(), lName.getText());
                teacherList.setListData(toArr(teachers));
                fName.setText("");
                lName.setText("");
                phoneNumber.setText("");
                address.setText("");
                deselect.setVisible(false);
                delete.setVisible(false);
            }
        });

        clear.addActionListener(e -> {
            fName.setText("");
            lName.setText("");
            phoneNumber.setText("");
            address.setText("");
        });

        deselect.addActionListener(e -> {
            teacherList.clearSelection();
            fName.setText("");
            lName.setText("");
            phoneNumber.setText("");
            address.setText("");
            deselect.setVisible(false);
            delete.setVisible(false);
        });

        delete.addActionListener(e -> {
            Teacher teacher = teacherList.getSelectedValue();
            teachers.remove(teacher);
            teacherList.setListData(toArr(teachers));
            fName.setText("");
            lName.setText("");
            phoneNumber.setText("");
            address.setText("");
            deselect.setVisible(false);
            delete.setVisible(false);
        });

        teacherList.addListSelectionListener(e -> {
            Teacher teacher = teacherList.getSelectedValue();
            if (teacher != null) {
                deselect.setVisible(true);
                delete.setVisible(true);
//                fName.setText(teacher.getfName());
//                lName.setText(teacher.getlName());
//                address.setText(teacher.getAddress());
//                phoneNumber.setText(teacher.getPhoneNumber());
            }
        });
        

    }
    public static Teacher[] toArr(ArrayList<Teacher> list){
        Teacher[] array = new Teacher[list.size()];
        for (int i = 0; i < list.size(); i++){
            array[i] = list.get(i);
        }
        return array;
    }

    public void getNames(){
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
                System.out.println(teachers.get(0));
            }
            teacherList.setListData(toArr(teachers));
            con.close();
        }catch(Exception e)
        {

        }
    }

}
