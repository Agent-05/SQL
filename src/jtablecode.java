import javax.swing.*;

public class jtablecode {
    public jtablecode(){
        String[] columnTitles = {"X", "Y", "Z"};
        String[][] data = {};
        JTable table = new JTable(data, columnTitles);
        table.getTableHeader().setReorderingAllowed(false);

    }
}
