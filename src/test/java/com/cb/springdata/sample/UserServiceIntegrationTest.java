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


}
