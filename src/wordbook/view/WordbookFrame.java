package wordbook.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import wordbook.dao.WordDao;
import wordbook.dto.WordDto;
import wordbook.dto.WordbookDto;
import wordbook.interfaces.Notifiable;
import wordbook.view.component.NanumButton;
import wordbook.view.component.NanumLabel;
import wordbook.view.panelbuilder.BorderPanelBuilder;
import wordbook.view.panelbuilder.FlowPanelBuilder;
import wordbook.view.panelbuilder.GridPanelBuilder;

public class WordbookFrame extends JFrame implements ActionListener, Notifiable {
    WordbookTable wordbookTable;
    WordHistoryTable wordHistoryTable;
    NanumLabel currentWordLabel, currentMeanLabel;

    WordbookDto currentWordbook;
    List<WordDto> words;
    boolean showWord = true;
    int cursor = -1;

    boolean flicking = false;

    public WordbookFrame() throws Exception {
        super("단어장");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(0, 0, 600, 350);
        setLocationRelativeTo(null);

        wordbookTable = new WordbookTable(this, 100, 0);

        wordHistoryTable = new WordHistoryTable();

        currentWordLabel = new NanumLabel("단어장을 선택해주세요!", 22f);
        currentMeanLabel = new NanumLabel(" ", 14f);

        currentWordLabel.setHorizontalAlignment(JLabel.CENTER);
        currentMeanLabel.setHorizontalAlignment(JLabel.CENTER);

        JButton nextBtn = new NanumButton("다음 단어 볼래요", 12f);
        JButton memoBtn = new NanumButton("다시 안봐도 돼요", 12f);
        JButton flickBtn = new NanumButton("깜빡이 모드", 12f);

        nextBtn.setActionCommand("next");
        nextBtn.addActionListener(this);

        memoBtn.setActionCommand("memorized");
        memoBtn.addActionListener(this);

        flickBtn.setActionCommand("flick");
        flickBtn.addActionListener((e) -> {
            flicking = !flicking;

            if (getCurrentWord() == null) {
                flicking = false;
                JOptionPane.showMessageDialog(this, "선택된 단어장이 없는거 있습니다");
                return;
            }

            if (flicking) {
                new Thread(() -> {
                    while (flicking) {
                        try {
                            Thread.sleep(1200);
                            actionPerformed(e);
                        } catch (Exception x) {
                            x.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        var buttons = new FlowPanelBuilder()
                .add(flickBtn)
                .add(nextBtn)
                .add(memoBtn)
                .build();

        var mainPanel = new BorderPanelBuilder()
                .addCenter(wordHistoryTable)
                .addSouth(new GridPanelBuilder(3, 1).add(currentWordLabel).add(currentMeanLabel).add(buttons).build())
                .build();

        add(new BorderPanelBuilder()
                .addWest(wordbookTable)
                .addCenter(mainPanel)
                .build());

        setVisible(true);
        refresh();
    }

    public void refresh() {
        wordbookTable.refresh();
        if (currentWordbook != null) {
            words = new WordDao().getAllNotMemorized(currentWordbook.getId());
            if (words.size() != 0) {
                cursor %= words.size();
            }
            setTitle("단어장 [%s] (%d/%d)".formatted(currentWordbook.getName(), cursor, words.size()));
        } else {
            setTitle("단어장");
        }
        if (getCurrentWord() != null) {
            if (showWord) {
                currentWordLabel.setText(getCurrentWord().getWord());
                currentMeanLabel.setText(" ");
            } else {
                currentMeanLabel.setText(getCurrentWord().getMean());
            }
        } else {
            currentWordLabel.setText("단어장을 선택해주세요");
        }
    }

    public WordDto getCurrentWord() {
        System.out.println("cursor : " + cursor);
        if (cursor < 0 || cursor >= words.size())
            return null;

        System.out.println(words.get(cursor));
        return words.get(cursor);
    }

    @Override
    public void notifyChanges() {
        if (wordbookTable != null && currentWordbook != wordbookTable.getCurrentWordBook()) {
            currentWordbook = wordbookTable.getCurrentWordBook();
            words = new WordDao().getAllNotMemorized(currentWordbook.getId());

            cursor = 0;
            wordHistoryTable.clear();

            this.currentWordLabel.setText(words.get(0).getWord());
            this.currentMeanLabel.setText(" ");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!showWord)
            return;

        if (words.isEmpty()) {
            JOptionPane.showMessageDialog(this, "단어장이 비어있습니다");
            return;
        }

        WordDto currentWord = getCurrentWord();
        if (currentWord == null) {
            JOptionPane.showMessageDialog(this, "선택된 단어장이 없는거 같아요.");
            return;
        }

        new Thread(() -> {
            try {
                showWord = false;
                Thread.sleep(500);
                showWord = true;
                refresh();
            } catch (Exception x) {
            }
        }).start();
        if (e.getActionCommand().equals("memorized")) {
            currentWord.setMemorized(true);
            new WordDao().update(currentWord);
        }
        wordHistoryTable.add(currentWord);
        refresh();
        if (cursor + 1 >= words.size()) {
            wordHistoryTable.removeFront();
        }
        wordHistoryTable.scrolldown();
        cursor = (cursor + 1) % words.size();
        repaint();
    }
}
