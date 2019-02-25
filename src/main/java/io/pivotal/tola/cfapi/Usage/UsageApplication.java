package io.pivotal.tola.cfapi.Usage;

import org.cloudfoundry.reactor.ConnectionContext;
import org.cloudfoundry.reactor.DefaultConnectionContext;
import org.cloudfoundry.reactor.tokenprovider.PasswordGrantTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@SpringBootApplication
public class UsageApplication {

	private static final Logger LOG = LoggerFactory.getLogger(UsageApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(UsageApplication.class, args);
	}

	@Component
	class GetToken implements ApplicationRunner {

		private ConnectionContext connectionContext;

		private PasswordGrantTokenProvider tokenProvider;

		@Autowired
		public GetToken(PasswordGrantTokenProvider tokenProvider, ConnectionContext connectionContext) {
			this.tokenProvider = tokenProvider;
			this.connectionContext = connectionContext;
		}

		@Override
		public void run(ApplicationArguments args) throws Exception {

			Mono<String> token = tokenProvider.getToken(connectionContext);
			LOG.info("Token is {}", token.block());

		}

	}

	///////////////////////////////////////////////////////
	// CF Java API Bean definitions
	///////////////////////////////////////////////////////

	@Bean
	DefaultConnectionContext connectionContext(@Value("${cf.apiHost}") String apiHost) {
		return DefaultConnectionContext.builder().apiHost(apiHost).build();
	}

	@Bean
	PasswordGrantTokenProvider tokenProvider(@Value("${cf.username}") String username,
			@Value("${cf.password}") String password) {
		return PasswordGrantTokenProvider.builder().password(password).username(username).build();
	}

}
