package com.cb.springdata.sample.repositories;

import com.cb.springdata.sample.entities.User;
import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbasePagingAndSortingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "user")
public interface UserRepository extends CouchbasePagingAndSortingRepository<User, String> {

    List<User> findByName(String name);

    @Query("#{#n1ql.selectEntity} where #{#n1ql.filter} and  ANY preference IN " +
            " preferences SATISFIES preference.name = $1 END")
    List<User> findUsersByPreferenceName(String namxe);

    @Query("#{#n1ql.selectEntity} where #{#n1ql.filter} and meta().id = $1 and ARRAY_CONTAINS(securityRoles, $2)")
    User hasRole(String userId, String role);


}
