package wordbook.font;

import java.io.InputStream;
import javax.swing.JOptionPane;
import java.awt.Font;

public class NanumGothicFont {
    static InputStream fontStream = null;
    static Font font = null;

    public static Font getFont() {
        if (font != null) {
            return font;
        }
        if (fontStream == null) {
            fontStream = NanumGothicFont.class.getResourceAsStream("/wordbook/font/NanumGothic.ttf");
        }
        if (font == null) {
            try {
                font = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "NanumGothic Font Error",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        }
        return font;
    }
}
