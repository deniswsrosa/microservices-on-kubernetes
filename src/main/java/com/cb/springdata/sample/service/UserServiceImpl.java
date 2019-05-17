package com.cb.springdata.sample.service;

import com.cb.springdata.sample.entities.Address;
import com.cb.springdata.sample.entities.User;
import com.cb.springdata.sample.repositories.UserRepository;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.N1qlParams;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.ParameterizedN1qlQuery;
import com.couchbase.client.java.query.consistency.ScanConsistency;
import com.couchbase.client.java.search.SearchQuery;
import com.couchbase.client.java.search.queries.DisjunctionQuery;
import com.couchbase.client.java.search.result.SearchQueryResult;
import com.couchbase.client.java.search.result.SearchQueryRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public User findById(String userId) {
        return userRepository.findById(userId).get();
    }

    @Override
    public User save(@Valid User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findUsersByPreferenceName(String name){
        return  userRepository.findUsersByPreferenceName(name);
    }

    @Override
    public boolean hasRole(String userId, String role) {
        return userRepository.hasRole(userId, role) != null;
    }

    //demo of how to run an ad hoc query
    @Override
    public List<User> findUserByAddress(String streetName, String number, String postalCode,
                                        String city, String country) {

        String bucketName = userRepository.getCouchbaseOperations().getCouchbaseBucket().bucketManager().info().name();

        String queryString = "SELECT meta(b).id as id, b.* FROM "+bucketName+" b WHERE  b._class = '"+User.class.getName() +"' ";

        if( streetName != null ){
            queryString += " and b.address.streetName = '"+streetName+"' ";
        }

        if(number != null){
            queryString += " and b.address.houseNumber = '"+number+"' ";
        }

        if(postalCode != null){
            queryString += " and b.address.postalCode = '"+postalCode+"' ";
        }

        if(city != null){
            queryString += " and b.address.city = '"+city+"' ";
        }

        if(country != null){
            queryString += " and b.address.country = '"+country+"' ";
        }

        N1qlParams params = N1qlParams.build().consistency(ScanConsistency.REQUEST_PLUS).adhoc(true);
        ParameterizedN1qlQuery query = N1qlQuery.parameterized(queryString, JsonObject.create(), params);
        return userRepository.getCouchbaseOperations().findByN1QLProjection(query, User.class);
    }


    @Override
    public List<User> searchByName(String name) {
        List<User> buildings = new ArrayList<>();
        DisjunctionQuery ftsQuery = SearchQuery.disjuncts(
                SearchQuery.wildcard(name).field("name").boost(.05), SearchQuery.wildcard(name).field("surname") );

        SearchQueryResult result = userRepository.getCouchbaseOperations().getCouchbaseBucket()
                .query(new SearchQuery("nameSearch", ftsQuery).limit(10).highlight());

        if (result != null && result.errors().isEmpty()) {
            Iterator<SearchQueryRow> resultIterator = result.iterator();
            while (resultIterator.hasNext()) {
                SearchQueryRow row = resultIterator.next();
                buildings.add(userRepository.findById(row.id()).get());
            }
        }
        return buildings;
    }

    @Override
    public List<User> populate() {
        Address address1 = new Address("street1", "1", "0000", "santo andre", "br");

        List<User> users = new ArrayList<>();
        for(int i =0; i < 100; i ++) {
            users.add( new User("user::"+i, "user"+i, "user"+i, address1, new ArrayList<>(), new ArrayList<>()));
        }

        users.forEach(e-> userRepository.save(e));
        return users;
    }
}
