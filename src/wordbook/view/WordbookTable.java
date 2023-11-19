package wordbook.view;

import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import wordbook.dao.WordbookDao;
import wordbook.dto.WordbookDto;
import wordbook.font.NanumGothicFont;
import wordbook.inserter.TxtInserter;
import wordbook.interfaces.Notifiable;
import wordbook.view.component.UnmodifiableTableModel;

public class WordbookTable extends JScrollPane {
    private final DefaultTableModel tableModel;
    private final List<WordbookDto> tableItem;
    private final JTable table;

    private WordbookDto currentWord;
    private WordbookDto lastFocusedWord;

    private final Notifiable notifiable;

    public WordbookTable(Notifiable notifiable) {
        this.notifiable = notifiable;
        tableModel = new UnmodifiableTableModel(new String[] { "단어장" }, 0);
        tableItem = new ArrayList<>();
        table = new JTable();

        table.setModel(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFont(NanumGothicFont.getFont().deriveFont(12f));

        table.addMouseListener(mouseEventHandler);
        table.setDropTarget(dragDropHandler);

        table.setComponentPopupMenu(createPopupMenu());

        setViewportView(table);
        refresh();
    }

    public WordbookTable(Notifiable notifiable, int preferredWidth, int preferredHeight) {
        this(notifiable);
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));
    }

    public WordbookDto getCurrentWordBook() {
        return currentWord;
    }

    public void clear() {
        tableItem.clear();
        tableModel.setRowCount(0);
    }

    public void refresh() {
        clear();
        new WordbookDao().getAll().forEach((dto) -> {
            tableItem.add(dto);
            tableModel.addRow(new Object[] { dto.getName() });
        });
        repaint();
    }

    private JPopupMenu createPopupMenu() {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem select = new JMenuItem("선택하기");
        JMenuItem remove = new JMenuItem("지우기");

        select.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "선택했어용");
            }
        });

        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(lastFocusedWord.getName());
                JOptionPane.showMessageDialog(null, "지울게용");
            }
        });

        popupMenu.add(select);
        popupMenu.add(remove);
        return popupMenu;
    }

    // #region mouseEventHandler
    private final MouseAdapter mouseEventHandler = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1 && table.getSelectedRow() != -1) {
                currentWord = tableItem.get(table.getSelectedRow());
                notifiable.notifyChanges();
            }
        }

        public void mousePressed(MouseEvent e) {
            var focusedIndex = table.rowAtPoint(e.getPoint());
            if (focusedIndex != -1) {
                lastFocusedWord = tableItem.get(focusedIndex);
            }
        };
    };
    // #endregion

    // #region dragDropHandler
    private final DropTarget dragDropHandler = new DropTarget() {
        @SuppressWarnings("unchecked")
        @Override
        public synchronized void drop(DropTargetDropEvent dtde) {
            try {
                dtde.acceptDrop(DnDConstants.ACTION_COPY);
                final List<File> droppedFiles = (List<File>) dtde.getTransferable()
                        .getTransferData(DataFlavor.javaFileListFlavor);
                for (File file : droppedFiles) {
                    if (file.getName().endsWith(".txt")) {
                        new Thread(() -> {
                            try {
                                TxtInserter.loadTextDataIntoDb(file);
                                refresh();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }).start();
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };
    // #endregion
}
