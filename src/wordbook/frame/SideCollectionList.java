package wordbook.frame;

import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import wordbook.dao.CollectionDao;
import wordbook.dto.CollectionDto;
import wordbook.font.NanumGothicFont;
import wordbook.inserter.TxtInserter;

public class SideCollectionList extends JScrollPane {
    private final DefaultListModel<CollectionDto> listModel;

    private final JList<CollectionDto> list;

    public SideCollectionList(WordbookFrame frame) {
        super();
        setPreferredSize(new Dimension(150, 0));

        listModel = new DefaultListModel<>();

        list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        list.setFont(NanumGothicFont.getFont().deriveFont(12f));
        list.setModel(listModel);
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1 && list.getSelectedValue() != null) {
                    frame.setCollection(list.getSelectedValue());
                }
            }
        });
        list.setDropTarget(new DropTarget() {
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
                                    fetchAll(); 
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }).start();
                            // TxtInserter.loadTextDataIntoDb(file);
                            // fetchAll();

                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
        fetchAll();
        setViewportView(list);
    }

    public void fetchAll() {
        listModel.clear();
        new CollectionDao().getAll().forEach(listModel::addElement);
    }
}
