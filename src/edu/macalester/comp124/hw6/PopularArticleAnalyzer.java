package edu.macalester.comp124.hw6;

import org.wikapidia.conf.ConfigurationException;
import org.wikapidia.core.dao.DaoException;
import org.wikapidia.core.lang.Language;
import org.wikapidia.core.model.LocalPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Analyzes the overlap in popular concepts.
 * Experimental code for Shilad's intro Java course.
 *
 * @author Shilad Sen
 */
public class PopularArticleAnalyzer {
    private final WikAPIdiaWrapper wpApi;

    /**
     * Constructs a new analyzer.
     * @param wpApi
     */
    public PopularArticleAnalyzer(WikAPIdiaWrapper wpApi) {
        this.wpApi = wpApi;
    }

    /**
     * Returns the n most popular articles in the specified language.
     * @param language
     * @param n
     * @return
     */
    public List<LocalPage> getMostPopular(Language language, int n) {
        return null;    // TODO: implement me for part 1
    }

    public static void main(String args[]) {
        Language simple = Language.getByLangCode("simple");

        // Change the path below to point to the parent directory on the lab computer
        // or laptop that holds the BIG "db" directory.
        WikAPIdiaWrapper wrapper = new WikAPIdiaWrapper("./wikAPIdia-small");

        // A simple test of the WikAPIdia wrapper.
        LocalPage page = wrapper.getLocalPageByTitle(simple, "Apple");
        if (page == null) {
            System.err.println("COULDN'T FIND Apple! Something's wrong...");
            System.exit(1);
        }
        System.out.println("page is " + page);

        // TODO: Complete me for part 1.
        // construct a PopularArticleAnalyzer
        // Print out the 20 most popular articles in the language.
        // United states should be #1
    }
}
