import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Style;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class coursePanel extends JPanel {
    ArrayList<Course> courses = new ArrayList<>();
    Frame parent = null;
    public coursePanel(Frame parent){
        this.parent = parent;
        this.setLayout(null);
        this.setBounds(0,25,800,437);
        //Makes the Table Model Variables
        String[] columnTitles = {"Course ID", "Course Name", "Type"};
        String[][] data = {};
        DefaultTableModel tableModel = new DefaultTableModel(data, columnTitles){
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        //Makes the Jtable and sets the basic functions
        getNames();


        JTable table = new JTable(tableModel);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.setFocusable(false);
        //Adds a scroll panel to the tabel
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 10, 380, 275);
        scrollPane.setViewportView(table);
        this.add(scrollPane);

        JLabel ID = new JLabel("ID:");
        JLabel txt1 = new JLabel("Course Name:");
        JLabel txt2 = new JLabel(" Course Type");
        JTextField idName = new JTextField();
        JTextField fName = new JTextField();
        JButton save = new JButton();
        JButton clear = new JButton();
        JButton deselect = new JButton();
        JButton delete = new JButton();

        txt1.setBounds(450, 60, 100, 25);
        txt2.setBounds(515, 100, 100, 25);
        ID.setBounds(450, 20, 100, 25);


        fName.setBounds(550, 62, 100, 17);
        idName.setBounds(550, 22, 100, 17);
        deselect.setBounds(450, 260, 200, 25);
        save.setBounds(450, 170, 200, 25);
        clear.setBounds(450, 200, 200, 25);
        delete.setBounds(450, 230, 200, 25);

        idName.setFocusable(false);

        this.add(txt1);
        this.add(txt2);
        this.add(ID);
        this.add(idName);
        this.add(fName);
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

        ButtonGroup group = new ButtonGroup();

        JRadioButton aca = new JRadioButton("Academic");
        JRadioButton ap = new JRadioButton("AP");
        JRadioButton kap = new JRadioButton("KAP");

        aca.setBounds(450, 130, 90, 25);
        ap.setBounds(540, 130, 50, 25);
        kap.setBounds(590, 130, 100, 25);

        aca.setFocusable(false);
        ap.setFocusable(false);
        kap.setFocusable(false);
        group.add(aca);
        group.add(ap);
        group.add(kap);

        aca.setActionCommand("0");
        ap.setActionCommand("1");
        kap.setActionCommand("2");

        this.add(aca);
        this.add(ap);
        this.add(kap);
        //All items have been layed out on the panel
        //Loading all the names into the jtable
        for (Course a: courses){
            String id = "" + a.getId();
            String ldiff = "" + a.getDiff();
            String name = a.getTitle();
            String diff = "";
            switch (ldiff){
                case "0" -> {
                    diff = "Academic";
                }
                case "1" -> {
                    diff = "AP";
                }
                case "2" -> {
                    diff = "KAP";
                }
            }
            String[] entry = {id, name, diff};
            tableModel.addRow(entry);
        }

        save.addActionListener(e-> {
            if (idName.getText().isEmpty()){
                if (!fName.getText().isEmpty() && group.getSelection() != null){
                    String difficulty = group.getSelection().getActionCommand();
                    String diff = "";
                    int entrySet = 0;
                    switch (difficulty){
                        case "0" -> {
                            diff = "Academic";
                            entrySet = 0;
                        }
                        case "1" -> {
                            diff = "AP";
                            entrySet = 1;
                        }
                        case "2" -> {
                            diff = "KAP";
                            entrySet = 2;
                        }

                    }
                    System.out.println(entrySet);
                    Course newEntry1 = new Course(fName.getText(), entrySet);

                    String[] newEntry = { Integer.toString(newEntry1.getId()), fName.getText(), diff};
                    courses.add(newEntry1);
                    tableModel.addRow(newEntry);
                }
            }
            else{
                int row = table.getSelectedRow();
                Course entry = courses.get(row-1);
                String difEntry = group.getSelection().getActionCommand();
                int entryD = -1;
                String tableD = "";
                switch (difEntry){
                    case "0" -> {
                        entryD = 0;
                        tableD = "Academic";
                    }
                    case "1" -> {
                        entryD = 1;
                        tableD = "AP";
                    }
                    case "2" -> {
                        entryD = 2;
                        tableD = "KAP";
                    }
                }
                entry.updateCourse(entry.getId(), fName.getText(), entryD);
                table.setValueAt(fName.getText(), row, 1);
                table.setValueAt(tableD, row, 2);
            }

        });

        delete.addActionListener(e-> {
            int row = table.getSelectedRow();
            System.out.println("Row = " + row + " tableModelSize = " + tableModel.getRowCount() + " courseSize = " + courses.size());

            Course entry = courses.get(row);
            entry.updateCourse(entry.getId(), "-", entry.getDiff());
            tableModel.removeRow(row);
            courses.remove(entry);
            fName.setText("");
            group.clearSelection();
            idName.setText("");
            table.clearSelection();
            deselect.setVisible(false);
            delete.setVisible(false);
            System.out.println("tableModelSize = " + tableModel.getRowCount());
        });

        deselect.addActionListener(e-> {
            fName.setText("");
            group.clearSelection();
            idName.setText("");
            table.clearSelection();
            delete.setVisible(false);
            deselect.setVisible(false);

        });

        clear.addActionListener(e-> {
            fName.setText("");
            group.clearSelection();
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                idName.setText((String) table.getValueAt(row, 0));
                fName.setText((String) table.getValueAt(row, 1));
                String dif = (String) table.getValueAt(row, 2);
                switch (dif) {
                    case "AP" -> {
                        ap.setSelected(true);
                    }
                    case "KAP" -> {
                        kap.setSelected(true);

                    }
                    case "Academic" -> {
                        aca.setSelected(true);

                    }
                }
                deselect.setVisible(true);
                delete.setVisible(true);
            }
        });
        this.setVisible(true);
    }


    public ArrayList<Integer> loadSectionData(/*DefaultTableModel table,*/ int teacherID, int courseID){
        ArrayList<Integer> sectionID = new ArrayList<>();

        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_manager","root","password");
            Statement s =  con.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM section;");
            while(rs!=null && rs.next())
            {
                if(rs.getInt("teacher_id") == teacherID && rs.getInt("course_id") == courseID)
                {
                    //looking for section id
                    sectionID.add(rs.getInt("section_id"));
                }
            }
            //use sectionID and students
            con.close();

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return sectionID;
    }


    public ArrayList<Student> loadStudentData(/*DefaultTableModel table,*/ int sectionID){
        ArrayList<Student> students = new ArrayList<>();
        ArrayList<Integer> studentID = new ArrayList<>();

        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_manager","root","password");
            Statement s =  con.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM enrollment;");
            //goes through enrollment and sees if section ID matches then pull student id called "id"
            while(rs!=null && rs.next())
            {
                if(rs.getInt("section_id") == sectionID)
                {
                    studentID.add(rs.getInt("id"));
                }
            }
            //use sectionID and students
            con.close();

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        ArrayList<Student> h = parent.getStup().students;
        for(Integer a: studentID)
        {
            for(Student j: h )
            {
                if(j.getId() == a)//if id in list matches student id
                {
                    students.add(j);
                }
            }
        }

        return h;
    }


    public void getNames(){
        courses.clear();
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_manager","root","password");
            Statement s =  con.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM course;");
            while(rs!=null&&rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                int diff = rs.getInt("diff");
                Course existingCourse = new Course(id, title, diff);//pass in an id to not create a new student but just get one instead
                courses.add(existingCourse);
            }
            con.close();
        }catch(Exception e)
        {

        }
    }


}
