package com.raviparekh.quickUrls.repositories;

import com.raviparekh.quickUrls.exceptions.URLNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;


public class JdbcURLDao implements URLDAO {

    private static String INSERT_URL = "INSERT INTO URLS(URLKEY, FULLURL) VALUES(?, ?)";

    private static String QUERY_URL = "SELECT FULLURL FROM URLS WHERE URLKEY = '%s'";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveURL(String URLKey, String fullURL) {
        SqlRowSet urlsForKey = jdbcTemplate.queryForRowSet(String.format(QUERY_URL, URLKey));

        if (urlsForKey.isFirst()) {
            jdbcTemplate.update(INSERT_URL, URLKey, fullURL);
        }
    }

    public String getFullURL(String URLKey) throws URLNotFound {
        SqlRowSet urlsForKey = jdbcTemplate.queryForRowSet(String.format(QUERY_URL, URLKey));
        if (urlsForKey.next()){
            return urlsForKey.getString("FULLURL");
        }
        throw new URLNotFound("No URL found");
    }

}
