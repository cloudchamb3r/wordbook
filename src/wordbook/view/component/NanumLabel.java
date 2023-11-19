package wordbook.view.component;

import javax.swing.JLabel;

import wordbook.font.NanumGothicFont;

public class NanumLabel extends JLabel {
    public NanumLabel(String text, float fontSize) {
        super(text);
        setFont(NanumGothicFont.getFont().deriveFont(fontSize));
    }
}
