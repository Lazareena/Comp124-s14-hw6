package edu.macalester.comp124.hw6;

import acm.graphics.GDimension;
import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.program.GraphicsProgram;
import org.wikapidia.core.lang.Language;
import org.wikapidia.core.model.LocalPage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

/**
 * Visualizes the most popular concepts in each language,
 * and the pages in other languages associated with the same concept.
 *
 * This class MUST be run as a Java application (ConceptVisualizer.main()).
 * This class MUST be run from the module directory.
 *
 * @author Shilad Sen
 */
public class ConceptVisualizer extends GraphicsProgram {
    private static final int PAGES_PER_LANG = 30;
    private WikAPIdiaWrapper wp;

    private static final Language SIMPLE = Language.getByLangCode("simple");
    private static final Language HINDI = Language.getByLangCode("hi");
    private static final Language LATIN = Language.getByLangCode("la");

    private LanguageBoxes simpleBoxes;
    private LanguageBoxes latinBoxes;
    private LanguageBoxes hindiBoxes;
    private FancyLabel label;

    public void init() {
        try {
            GImage bg = new GImage(ImageIO.read(getClass().getResource("/background.jpg")));
            bg.setSize(new GDimension(800, 400));
            add(bg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setSize(800, 400);
    }

    public void run() {
        setSize(800, 400);
        wp = new WikAPIdiaWrapper("/Users/ssen/wikAPIdia");
        label = new FancyLabel("Hover over a title to analyze it");
        label.setColor(ColorPallete.FONT_COLOR);

        add(label, 20, 20);
        simpleBoxes = makeBoxes(SIMPLE, ColorPallete.COLOR1, 150);
        hindiBoxes = makeBoxes(HINDI, ColorPallete.COLOR2, 225);
        latinBoxes = makeBoxes(LATIN, ColorPallete.COLOR3, 300);
        addMouseListeners();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        GObject o = getElementAt(e.getX(), e.getY());
        if (o instanceof LanguageBoxes) {
            LanguageBoxes boxes = (LanguageBoxes)o;
            LocalPageBox box = boxes.getLocalBoxAt(e.getX(), e.getY());
            if (box == null) {
                simpleBoxes.unhighlight();
                latinBoxes.unhighlight();
                hindiBoxes.unhighlight();
                label.setText("");
            } else {
                List<LocalPage> pages = null;
                String description = "";

                LocalPage lp = box.getPage();
                System.out.println("You hovered over " + lp);

                // Get all pages representing the same concept.
                // Build up a textual description of the pages in each language.
                // You can insert line breaks in your textual description using newlines ("\n").
                pages = wp.getInOtherLanguages(lp);
                for (LocalPage lp2 : pages) {
                    description += lp2.getLanguage() + ": " + lp2.getTitle() + "\n";
                }

                label.setText(description);
                simpleBoxes.highlightPages(pages);
                latinBoxes.highlightPages(pages);
                hindiBoxes.highlightPages(pages);
            }
        } else {
            simpleBoxes.unhighlight();
            latinBoxes.unhighlight();
            hindiBoxes.unhighlight();
            label.setText("");
        }
    }

    public LanguageBoxes makeBoxes(Language language, Color color, int y) {
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
