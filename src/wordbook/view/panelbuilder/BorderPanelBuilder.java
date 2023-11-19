package wordbook.view.panelbuilder;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;

public class BorderPanelBuilder {
    JPanel panel;

    public BorderPanelBuilder() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
    }

    public BorderPanelBuilder addSouth(Component component) {
        panel.add(component, BorderLayout.SOUTH);
        return this;
    }

    public BorderPanelBuilder addNorth(Component component) {
        panel.add(component, BorderLayout.NORTH);
        return this;
    }

    public BorderPanelBuilder addEast(Component component) {
        panel.add(component, BorderLayout.EAST);
        return this;
    }

    public BorderPanelBuilder addWest(Component component) {
        panel.add(component, BorderLayout.WEST);
        return this;
    }

    public BorderPanelBuilder addCenter(Component component) {
        panel.add(component, BorderLayout.CENTER);
        return this;
    }

    public BorderPanelBuilder preferredSize(int width, int height) {
        panel.setPreferredSize(new Dimension(width, height));
        return this;
    }

    public JPanel build() {
        return panel;
    }
}
