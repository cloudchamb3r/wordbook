package wordbook.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BoxLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import wordbook.dao.WordDao;
import wordbook.dto.CollectionDto;
import wordbook.dto.WordDto;

public class WordbookFrame extends JFrame {
    SideCollectionList sideCollection;
    WordHistoryTable wordHistoryTable;
    NanumLabel currentWordLabel;

    CollectionDto collection = null;
    List<WordDto> words = null;

    int cursor = -1;

    public WordbookFrame() throws Exception {
        super("단어장");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(0, 0, 510, 346);
        setLocationRelativeTo(null);

        JPanel quizPanel = new JPanel();
        quizPanel.setLayout(new BorderLayout());

        wordHistoryTable = new WordHistoryTable();
        quizPanel.add(wordHistoryTable, BorderLayout.CENTER);

        var quizPanelbtm = new JPanel();
        quizPanelbtm.setPreferredSize(new Dimension(365, 80));

        currentWordLabel = new NanumLabel("단어장을 선택해주세요!", 22f);

        var wordPanel = new JPanel();
        wordPanel.add(currentWordLabel);

        var btnPanel = new JPanel();
        var nextBtn = new NanumButton("다음 단어 볼래요", 12f);

        var memoBtn = new NanumButton("다시 안봐도 돼요", 12f);

        nextBtn.addActionListener(e -> {
            wordHistoryTable.add(getCurrentWord());
            cursor++;
            refresh();
        });

        memoBtn.addActionListener(e -> {
            var cwd = getCurrentWord();
            cwd.setMemorized(true);
            new WordDao().update(cwd);
            wordHistoryTable.add(getCurrentWord());
            cursor++;
            refresh();

        });

        btnPanel.add(nextBtn);
        btnPanel.add(memoBtn);

        quizPanelbtm.setLayout(new BoxLayout(quizPanelbtm, BoxLayout.Y_AXIS));

        quizPanelbtm.add(wordPanel);
        quizPanelbtm.add(btnPanel);
        quizPanel.add(quizPanelbtm, BorderLayout.SOUTH);

        sideCollection = new SideCollectionList(this);
        add(sideCollection, BorderLayout.WEST);
        add(quizPanel, BorderLayout.CENTER);
        setVisible(true);

        refresh();
    }

    public void refresh() {
        sideCollection.refresh();

        if (collection != null) {
            setTitle("단어장 [" + collection.getName() + "] (" + cursor + " / " + words.size() + ")");
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
        this.words = new WordDao().getAllNotMemorized(collection.getId());
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
}
