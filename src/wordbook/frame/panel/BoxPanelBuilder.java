package wordbook.frame.panel;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class BoxPanelBuilder {
    JPanel panel;

    public BoxPanelBuilder() {
        panel = new JPanel();
    }

    public BoxPanelBuilder add(Component component) {
        panel.add(component);
        return this;
    }

    public BoxPanelBuilder axis(int axis) {
        panel.setLayout(new BoxLayout(panel, axis));
        return this;
    }

    public BoxPanelBuilder preferredSize(int width, int height) {
        panel.setPreferredSize(new Dimension(width, height));
        return this;
    }

    public JPanel build() {
        return panel;
    }
}
