package com.qcache.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.qcache.example.db.model.User;
import com.qcache.example.service.UserService;

/**
 * @author guofeng.qin
 */
@SpringBootApplication
public class App implements CommandLineRunner {

	@Autowired
	private UserService userService;

	@Override
	public void run(String... args) throws Exception {
		LogicThread.getExecutor().execute(() -> {
			userService.addUser(new User(1, "testemail", "testname"));
			
			userService.getUser(1, (User value) -> {
				System.out.println(value.getEmail());
			});
		});
	}
}
