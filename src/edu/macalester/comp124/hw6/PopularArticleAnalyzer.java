package edu.macalester.comp124.hw6;

import org.wikapidia.conf.ConfigurationException;
import org.wikapidia.core.dao.DaoException;
import org.wikapidia.core.lang.Language;
import org.wikapidia.core.model.LocalPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Analyzes the overlap in popular concepts.
 * Experimental code for Shilad's intro Java course.
 * Note that you MUST correct WikAPIdiaWrapper.DATA_DIRECTORY before this works.
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
        List<LocalPagePopularity> results = new ArrayList<LocalPagePopularity>();
        for (LocalPage lp : wpApi.getLocalPages(language)) {
            int inLinks = wpApi.getNumInLinks(lp);
            LocalPagePopularity a = new LocalPagePopularity(lp, inLinks);
            results.add(a);
        }

        Collections.sort(results);

        List<LocalPage> popular = new ArrayList<LocalPage>();
        for (LocalPagePopularity lpp : results.subList(0, n-1)) {
            popular.add(lpp.getPage());
            System.out.println(lpp.getPage().getTitle());
        }
        return popular;
    }

    public static void main(String args[]) {
        Language simple = Language.getByLangCode("simple");

        WikAPIdiaWrapper wrapper = new WikAPIdiaWrapper("/Users/Reena/IdeaProjects/comp124s14/hw6/wp/wp-db-large");

        PopularArticleAnalyzer popularArticles = new PopularArticleAnalyzer(wrapper);
        popularArticles.getMostPopular(simple, 20);

    }
}
