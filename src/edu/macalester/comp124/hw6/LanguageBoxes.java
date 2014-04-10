package edu.macalester.comp124.hw6;

import acm.graphics.GCompound;
import acm.graphics.GLabel;
import acm.graphics.GPoint;
import org.wikapidia.core.lang.Language;
import org.wikapidia.core.model.LocalPage;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A row of boxes for local pages in some language.
 *
 * @author Shilad Sen
 */
public class LanguageBoxes extends GCompound {
    private static final int PADDING = 5;

    private final Language language;
    private final Color color;
    private final List<LocalPageBox> boxes = new ArrayList<LocalPageBox>();

    /**
     * Creates a new set of language boxes associated with some pages.
     *
     * @param color
     * @param language
     * @param pages
     */
    public LanguageBoxes(Color color, Language language, List<LocalPage> pages) {
        this.color = color;
        this.language = language;
        GLabel label = new GLabel(language.toString());
        label.setColor(Color.LIGHT_GRAY);
        label.setFont("Helvetica-20");
        add(label, 0, 0);
        for (LocalPage lp : pages) {
            LocalPageBox box = new LocalPageBox(color, lp);
            double x = (box.getWidth() + PADDING) * boxes.size();
            double y = label.getHeight() + PADDING;
            add(box, x, y);
            boxes.add(box);
        }
    }

    /**
     * Highlights the specified pages (i.e. colors them "normal").
     * All other pages should be colored gray.
     *
     * @param pages
     */

    public void highlightPages(List<LocalPage> pages) {
        Color normal = this.color;

        for (int j = 0; j < boxes.size(); j++) {

            for (int i = 0; i < pages.size(); i++) {
                LocalPage a = pages.get(i);
                if (a.equals(boxes.get(j).getPage())) {
                    boxes.get(j).setFillColor(normal);
                    break;
                } else {
                    boxes.get(j).setFillColor(color.GRAY);
                }
            }
        }
    }

    /**
     * Colors all pages "normally."
     */
    public void unhighlight() {
        // TODO: implement me for part 3
        Color normal = this.color;
        for (int i = 0; i < boxes.size(); i++) {
            boxes.get(i).setFillColor(normal);
        }
    }

    public Language getLanguage() {
        return language;
    }

    public List<LocalPageBox> getBoxes() {
        return boxes;
    }

    /**
     * Returns the local page box at some location.
     *
     * @param x
     * @param y
     * @return
     */
    public LocalPageBox getLocalBoxAt(double x, double y) {
        GPoint p = getLocation();
        return (LocalPageBox) getElementAt(x - p.getX(), y - p.getY());
    }
}
