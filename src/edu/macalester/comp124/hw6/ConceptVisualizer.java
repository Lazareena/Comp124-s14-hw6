package edu.macalester.comp124.hw6;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.program.GraphicsProgram;
import org.wikapidia.conf.ConfigurationException;
import org.wikapidia.core.dao.DaoException;
import org.wikapidia.core.lang.Language;
import org.wikapidia.core.model.LocalPage;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * @author Shilad Sen
 */
public class ConceptVisualizer extends GraphicsProgram {
    private static final int PAGES_PER_LANG = 50;
    private WikAPIdiaWrapper wp;

    private static final Language SIMPLE = Language.getByLangCode("simple");
    private static final Language HINDI = Language.getByLangCode("hi");
    private static final Language LATIN = Language.getByLangCode("la");

    private LanguageBoxes simpleBoxes;
    private LanguageBoxes latinBoxes;
    private LanguageBoxes hindiBoxes;
    private FancyLabel label;

    public void init() {
        setSize(800, 600);
    }

    public void run() {
        setSize(800, 600);
        try {
            wp = new WikAPIdiaWrapper();
            label = new FancyLabel("FOo bar\nbaz");
            add(label, 20, 20);
            simpleBoxes = makeBoxes(SIMPLE, Color.GREEN, 300);
            hindiBoxes = makeBoxes(HINDI, Color.RED, 400);
            latinBoxes = makeBoxes(LATIN, Color.BLUE, 500);
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        addMouseListeners();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        GObject o = getElementAt(e.getX(), e.getY());
        if (o instanceof LanguageBoxes) {
            LanguageBoxes boxes = (LanguageBoxes)o;
            LocalPageBox box = boxes.getLocalBoxAt(e.getX() - boxes.getX(), e.getY() - boxes.getY());
            if (box == null) {
                simpleBoxes.restoreColors();
                latinBoxes.restoreColors();
                hindiBoxes.restoreColors();
                label.setText("");
            } else {
                try {
                    List<LocalPage> pages = wp.getInOtherLanguages(box.getPage());
                    pages.add(box.getPage());
                    simpleBoxes.highlightPages(pages);
                    latinBoxes.highlightPages(pages);
                    hindiBoxes.highlightPages(pages);

                    String description = "";
                    for (LocalPage lp : pages) {
                        description += lp.getLanguage() + ": " + lp.getTitle().getTitleStringWithoutNamespace() + "\n";
                    }
                    label.setText(description);
                } catch (DaoException e1) {
                    e1.printStackTrace();
                }
            }
        } else {
            simpleBoxes.restoreColors();
            latinBoxes.restoreColors();
            hindiBoxes.restoreColors();
            label.setText("");
        }
    }

    public LanguageBoxes makeBoxes(Language language, Color color, int y) throws DaoException {
        PopularArticleAnalyzer analyzer = new PopularArticleAnalyzer(wp);
        List<LocalPage> popular = analyzer.getMostPopular(language, PAGES_PER_LANG);
        LanguageBoxes boxes = new LanguageBoxes(color, language, popular);
        add(boxes, 20, y);
        return boxes;
    }

    public static void main(String args[]) {
        ConceptVisualizer visualizer = new ConceptVisualizer();
        visualizer.start(args);
    }
}
