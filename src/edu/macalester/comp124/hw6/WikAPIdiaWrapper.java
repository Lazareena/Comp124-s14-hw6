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
 * This is a wrapper I wrote around the WikAPIdia API for COMP 124.
 *
 * The design strives to be understandable to intro students, so parts of it may seem
 * awkward to experienced Java programmers.
 *
 * @author Shilad Sen
 */
public class WikAPIdiaWrapper {
    public static final String PATH_LAPTOP = ".";
    public static final String PATH_LAB = "wikAPIdia";

    private static final int CONCEPT_ALGORITHM_ID = 1;

    private final Env env;
    private RawPageDao rpDao;
    private LocalPageDao lpDao;
    private LocalLinkDao llDao;
    private UniversalPageDao upDao;

    /**
     * Creates a new wrapper object with default configuration settings.
     *
     * baseDir should either be PATH_LAPTOP or PATH_LAB depending on whether
     * you are using a lab computer or not.
     */
    public WikAPIdiaWrapper(String baseDir) {
        try {
            File dbDir = new File(baseDir, "db");
            if (!dbDir.isDirectory()) {
                System.err.println(
                        "Database directory " + dbDir + " does not exist." +
                        "Have you downloaded and extracted the database?" +
                        "Are you running the program from the right directory?"
                );
                System.exit(1);
            }
            env = new EnvBuilder().setBaseDir(new File(baseDir)).build();
            this.rpDao = env.getConfigurator().get(RawPageDao.class);
            this.lpDao = env.getConfigurator().get(LocalPageDao.class);
            this.llDao = env.getConfigurator().get(LocalLinkDao.class);
            this.upDao = env.getConfigurator().get(UniversalPageDao.class);
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
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
     */
    public int getNumInLinks(LocalPage page) {
        DaoFilter filter = new DaoFilter()
                .setLanguages(page.getLanguage())
                .setDestIds(page.getLocalId());
        try {
            return llDao.getCount(filter);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns a list of ALL the local pages in a particular language.
     * @param language
     * @return
     */
    public List<LocalPage> getLocalPages(Language language) {
        DaoFilter df = new DaoFilter()
                .setLanguages(language)
                .setRedirect(false)
                .setDisambig(false)
                .setNameSpaces(NameSpace.ARTICLE);
        try {
            return IteratorUtils.toList(lpDao.get(df).iterator());
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns a list of the pages that represent the same concept in other languages.
     * @param page
     * @return
     */
    public List<LocalPage> getInOtherLanguages(LocalPage page) {
        try {
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
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPageText(LocalPage page) {
        try {
            return rpDao.getById(page.getLanguage(), page.getLocalId()).getBody();
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String args[]) {

    }
}
