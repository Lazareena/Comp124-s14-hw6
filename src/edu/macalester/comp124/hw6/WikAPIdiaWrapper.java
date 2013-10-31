package edu.macalester.comp124.hw6;

import org.apache.commons.collections.IteratorUtils;
import org.wikapidia.conf.ConfigurationException;
import org.wikapidia.core.cmd.Env;
import org.wikapidia.core.cmd.EnvBuilder;
import org.wikapidia.core.dao.*;
import org.wikapidia.core.lang.Language;
import org.wikapidia.core.lang.LanguageSet;
import org.wikapidia.core.lang.LocalId;
import org.wikapidia.core.model.LocalPage;
import org.wikapidia.core.model.NameSpace;
import org.wikapidia.core.model.UniversalPage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a wrapper I wrote around the WikAPIdia API for Shilad's intro Java course.
 *
 * The design strives to be understandable to intro students, so parts of it may seem
 * awkward to experienced Java programmers.
 *
 * @author Shilad Sen
 */
public class WikAPIdiaWrapper {
    private int CONCEPT_ALGORITHM_ID = 1;

    private final Env env;
    private LocalPageDao lpDao;
    private LocalLinkDao llDao;
    private UniversalPageDao upDao;

    /**
     * Creates a new wrapper object with default configuration settings.
     *
     * @throws org.wikapidia.conf.ConfigurationException
     */
    public WikAPIdiaWrapper() throws ConfigurationException {
        env = new EnvBuilder().setBaseDir(new File("wikAPIdia")).build();
        this.lpDao = env.getConfigurator().get(LocalPageDao.class);
        this.llDao = env.getConfigurator().get(LocalLinkDao.class);
        this.upDao = env.getConfigurator().get(UniversalPageDao.class);
    }

    /**
     * @return The list of installed languages.
     */
    public List<Language> getLanguages() {
        LanguageSet lset = env.getLanguages();
        return new ArrayList<Language>(lset.getLanguages());
    }

    /**
     * Returns the number of WikiLinks to a particular page.
     * @param page
     * @return
     * @throws org.wikapidia.core.dao.DaoException
     */
    public int getNumInLinks(LocalPage page) throws DaoException {
        DaoFilter filter = new DaoFilter()
                .setLanguages(page.getLanguage())
                .setDestIds(page.getLocalId());
        return llDao.getCount(filter);
    }

    /**
     * Returns a list of ALL the local pages in a particular language.
     * @param language
     * @return
     * @throws org.wikapidia.core.dao.DaoException
     */
    public List<LocalPage> getLocalPages(Language language) throws DaoException {
        DaoFilter df = new DaoFilter()
                .setLanguages(language)
                .setRedirect(false)
                .setDisambig(false)
                .setNameSpaces(NameSpace.ARTICLE);
        return IteratorUtils.toList(lpDao.get(df).iterator());
    }

    /**
     * Returns a list of the pages that represent the same concept in other languages.
     * @param page
     * @return
     * @throws org.wikapidia.core.dao.DaoException
     */
    public List<LocalPage> getInOtherLanguages(LocalPage page) throws DaoException {
        int conceptId = upDao.getUnivPageId(page, CONCEPT_ALGORITHM_ID);
        List<LocalPage> results = new ArrayList<LocalPage>();
        UniversalPage up = upDao.getById(conceptId, CONCEPT_ALGORITHM_ID);
        if (up == null) {
            return results;
        }
        for (LocalId lid : up.getLocalEntities()) {
            if (!lid.equals(page.toLocalId())) {
                LocalPage lp = lpDao.getById(lid.getLanguage(), lid.getId());
                if (lp != null) {
                    results.add(lp);
                }
            }
        }
        return results;
    }
}
