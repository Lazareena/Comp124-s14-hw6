package edu.macalester.comp124.hw6;

import acm.graphics.GCompound;
import acm.graphics.GLabel;
import org.wikapidia.core.lang.Language;
import org.wikapidia.core.model.LocalPage;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Shilad Sen
 */
public class LanguageBoxes extends GCompound {
    private final Language language;
    private final Color color;
    private final List<LocalPageBox> boxes = new ArrayList<LocalPageBox>();

    public LanguageBoxes(Color color, Language language, List<LocalPage> pages) {
        this.color = color;
        this.language = language;
        GLabel label = new GLabel(language.toString());
        add(label, 0, 0);
        for (LocalPage lp : pages) {
            LocalPageBox box = new LocalPageBox(color, lp);
            add(box, 20 * boxes.size(), label.getHeight() + 20);
            boxes.add(box);
        }
    }

    public void highlightPages(List<LocalPage> pages) {
        for (LocalPageBox box : boxes) {
            if (pages.contains(box.getPage())) {
                box.fadeIn();
            } else {
                box.fadeOut();
            }
        }
    }

    public void restoreColors() {
        for (LocalPageBox box : boxes) {
            box.fadeIn();
        }
    }

    public Language getLanguage() {
        return language;
    }

    public List<LocalPageBox> getBoxes() {
        return boxes;
    }

    public LocalPageBox getLocalBoxAt(double x, double y) {
        return (LocalPageBox) getElementAt(x, y);
    }
}
