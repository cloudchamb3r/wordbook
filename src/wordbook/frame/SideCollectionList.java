package wordbook.frame;

import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import wordbook.dao.CollectionDao;
import wordbook.dto.CollectionDto;
import wordbook.font.NanumGothicFont;
import wordbook.inserter.TxtInserter;

public class SideCollectionList extends JScrollPane implements MouseListener {
    private final DefaultListModel<CollectionDto> listModel;
    private final WordbookFrame frame;

    private final JList<CollectionDto> list;

    public SideCollectionList(WordbookFrame frame) {
        super();
        setPreferredSize(new Dimension(150, 0));

        this.frame = frame;

        this.listModel = new DefaultListModel<>();

        this.list = new JList<>(listModel);
        this.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.list.setLayoutOrientation(JList.VERTICAL);
        this.list.setVisibleRowCount(-1);
        this.list.setFont(NanumGothicFont.getFont().deriveFont(12f));
        this.list.setModel(listModel);
        this.list.addMouseListener(this);
        this.list.setDropTarget(new DropTarget() {
            @SuppressWarnings("unchecked")
            @Override
            public synchronized void drop(DropTargetDropEvent dtde) {
                try {
                    dtde.acceptDrop(DnDConstants.ACTION_COPY);
                    final List<File> droppedFiles = (List<File>) dtde.getTransferable()
                            .getTransferData(DataFlavor.javaFileListFlavor);
                    for (File file : droppedFiles) {
                        if (file.getName().endsWith(".txt")) {
                            TxtInserter.loadTextData(file);
                            frame.refresh();
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
        refresh();
        this.setViewportView(this.list);
    }

    public void refresh() {
        listModel.clear();
        new CollectionDao().getAll().forEach(listModel::addElement);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1 && list.getSelectedValue() != null) {
            frame.setCollection(list.getSelectedValue());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
