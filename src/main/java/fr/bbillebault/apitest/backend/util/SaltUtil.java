package fr.bbillebault.apitest.backend.util;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class SaltUtil {
	public String generateSalt() {
		Random random = new SecureRandom();
		StringBuilder saltBuilder = new StringBuilder();
		
		for(int i = 0; i < 32; i++) {
			saltBuilder.append("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(random.nextInt(62)));
		}
		
		return saltBuilder.toString();
	}
}
