package wordbook.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import wordbook.dto.WordDto;
import wordbook.font.NanumGothicFont;
import wordbook.view.component.UnmodifiableTableModel;

public class WordHistoryTable extends JScrollPane {
    private final DefaultTableModel tableModel;
    private final List<WordDto> tableItem;
    private final JTable table;

    public WordHistoryTable() {
        tableModel = new UnmodifiableTableModel(new String[] { "암기", "단어", "뜻" }, 0);
        tableItem = new ArrayList<>();
        table = new JTable();

        table.setModel(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFont(NanumGothicFont.getFont().deriveFont(12f));

        setViewportView(table);
    }

    public void add(WordDto dto) {
        if (dto == null)
            return;
        tableItem.add(dto);
        tableModel.addRow(new Object[] { dto.getMemorized(), dto.getWord(), dto.getMean() });
        scrolldown();
    }

    public void removeFront() {
        if (tableItem.isEmpty())
            return;
        tableItem.remove(0);
        tableModel.removeRow(0);
    }

    public void clear() {
        tableItem.clear();
        tableModel.setRowCount(0);
    }

    public void scrolldown() {
        table.changeSelection(table.getRowCount() - 1, 0, false, false);
    }
}
