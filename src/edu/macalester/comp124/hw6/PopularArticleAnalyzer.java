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

    public PopularArticleAnalyzer(WikAPIdiaWrapper wpApi) {
        this.wpApi = wpApi;
    }

    public List<LocalPage> getMostPopular(Language language, int n) {
        List<LocalPagePopularity> popularities = new ArrayList<LocalPagePopularity>();
        for (LocalPage lp : wpApi.getLocalPages(language)) {
            popularities.add(new LocalPagePopularity(lp, wpApi.getNumInLinks(lp)));
        }

        Collections.sort(popularities);
        Collections.reverse(popularities);

        List<LocalPage> result = new ArrayList<LocalPage>();
        for (int i = 0; i < n; i++) {
            result.add(popularities.get(i).getPage());
        }
        return result;
    }

    public static void main(String args[]) {
        Language simple = Language.getByLangCode("simple");
        WikAPIdiaWrapper wrapper = new WikAPIdiaWrapper("/Users/ssen/wikAPIdia");
        PopularArticleAnalyzer analyzer = new PopularArticleAnalyzer(wrapper);

        System.out.println("most popular languages in " + simple);
        for (LocalPage lp : analyzer.getMostPopular(simple, 50)) {
            System.out.println("\t" + lp);
        }
    }
}
