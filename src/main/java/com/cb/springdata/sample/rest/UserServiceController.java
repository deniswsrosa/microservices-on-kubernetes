package com.cb.springdata.sample.rest;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cb.springdata.sample.entities.User;
import com.cb.springdata.sample.service.UserService;

import javax.websocket.server.PathParam;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/user")
public class UserServiceController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/{id}", method = GET, produces = APPLICATION_JSON_VALUE)
    public User findById(@PathParam("id") String id) {
        return userService.findById(id);
    }

    @RequestMapping(value = "/preference", method = GET, produces = APPLICATION_JSON_VALUE)
    public List<User> findPreference(@RequestParam("name") String name) {
        return userService.findUsersByPreferenceName(name);
    }

    @RequestMapping(value = "/find", method = GET, produces = APPLICATION_JSON_VALUE)
    public List<User> findUserByName(@RequestParam("name") String name) {
        return userService.findByName(name);
    }

    @RequestMapping(value = "/save", method = POST, produces = APPLICATION_JSON_VALUE)
    public User findUserByName(@RequestBody User user) {
        return userService.save(user);
    }


    @RequestMapping(value = "/populate", method = GET, produces = APPLICATION_JSON_VALUE)
    public List<User> populateDatabase() {
        return userService.populate();
    }

}
