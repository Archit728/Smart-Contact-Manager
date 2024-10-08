package com.scm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.scm.services.EmailService;

@SpringBootTest
class ScmApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private EmailService service;
	@Test
	void sendEmailTest(){
		service.sendEmail("archit.bhatnagar31@gmail.com", "testing email service", "scm project email service");
	}

}
