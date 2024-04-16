import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicInteger;

public class coursePanel extends JPanel {
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

        aca.setActionCommand("Academic");
        ap.setActionCommand("AP");
        kap.setActionCommand("KAP");

        this.add(aca);
        this.add(ap);
        this.add(kap);
        String[] test = {"12344234", "2", "AP"};
        tableModel.addRow(test);

        //All items have been layed out on the panel


        //This is going to be replaced by the SQL id generation
        AtomicInteger temporaryID = new AtomicInteger(1);


        save.addActionListener(e-> {
            if (idName.getText().isEmpty()){
                if (!fName.getText().isEmpty() && group.getSelection() != null){
                    String[] newEntry = { "" + temporaryID, fName.getText(), group.getSelection().getActionCommand()};
                    tableModel.addRow(newEntry);
                    temporaryID.set(temporaryID.get() + 1);
                }
            }
            else{
                int row = table.getSelectedRow();
                table.setValueAt(fName.getText(), row, 1);
                table.setValueAt(group.getSelection().getActionCommand(), row, 2);
            }

        });

        delete.addActionListener(e-> {
            int row = table.getSelectedRow();
            tableModel.removeRow(row);
            fName.setText("");
            group.clearSelection();
            idName.setText("");
            table.clearSelection();
        });

        deselect.addActionListener(e-> {
            fName.setText("");
            group.clearSelection();
            idName.setText("");
            table.clearSelection();
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

    public String getCourseName(int course_id)
    {
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_manager","root","password");
            Statement s =  con.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM course;");
            while(rs!=null && rs.next())
            {
                if(rs.getInt("id") == course_id)
                {
                    return rs.getString("title");
                }
            }
            con.close();

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return "null";
    }

}
