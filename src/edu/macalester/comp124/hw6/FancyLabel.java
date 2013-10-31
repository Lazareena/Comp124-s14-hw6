package edu.macalester.comp124.hw6;

import acm.graphics.GCompound;
import acm.graphics.GLabel;

/**
 * @author Shilad Sen
 */
public class FancyLabel extends GCompound {
    public FancyLabel(String text) {
        this.setText(text);
    }

    public void setText(String text) {
        this.removeAll();
        int y = 0;
        for (String line : text.split("\n")) {
            GLabel label = new GLabel(line);
            add(label, 0.0, label.getHeight() + y);
            y += label.getHeight() + 10;
        }
    }
}
