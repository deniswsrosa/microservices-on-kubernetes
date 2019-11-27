package com.cb.springdata.sample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.support.IndexManager;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CouchbaseConfig extends AbstractCouchbaseConfiguration {

    @Autowired
    private Environment env;


    @Override
    public IndexManager indexManager() {
        return new IndexManager(true, true, true);
    }

    @Override
    protected List<String> getBootstrapHosts() {
        return Arrays.asList(env.getProperty("spring.couchbase.bootstrap-hosts").split(","));
    }

    @Override
    protected String getBucketName() {
        return env.getProperty("spring.couchbase.bucket.name");
    }

    @Override
    protected String getBucketPassword() {
        return env.getProperty("spring.couchbase.bucket.password");
    }

    @Override
    protected String getUsername() {
        return env.getProperty("spring.couchbase.bucket.user");
    }

}
