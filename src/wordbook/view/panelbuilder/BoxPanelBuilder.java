package wordbook.view.panelbuilder;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class BoxPanelBuilder {
    JPanel panel;

    public BoxPanelBuilder(int axis) {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, axis));

    }

    public BoxPanelBuilder add(Component component) {
        panel.add(component);
        return this;
    }

    public BoxPanelBuilder preferredSize(int width, int height) {
        panel.setPreferredSize(new Dimension(width, height));
        return this;
    }

    public BoxPanelBuilder background(Color color) {
        panel.setBackground(color);
        return this;
    }

    public JPanel build() {
        return panel;
    }
}
