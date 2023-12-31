package wordbook.inserter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;

import javax.swing.JOptionPane;

import wordbook.dao.WordbookDao;
import wordbook.dao.WordDao;

public class TxtInserter {

    public static void loadTextDataIntoDb(File txtFile) throws Exception {
        try (FileReader fr = new FileReader(txtFile, StandardCharsets.UTF_8)) {
            try (BufferedReader br = new BufferedReader(fr)) {
                String filenameWihtoutExt = txtFile.getName().substring(0, txtFile.getName().length() - 4);
                int collectionId = new WordbookDao().create(filenameWihtoutExt);
                boolean word = true;
                String line, wordString = null, meanString = null;
                while ((line = br.readLine()) != null) {
                    if (word) {
                        wordString = line;
                    }
                    if (!word) {
                        meanString = line;
                    }

                    if (wordString != null && meanString != null) {
                        wordString = wordString.trim();
                        meanString = meanString.trim();
                        new WordDao().add(collectionId, wordString, meanString);
                        wordString = null;
                        meanString = null;
                    }
                    word = !word;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "TxtInserter ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
}
