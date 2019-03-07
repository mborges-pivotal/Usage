package io.pivotal.tola.cfapi.Usage;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;


@SpringBootApplication
public class UsageApplication {

	private static final Logger LOG = LoggerFactory.getLogger(UsageApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(UsageApplication.class, args);
	}

	@Component
	class GetToken implements ApplicationRunner {

		@Autowired
		private FoundationsConfig config;

		@Override
		public void run(ApplicationArguments args) throws Exception {

			String[] foundations = (String[])config.getFoundationNames().toArray(new String[]{});
			LOG.info("Foundation list: {}", Arrays.toString(foundations));
			for(String name: foundations) {
				LOG.info("foundation {} token is: {}", name, config.getFoundationToken(name));
			}

		}

	}

}
