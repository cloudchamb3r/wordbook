package wordbook.frame;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import wordbook.dto.WordDto;

public class WordHistoryTable extends JScrollPane {
    DefaultTableModel historyTableModel;
    JTable historyTable;

    public WordHistoryTable() {
        historyTableModel = new DefaultTableModel(new String[] { "암기", "단어", "뜻" }, 0);
        historyTable = new JTable();

        historyTable.setModel(historyTableModel);

        setViewportView(historyTable);
    }

    public void add(WordDto dto) {
        if (dto == null)
            return;
        historyTableModel.addRow(new Object[] { dto.getMemorized(), dto.getWord(), dto.getMean() });
        scrolldown();
    }

    public void clear() {
        historyTableModel.setRowCount(0);
    }

    public void scrolldown() {
        historyTable.changeSelection(historyTable.getRowCount() - 1, 0, false, false);
    }
}
