package com.cb.springdata.sample;

import com.cb.springdata.sample.entities.Address;
import com.cb.springdata.sample.entities.Preference;
import com.cb.springdata.sample.entities.User;
import com.cb.springdata.sample.service.UserService;
import lombok.val;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class UserServiceIntegrationTest extends SampleApplicationTests {

    public static final String USER_1 = "user::1";
    public static final String COMPANY_2 = "company::2";

    @Autowired
    private UserService userService;

    @Test
    public void testSave() {
        val user = new User(USER_1, "someUser", new Address(), new ArrayList<>(), new ArrayList<>());
        userService.save(user);

        User newBuilding = userService.findById(USER_1);
        assertThat(newBuilding, equalTo(user));
    }

    @Test
    public void testSelectByPreference() {

        List<Preference> preferences =  Arrays.asList(new Preference("targetName","targetValue"));
        List<Preference> otherPreferences =  Arrays.asList(new Preference("someOtherName","someOtherValue"));

        userService.save(new User(USER_1, "user1", new Address(), preferences, new ArrayList<>()));
        userService.save(new User("user::2", "user2", new Address(), preferences, new ArrayList<>()));
        userService.save(new User("user::3", "user3", new Address(), otherPreferences, new ArrayList<>()));

        List<User> users = userService.findUsersByPreferenceName("targetName");

       assertThat(users, hasSize(2));
    }

    @Test
    public void testHasRole() {
        userService.save(new User(USER_1, "user1", new Address(), new ArrayList<>(), Arrays.asList("admin", "manager")));

        assertTrue(userService.hasRole(USER_1, "admin" ));
        assertFalse(userService.hasRole(USER_1, "user" ));
    }

    @Test
    public void testComposedAddress() {
        Address address1 = new Address("street1", "1", "0000", "santo andre", "br");
        Address address2 = new Address("street1", "2", "0000", "santo andre", "br");
        Address address3 = new Address("street2", "12", "1111", "munich", "de");

        userService.save(new User(USER_1, "user1", address1, new ArrayList<>(), new ArrayList<>()));
        userService.save(new User("user::2", "user2", address2, new ArrayList<>(), new ArrayList<>()));
        userService.save(new User("user::3", "user3", address3, new ArrayList<>(), new ArrayList<>()));

        List<User> users =  userService.findUserByAddress("street1", null, null, null, null);
        assertThat(users, hasSize(2));

        users =  userService.findUserByAddress("street1", "1", null, null, null);
        assertThat(users, hasSize(1));

        users =  userService.findUserByAddress(null, null, null, null, "de");
        assertThat(users, hasSize(1));
    }

    @Test
    public void testFullTextSearch() {

        Address address1 = new Address("street1", "1", "0000", "santo andre", "br");
        Address address2 = new Address("street1", "2", "0000", "santo andre", "br");
        Address address3 = new Address("street2", "12", "1111", "munich", "de");

        userService.save(new User(USER_1, "user1", address1, new ArrayList<>(), new ArrayList<>()));
        userService.save(new User("user::2", "user2", address2, new ArrayList<>(), new ArrayList<>()));
        userService.save(new User("user::22", "user22", address3, new ArrayList<>(), new ArrayList<>()));

//        List<User> users = userService.searchByName("user2*");
//
//        assertThat(users, hasSize(2));
    }
}
