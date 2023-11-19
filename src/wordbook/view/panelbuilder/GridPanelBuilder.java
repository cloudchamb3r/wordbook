package wordbook.view.panelbuilder;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class GridPanelBuilder {
    JPanel panel;

    public GridPanelBuilder(int rows, int cols) {
        panel = new JPanel();
        panel.setLayout(new GridLayout(rows, cols));
    }

    public GridPanelBuilder add(Component component) {
        panel.add(component);
        return this;
    }

    public GridPanelBuilder preferredSize(int width, int height) {
        panel.setPreferredSize(new Dimension(width, height));
        return this;
    }

    public GridPanelBuilder background(Color color) {
        panel.setBackground(color);
        return this;
    }

    public JPanel build() {
        return panel;
    }
}
