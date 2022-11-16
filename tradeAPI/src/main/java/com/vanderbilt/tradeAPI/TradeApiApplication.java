package com.vanderbilt.tradeAPI;

import com.github.messenger4j.Messenger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TradeApiApplication {

	//Instantiating messenger bean
	@Bean
	public Messenger messenger(@Value("${MESSENGER_PAGE_ACCESS_TOKEN}") String pageAccessToken,
							   @Value("${MESSENGER_APP_SECRET}") String appSecret,
							   @Value("${MESSENGER_VERIFY_TOKEN}") String verifyToken){
		return Messenger.create(pageAccessToken, appSecret, verifyToken);
	}

	public static void main(String[] args) {
		SpringApplication.run(TradeApiApplication.class, args);
	}

}
