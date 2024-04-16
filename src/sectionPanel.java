import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class sectionPanel extends JPanel {
    public sectionPanel(){
        this.setLayout(null);
        this.setBounds(0,25,800,437);
        ArrayList<Section> sections = new ArrayList<>();
        JList<Section> jList = new JList<>();
        jList.setListData(toArr(sections));
        JScrollPane sectionsPane = new JScrollPane(jList);
        sectionsPane.setBounds(10, 10, 280, 275);
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
        scrollPane.setBounds(510, 10, 280, 275);
        scrollPane.setViewportView(table);
        this.add(scrollPane);

        JLabel txt1 = new JLabel("Teachers:");
        JLabel txt2 = new JLabel("Courses:");
        JComboBox<Teacher> teachers = new JComboBox<>();
        JComboBox<Course> courses = new JComboBox<>();
        JButton save = new JButton();
        JButton clear = new JButton();
        JButton deselect = new JButton();
        JButton delete = new JButton();

        txt1.setBounds(370, 10, 100, 25);
        txt2.setBounds(370, 60, 100, 25);
        teachers.setBounds(350, 40, 100, 17);
        courses.setBounds(350, 90, 100, 17);
        deselect.setBounds(300, 260, 200, 25);
        save.setBounds(300, 170, 200, 25);
        clear.setBounds(300, 200, 200, 25);
        delete.setBounds(300, 230, 200, 25);
        this.add(txt1);
        this.add(txt2);
        this.add(teachers);
        this.add(courses);
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



    }
    public static Section[] toArr(ArrayList<Section> list){
        Section[] array = new Section[list.size()];
        for (int i = 0; i < list.size(); i++){
            array[i] = list.get(i);
        }
        return array;
    }
}
