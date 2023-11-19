package wordbook.view.component;

import javax.swing.JButton;

import wordbook.font.NanumGothicFont;

public class NanumButton extends JButton {
    public NanumButton(String text, float fontSize) {
        super(text);
        setFont(NanumGothicFont.getFont().deriveFont(fontSize));
    }
}
