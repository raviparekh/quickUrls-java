package com.raviparekh.quickUrls.repositories;

import com.raviparekh.quickUrls.exceptions.URLNotFound;
import com.raviparekh.quickUrls.testConfigFixtures.TestRepositoryConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestRepositoryConfig.class)
public class SimpleURLServiceTest {

    @Autowired
    private URLDAO urldao;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static String QUERY_URL = "SELECT FULLURL FROM URLS WHERE URLKEY = '%s'";

    private static String INSERT_URL = "INSERT INTO URLS(URLKEY, FULLURL) VALUES(?, ?)";

    private String urlKey = "1234a";

    private String expectedUrl = "http://google.co.uk";

    @Test
    @Transactional
    public void addingNewUrlIsPersistedOnDB() {
        urldao.saveURL(urlKey, expectedUrl);

        SqlRowSet dbEntry = jdbcTemplate.queryForRowSet(String.format(QUERY_URL, "1234a"));
        assertTrue("Expected an entry in db", dbEntry.first());
        assertEquals(expectedUrl, dbEntry.getString("FULLURL"));
    }

    @Test
    @Transactional
    public void gettingByUrlKeyShouldReturnFullURL() throws URLNotFound {
        jdbcTemplate.update(INSERT_URL, urlKey, expectedUrl);

        String fullURL = urldao.getFullURL(urlKey);

        assertEquals(expectedUrl, fullURL);
    }

    @Test(expected = URLNotFound.class)
    public void gettingByNonPersistedUrlKeyShouldRaiseURLNotFound() throws URLNotFound {
        urldao.getFullURL(urlKey);
    }
}
