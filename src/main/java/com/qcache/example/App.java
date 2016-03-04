package com.qcache.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.qcache.example.db.model.User;
import com.qcache.example.service.UserService;

/**
 * @author guofeng.qin
 */
@SpringBootApplication
public class App {

	private static ConfigurableApplicationContext ctx;

	public static void main(String[] args) throws Exception {
		ctx = SpringApplication.run(App.class, args);

		UserService userService = ctx.getBean(UserService.class);

		LogicThread.getExecutor().execute(() -> {
			userService.addUser(new User(1, "testemail", "testname"));

			userService.getUser(1, (User value) -> {
				System.out.println(value.getEmail());
			});
		});
		Thread.sleep(10 * 60 * 1000L);
		User u = userService.getUser2(1);
		System.out.println(u.getEmail());
	}

	public static ConfigurableApplicationContext getCtx() {
		return ctx;
	}
}
