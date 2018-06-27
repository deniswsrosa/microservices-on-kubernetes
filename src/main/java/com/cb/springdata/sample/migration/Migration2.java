package com.cb.springdata.sample.migration;

import com.couchbase.client.java.Bucket;
import com.github.liquicouch.changeset.ChangeLog;
import com.github.liquicouch.changeset.ChangeSet;

/**
 * This is an example of how to use it without Spring, in this case you can execute all the queries via the Bucket argument.
 */
@ChangeLog(order = "2")
public class Migration2 {

    @ChangeSet(order = "1", id = "someChangeId21", author = "testAuthor")
    public void importantWorkToDo(){
        System.out.println("----------Migration2 - Method1");
    }

    @ChangeSet(order = "2", id = "someChangeId22", author = "testAuthor")
    public void method2(){
        System.out.println("----------Migration2 - Method2");
    }

    @ChangeSet(order = "3", id = "someChangeId23", author = "testAuthor")
    public void method3(){
        System.out.println("----------Migration2 - Method3");
    }

    @ChangeSet(order = "4", id = "someChangeId24", author = "testAuthor")
    public void method4(){
        System.out.println("----------Migration2 - Method4");
    }

    @ChangeSet(order = "5", id = "someChangeId25", author = "testAuthor")
    public void method5(Bucket bucket){
        System.out.println("----------Migration2 - Method5");
    }

    @ChangeSet(order = "6", id = "someChangeId256", author = "testAuthor", runAlways=true)
    public void method6(Bucket bucket){
        System.out.println("----------Migration2 - Method6 - THIS SHOULD ALWAYS RUN "+bucket.name());
    }
}
