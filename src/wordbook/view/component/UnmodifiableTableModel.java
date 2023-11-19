package wordbook.view.component;

import javax.swing.table.DefaultTableModel;

public class UnmodifiableTableModel extends DefaultTableModel {
    public UnmodifiableTableModel(String[] columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
