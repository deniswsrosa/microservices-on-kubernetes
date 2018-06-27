package com.cb.springdata.sample;

import com.cb.springdata.sample.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleApplicationTests {

	@Autowired
	protected UserRepository userRepository;

//	@Before
//	public void deleteAll(){
//		userRepository.deleteAll();
//	}

	@Test
	public void contextLoads() {
	}

}
