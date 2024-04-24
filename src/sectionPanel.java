import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class sectionPanel extends JPanel {
    ArrayList<Section> sections = new ArrayList<>();
    JComboBox<Teacher> teachers = new JComboBox<>();
    JComboBox<Course> courses = new JComboBox<>();
    Frame parent = null;
    public sectionPanel(Frame parent){
        this.parent = parent;
        getNames();
        this.setLayout(null);
        this.setBounds(0,25,800,437);
        JList<Section> jList = new JList<>();
        jList.setListData(toArr(sections));
        JScrollPane sectionsPane = new JScrollPane(jList);
        sectionsPane.setBounds(10, 140, 280, 135);
        this.add(sectionsPane);

        ArrayList<Student> students = parent.sp.students;

        JList<Student> studentJList = new JList<>(toArr2(students));
        JScrollPane studentsPane = new JScrollPane(studentJList);
        studentsPane.setBounds(560, 34, 230, 175);
        this.add(studentsPane);


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
        scrollPane.setBounds(300, 35, 250, 280);
        scrollPane.setViewportView(table);
        this.add(scrollPane);

        JLabel txt1 = new JLabel("Teachers:");
        JLabel txt2 = new JLabel("Courses:");
        JLabel txt3 = new JLabel("Available Sections:");
        JLabel txt4 = new JLabel("Students:");
        JLabel txt5 = new JLabel("Roster:");

        txt1.setBounds(120, 10, 100, 25);
        txt4.setBounds(650, 10, 100, 25);
        txt5.setBounds(400, 10, 100, 25);
        txt2.setBounds(120, 60, 100, 25);
        txt3.setBounds(100, 110, 200, 25);
        teachers.setBounds(50, 40, 200, 17);
        courses.setBounds(50, 90, 200, 17);

        this.add(txt1);
        this.add(txt2);
        this.add(txt3);
        this.add(txt4);
        this.add(txt5);
        this.add(teachers);
        this.add(courses);

        JButton add = new JButton("Add");
        JButton remove = new JButton("Remove");
        JButton deselect = new JButton("Deselect");

        deselect.setBounds(10, 285, 280, 28);
        add.setBounds(560, 220, 110, 94);
        remove.setBounds(679, 220, 110, 94);

        add.setFocusable(false);
        remove.setFocusable(false);
        deselect.setFocusable(false);

        this.add(add);
        this.add(remove);
        this.add(deselect);

        //everything is layed out

        add.addActionListener(e -> {
            if (jList.getSelectedValue() == null){
                if (teachers.getSelectedItem() != null && courses.getSelectedItem() != null && studentJList.getSelectedValue() != null){
                    Course course = (Course) courses.getSelectedItem();
                    Teacher teacher = (Teacher) teachers.getSelectedItem();
                    Student student = studentJList.getSelectedValue();
                    Section newSection = new Section(course.getId(), teacher.getId());
                    sections.add(newSection);
                    getNames();
                    jList.setListData(toArr(sections));
                    Enrollment enrollment = new Enrollment(newSection.getId(), student.getId());
                    String[] entry = {student.getLn(), student.getFn(), "" +student.getId()};
                    tableModel.addRow(entry);
                }
            }
            else{

            }

        });

        remove.addActionListener(e -> {

        });

        teachers.addItemListener(e -> {

        });

        courses.addItemListener(e -> {

        });

        jList.addListSelectionListener(e -> {
            for (int i = tableModel.getRowCount(); i > 0; i--){
                tableModel.removeRow(i-1);
            }
            if (!e.getValueIsAdjusting()){
                if (jList.getSelectedValue() != null) {
                    Section section = jList.getSelectedValue();
                    int sectionId = section.getId();
                }
            }
        });

        deselect.addActionListener(e -> {
            if (jList.getSelectedValue() != null){
                jList.clearSelection();

            }
        });
    }

    public void getNames(){
        sections.clear();
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_manager","root","password");
            Statement s =  con.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM section;");
            while(rs!=null&&rs.next())
            {
                int id = rs.getInt("id");
                int course_id = rs.getInt("course_id");
                int teacher_id = rs.getInt("teacher_id");
                Section existingSection = new Section(id, course_id, teacher_id);//pass in an id to not create a new student but just get one instead
                sections.add(existingSection);
            }
            con.close();
        }catch(Exception e)
        {

        }
    }

    public static Section[] toArr(ArrayList<Section> list){
        Section[] array = new Section[list.size()];
        for (int i = 0; i < list.size(); i++){
            array[i] = list.get(i);
        }
        return array;
    }

    public static Student[] toArr2(ArrayList<Student> list){
        Student[] array = new Student[list.size()];
        for (int i = 0; i < list.size(); i++){
            array[i] = list.get(i);
        }
        return array;
    }

    public void update()
    {
        ArrayList<Teacher> teachersArraylist = parent.tp.teachers;
        ArrayList<Course> courseArraylist = parent.cp.courses;
        teachers.removeAllItems();
        courses.removeAllItems();

        for (Teacher a: teachersArraylist){
            teachers.addItem(a);
        }
        for (Course a: courseArraylist){
            courses.addItem(a);
        }
    }


}
