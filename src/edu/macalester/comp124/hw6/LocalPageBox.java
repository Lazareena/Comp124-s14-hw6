package edu.macalester.comp124.hw6;

import acm.graphics.GCompound;
import acm.graphics.GRect;
import org.wikapidia.core.model.LocalPage;

import java.awt.*;

/**
 * @author Shilad Sen
 */
public class LocalPageBox extends GCompound {
    private static final int WIDTH = 15;
    private static final int HEIGHT = 20;

    private final LocalPage page;
    private final Color color;
    private final GRect grect;

    public LocalPageBox(Color color, LocalPage page) {
        this.color = color;
        this.page = page;
        grect = new GRect(WIDTH, HEIGHT);
        grect.setFilled(true);
        grect.setFillColor(color);
        add(grect);
    }

    public LocalPage getPage() {
        return page;
    }

    public void fadeOut() {
        grect.setFillColor(Color.LIGHT_GRAY);
    }

    public void fadeIn() {
        grect.setFillColor(color);
    }
}
