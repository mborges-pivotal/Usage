package io.pivotal.tola.cfapi.Usage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.cloudfoundry.reactor.DefaultConnectionContext;
import org.cloudfoundry.reactor.tokenprovider.PasswordGrantTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "usage")
@Data
public class FoundationsConfig {

	private static final Logger LOG = LoggerFactory.getLogger(FoundationsConfig.class);

	private List<Foundation> foundations = new ArrayList<>();
	
	private Map<String, FoundationConnection> foundationMap = new HashMap<String, FoundationConnection>();

	@PostConstruct
	public void init() {

	  for(Foundation f: foundations) {
		DefaultConnectionContext connectionContext = DefaultConnectionContext.builder().apiHost(f.apiHost).skipSslValidation(f.skipSslValidation).build();		  
		PasswordGrantTokenProvider tokenProvider = PasswordGrantTokenProvider.builder().password(f.password).username(f.username).build();
		FoundationConnection fc = new FoundationConnection(connectionContext, tokenProvider);
		foundationMap.put(f.name, fc);
		LOG.info("Creating foundation {}", f.name);
	  }
	}

	public List<String> getFoundationNames() {
		return new ArrayList<String>(foundationMap.keySet());
	}

	public String getFoundationToken(String name) {
		if (!foundationMap.containsKey(name)) {
			throw new Error(String.format("Foundation %s doesn't exist", name));
		}
		return foundationMap.get(name).getToken();
	}

	///////////////////////////////////

	@Data
	public static class Foundation {
		private String name;
		private String apiHost;
		private String username;
		private String password;
		private boolean skipSslValidation;

	}

}