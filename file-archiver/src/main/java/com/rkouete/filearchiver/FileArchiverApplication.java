package com.rkouete.filearchiver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.rkouete.filearchiver"})
public class FileArchiverApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileArchiverApplication.class, args);
	}

}
