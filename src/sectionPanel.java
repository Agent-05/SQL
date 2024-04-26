import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class sectionPanel extends JPanel {
    ArrayList<Section> sections = new ArrayList<>();
    ArrayList<Enrollment> students1 = new ArrayList<>();
    JComboBox<Teacher> teachers = new JComboBox<>();
    JComboBox<Course> courses = new JComboBox<>();
    int teacherID = -1;
    int courseID = -1;

    JList<Section> jList = new JList<>();
    JScrollPane sectionsPane = new JScrollPane(jList);
    Frame parent = null;
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
    public sectionPanel(Frame parent){
        this.parent = parent;
        getNames();
        this.setLayout(null);
        this.setBounds(0,25,800,437);
        sectionsPane.setBounds(10, 140, 280, 135);
        this.add(sectionsPane);

        ArrayList<Student> students = parent.sp.students;

        JList<Student> studentJList = new JList<>(toArr2(students));
        JScrollPane studentsPane = new JScrollPane(studentJList);
        studentsPane.setBounds(560, 34, 230, 175);
        this.add(studentsPane);


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

        JButton add = new JButton("Add Student");
        JButton remove = new JButton("Remove Student");
        JButton deselect = new JButton("Deselect");

        JButton addSection = new JButton("Add Section");
        JButton removeSection = new JButton("Remove Section");

        deselect.setBounds(10, 285, 280, 28);
        add.setBounds(560, 220, 110, 40);
        remove.setBounds(679, 220, 110, 40);
        addSection.setBounds(560, 260, 110, 40);
        removeSection.setBounds(679, 260, 110, 40);

        add.setFocusable(false);
        remove.setFocusable(false);
        deselect.setFocusable(false);
        addSection.setFocusable(false);
        removeSection.setFocusable(false);

        this.add(add);
        this.add(remove);
        this.add(deselect);
        this.add(addSection);
        this.add(removeSection);
        //everything is layed out

        addSection.addActionListener(e -> {
                if (teachers.getSelectedItem() != null && courses.getSelectedItem() != null){
                    Course course = (Course) courses.getSelectedItem();
                    Teacher teacher = (Teacher) teachers.getSelectedItem();
                    Section newSection = new Section(course.getId(), teacher.getId());
                    sections.add(newSection);
                    getNames();
                    updateSections(teacherID, courseID);
                }
            else{

            }

        });

        removeSection.addActionListener(e -> {
            if (jList.getSelectedValue() != null){
                jList.getSelectedValue().updateSection(jList.getSelectedValue().getId(), -1, jList.getSelectedValue().getTeacherId());
                getNames();
                updateSections(teacherID, courseID);
            }
            else{

            }

        });

        add.addActionListener(e -> {
            if (jList.getSelectedValue() != null){
                if (teachers.getSelectedItem() != null && courses.getSelectedItem() != null && studentJList.getSelectedValue() != null){
                    Student student = studentJList.getSelectedValue();
                    Section sectionToAdd = jList.getSelectedValue();
                    getNames();
                    Enrollment enrollment = new Enrollment(sectionToAdd.getId(), student.getId());
                    updateTable(sectionToAdd.id);
                    updateSections(teacherID, courseID);
                }
            }
            else{
            }

        });

        remove.addActionListener(e -> {
            int row = table.getSelectedRow();
            Enrollment entry = students1.get(row);
            entry.updateEnrollment(entry.getId(), -1);
            updateSections(teacherID, courseID);
            tableModel.removeRow(row-1);
            students1.remove(entry);
            table.clearSelection();
            deselect.setVisible(false);
        });
        teachers.addItemListener(e -> {
            if(teachers.getSelectedItem() != null)
            {
                Teacher a = (Teacher)(teachers.getSelectedItem());
                teacherID = a.getId();
                updateSections(teacherID, courseID);
            }
        });

        courses.addItemListener(e -> {
            if(courses.getSelectedItem() != null)
            {
                Course a = (Course)(courses.getSelectedItem());
                courseID = a.getId();
                updateSections(teacherID, courseID);
            }
        });

        jList.addListSelectionListener(e -> {
            for (int i = tableModel.getRowCount(); i > 0; i--){
                tableModel.removeRow(i-1);
            }
            if (!e.getValueIsAdjusting()){
                if (jList.getSelectedValue() != null) {
                    Section section = jList.getSelectedValue();
                    int sectionId = section.getId();
                    updateTable(sectionId);
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

    public void updateTable(int sectionID)
    {
        ArrayList<Integer> g = new ArrayList<>();
        tableModel.setNumRows(0);
        students1.clear();
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_manager","root","password");
            Statement s =  con.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM enrollment;");
            while(rs!=null && rs.next())
            {
                if(rs.getInt("id") == sectionID)
                {
                    g.add(rs.getInt("student_id"));
                }
            }

            rs = s.executeQuery("SELECT * FROM student;");
            while(rs!=null && rs.next())
            {
                for(Integer a: g)
                {
                    if(rs.getInt("id") == a)
                    {
                        students1.add(new Enrollment(sectionID, a));
                        //looking for section id
                        String[] entry = {rs.getString("first_name"), "" + rs.getString("last_name"), "" + rs.getInt("id")};
                        //String[] entry = {"" + rs.getInt("student_id"), "" + courseName};
                        tableModel.addRow(entry);
                    }
                }
            }
            //use sectionID and students
            con.close();

        }catch(Exception e)
        {

        }
    }

    ArrayList<Section> toDisplay = new ArrayList<>();
    public void updateSections(int teacherID, int courseID){
        toDisplay.clear();
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_manager","root","password");
            Statement s =  con.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM section;");
            while(rs!=null && rs.next())
            {
                System.out.println("Needed: " + rs.getInt("teacher_id"));
                System.out.println("Needed Course: " + rs.getInt("course_id"));
                System.out.println("Got: " + teacherID);
                System.out.println("Got Course: " + courseID + "\n");
                if(rs.getInt("teacher_id") == teacherID && rs.getInt("course_id") == courseID)
                {
                    toDisplay.add(new Section(rs.getInt("id"), rs.getInt("teacher_id"), rs.getInt("course_id")));
                }
            }
            jList.setListData(toArr(toDisplay));

            con.close();

        }catch(Exception e)
        {

        }
    }
}
