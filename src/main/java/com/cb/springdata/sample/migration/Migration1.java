package com.cb.springdata.sample.migration;

import com.cb.springdata.sample.entities.Address;
import com.cb.springdata.sample.entities.User;
import com.cb.springdata.sample.repositories.UserRepository;
import com.cb.springdata.sample.service.UserService;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.N1qlParams;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.ParameterizedN1qlQuery;
import com.couchbase.client.java.query.consistency.ScanConsistency;
import com.github.liquicouch.changeset.ChangeLog;
import com.github.liquicouch.changeset.ChangeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * If you are using Spring, you can Autowire your Services or Repositories
 */
@Component
@ChangeLog(order = "001")
public class Migration1 {

    @Autowired // Yes, You can a
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @ChangeSet(order = "001", id = "someChangeId1", author = "testAuthor")
    public void importantWorkToDo(Bucket bucket){
        System.out.println("----------Migration1 - Method1");
    }

    @ChangeSet(order = "002", id = "someChangeId2", author = "testAuthor")
    public void method2(Bucket bucket){
        System.out.println("----------Migration1 - Method2");
    }

    @ChangeSet(order = "003", id = "someChangeId3", author = "testAuthor")
    public void method3(Bucket bucket){
        System.out.println("----------Migration1 - Method3");
    }

    @ChangeSet(order = "004", id = "someChangeId4", author = "testAuthor")
    public void method4(Bucket bucket){
        System.out.println("----------Migration1 - Method4");
    }

    @ChangeSet(order = "005", id = "someChangeId5", author = "testAuthor")
    public void method5(){
        System.out.println("----------Migration1 - Method5 (The bucket parameter is not necessary here)");
    }


    /**
     * Here is an example of how you can check if your update has run successfully, all you need to do is to
     * return a ParameterizedN1qlQuery.
     * @return
     */
    @ChangeSet(order = "006", id = "someChangeId6", author = "testAuthor", recounts = "2", retries = "1")
    public ParameterizedN1qlQuery method6(){

        //adding some data as an example
        userService.save(new User("someUserIdForTesting", "user1", new Address(), new ArrayList<>(), Arrays.asList("admin", "manager")));
        Iterable<User> users = userRepository.findAll(new PageRequest(0, 100));//we just care about the first 100 records

        users.forEach( e-> {
            //rename admin to adm
             if(e.getSecurityRoles().contains("admin")) {
                 e.getSecurityRoles().remove("admin");
                 e.getSecurityRoles().add("adm");
             }
            userRepository.save(e);
        });

        String queryString = "Select count(userRole)  as size from test t unnest t.securityRoles as userRole " +
                " where t._class='com.cb.springdata.sample.entities.User' " +
                " and userRole = 'admin'";
        N1qlParams params = N1qlParams.build().consistency(ScanConsistency.REQUEST_PLUS).adhoc(true);
        ParameterizedN1qlQuery query = N1qlQuery.parameterized(queryString, JsonObject.create(), params);
        return query;
    }

}
