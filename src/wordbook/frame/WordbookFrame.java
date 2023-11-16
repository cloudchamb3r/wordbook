package wordbook.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import wordbook.dao.WordDao;
import wordbook.dto.CollectionDto;
import wordbook.dto.WordDto;
import wordbook.frame.panel.BorderPanelBuilder;
import wordbook.frame.panel.BoxPanelBuilder;
import wordbook.frame.panel.FlowPanelBuilder;

public class WordbookFrame extends JFrame implements ActionListener {
    SideCollectionList sideCollection;
    WordHistoryTable wordHistoryTable;
    NanumLabel currentWordLabel;

    CollectionDto collection;
    List<WordDto> words;

    int cursor = -1;

    public WordbookFrame() throws Exception {
        super("단어장");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(0, 0, 510, 346);
        setLocationRelativeTo(null);

        sideCollection = new SideCollectionList(this);
        wordHistoryTable = new WordHistoryTable();
        currentWordLabel = new NanumLabel("단어장을 선택해주세요!", 22f);
        JButton nextBtn = new NanumButton("다음 단어 볼래요", 12f);
        JButton memoBtn = new NanumButton("다시 안봐도 돼요", 12f);

        nextBtn.setActionCommand("next");
        nextBtn.addActionListener(this);

        memoBtn.setActionCommand("memorized");
        memoBtn.addActionListener(this);

        add(sideCollection, BorderLayout.WEST);
        add(
                new BoxPanelBuilder()
                        .axis(BoxLayout.Y_AXIS)
                        .add(wordHistoryTable)
                        .add(
                                new BoxPanelBuilder()
                                        .preferredSize(2000, 120)
                                        .axis(BoxLayout.Y_AXIS)
                                        .add(
                                                new FlowPanelBuilder()
                                                        .add(currentWordLabel)
                                                        .build())
                                        .add(
                                                new FlowPanelBuilder()
                                                        .add(nextBtn)
                                                        .add(memoBtn)
                                                        .build())
                                        .build()

                        )
                        .build(),
                BorderLayout.CENTER);
        setVisible(true);
        refresh();
    }

    public void refresh() {
        sideCollection.fetchAll();
        if (collection != null) {
            words = new WordDao().getAllNotMemorized(collection.getId());
            if (words.size() != 0) {
                cursor %= words.size();
            }
            setTitle("단어장 [%s] (%d/%d)".formatted(collection.getName(), cursor, words.size()));
        } else {
            setTitle("단어장");
        }
        if (getCurrentWord() != null) {
            currentWordLabel.setText(getCurrentWord().getWord());
        } else {
            currentWordLabel.setText("단어장을 선택해주세요");
        }
    }

    public void setCollection(CollectionDto dto) {
        this.collection = dto;
        this.cursor = 0;
        wordHistoryTable.clear();
        refresh();
    }

    public WordDto getCurrentWord() {
        System.out.println("cursor : " + cursor);
        if (cursor < 0 || cursor >= words.size())
            return null;

        System.out.println(words.get(cursor));
        return words.get(cursor);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        WordDto currentWord = getCurrentWord();
        if (currentWord == null) {
            JOptionPane.showMessageDialog(this, "선택된 단어장이 없는거 같아요.");
            return;
        }
        if (e.getActionCommand().equals("memorized")) {
            currentWord.setMemorized(true);
            new WordDao().update(currentWord);
        }
        wordHistoryTable.add(currentWord);
        cursor = (cursor + 1) % words.size();

        refresh();
    }
}
