import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class sectionPanel extends JPanel {
    Frame parent = null;
    public sectionPanel(Frame parent){
        this.parent = parent;
        this.setLayout(null);
        this.setBounds(0,25,800,437);
        ArrayList<Section> sections = new ArrayList<>();
        JList<Section> jList = new JList<>();
        jList.setListData(toArr(sections));
        JScrollPane sectionsPane = new JScrollPane(jList);
        sectionsPane.setBounds(10, 140, 280, 175);
        this.add(sectionsPane);

        //Makes the Table Model Variables
        String[] columnTitles = {"Student Last", "Student First", "Student ID"};
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
        scrollPane.setBounds(300, 15, 250, 300);
        scrollPane.setViewportView(table);
        this.add(scrollPane);

        JLabel txt1 = new JLabel("Teachers:");
        JLabel txt2 = new JLabel("Courses:");
        JLabel txt3 = new JLabel("Available Sections:");
        JLabel txt4 = new JLabel("Students:");
        JComboBox<Teacher> teachers = new JComboBox<>();
        JComboBox<Course> courses = new JComboBox<>();

        txt1.setBounds(120, 10, 100, 25);
        txt2.setBounds(120, 60, 100, 25);
        txt3.setBounds(100, 110, 200, 25);
        teachers.setBounds(50, 40, 200, 17);
        courses.setBounds(50, 90, 200, 17);

        this.add(txt1);
        this.add(txt2);
        this.add(txt3);
        this.add(teachers);
        this.add(courses);


    }
    public static Section[] toArr(ArrayList<Section> list){
        Section[] array = new Section[list.size()];
        for (int i = 0; i < list.size(); i++){
            array[i] = list.get(i);
        }
        return array;
    }
    public int getTeacherSectionId(int teacher_id)
    {
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_manager","root","password");
            Statement s =  con.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM section;");
            while(rs!=null && rs.next())
            {
                if(rs.getInt("teacher_id") == teacher_id)
                {
                    return rs.getInt("id");
                }
            }
            con.close();

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    public int getTeacherCourseId(int teacher_id)
    {
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_manager","root","password");
            Statement s =  con.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM section;");
            while(rs!=null && rs.next())
            {
                if(rs.getInt("teacher_id") == teacher_id)
                {
                    return rs.getInt("course_id");
                }
            }
            con.close();

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return -1;
    }

}
