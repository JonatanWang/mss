package se.cygni.mss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import se.cygni.mss.tsv.FileProcessor;

@SpringBootApplication
public class MovieSearchServicesApplication {

	public static void main(String[] args) {

		FileProcessor.download();
		FileProcessor.unzip();
		SpringApplication.run(MovieSearchServicesApplication.class, args);
	}
}
