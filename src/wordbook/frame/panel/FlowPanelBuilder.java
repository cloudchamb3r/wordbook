package wordbook.frame.panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;

public class FlowPanelBuilder {
    JPanel panel;

    public FlowPanelBuilder() {
        panel = new JPanel();
    }

    public FlowPanelBuilder add(Component component) {
        panel.add(component);
        return this;
    }

    public FlowPanelBuilder preferredSize(int width, int height) {
        panel.setPreferredSize(new Dimension(width, height));
        return this;
    }

    public FlowPanelBuilder background(Color color) {
        panel.setBackground(color);
        return this;
    }

    public JPanel build() {
        return panel;
    }
}
