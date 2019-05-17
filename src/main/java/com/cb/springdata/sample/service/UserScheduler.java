package com.cb.springdata.sample.service;

import com.cb.springdata.sample.entities.Address;
import com.cb.springdata.sample.entities.Preference;
import com.cb.springdata.sample.entities.User;
import com.github.javafaker.Faker;
import org.apache.commons.lang3.LocaleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class UserScheduler {

    @Autowired
    private UserService userService;

    @Scheduled(fixedRate = 10000)
    public void insertUsers() {
        for(int i=0; i < 10; i++) {
            userService.save(generateRandomUser());
        }
    }


    private User generateRandomUser() {
        Faker faker = new Faker(LocaleUtils.toLocale(getRandom("en", "es", "pt_BR", "de")));
        User user = new User();
        user.setName(faker.name().fullName());
        user.setUsername(faker.name().username());
        user.setId(UUID.randomUUID().toString());

        user.setPreferences(getRandomPreferences());

        user.setAddress(Address.builder()
                .city(faker.address().city())
                .country(faker.address().country())
                .houseNumber(faker.address().streetAddressNumber())
                .streetName(faker.address().streetName())
                .postalCode(faker.address().zipCode())
                .build()
        );

        user.setSecurityRoles(Arrays.asList(getRandom("ADMIN", "SUPERVISOR", "USER", "PRO_USER" )));

        return user;
    }


    private List<Preference> getRandomPreferences() {
        List<Preference> preferences = new ArrayList<>();
        preferences.add(new Preference("lang", getRandom("en", "es", "pt_BR", "de")));
        preferences.add(new Preference("notification", getRandom("none", "email", "email_alert")));
        return preferences;
    }



    private String getRandom(String ... args) {
        return args [ThreadLocalRandom.current().nextInt(0, args.length )];
    }
}
