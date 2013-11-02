package edu.macalester.comp124.hw6;

import org.junit.Test;
import org.wikapidia.conf.ConfigurationException;
import org.wikapidia.core.cmd.Env;
import org.wikapidia.core.cmd.EnvBuilder;
import org.wikapidia.core.dao.DaoException;
import org.wikapidia.core.dao.LocalPageDao;
import org.wikapidia.core.lang.Language;
import org.wikapidia.core.model.LocalPage;
import org.wikapidia.core.model.NameSpace;
import org.wikapidia.core.model.Title;

import java.io.File;

import static junit.framework.Assert.assertNotNull;

/**
 * @author Shilad Sen
 */
public class TestWikAPIdia {
    @Test
    public void testWorking() throws ConfigurationException, DaoException {
        EnvBuilder builder = new EnvBuilder();
        builder.setBaseDir(new File("wikAPIdia"));
        Env env = builder.build();
        System.out.println("loaded languages are " + env.getLanguages());
        LocalPageDao dao = env.getConfigurator().get(LocalPageDao.class);
        Language simple = Language.getByLangCode("simple");
        LocalPage page = dao.getByTitle(new Title("Barack Obama", simple), NameSpace.ARTICLE);
        assertNotNull(page);
    }
}
