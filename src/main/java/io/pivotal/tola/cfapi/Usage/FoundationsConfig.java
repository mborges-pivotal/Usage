package io.pivotal.tola.cfapi.Usage;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Component
@ConfigurationProperties(prefix = "foundations")
@Data
public class FoundationsConfig {

	private static final Logger LOG = LoggerFactory.getLogger(FoundationsConfig.class);

	private List<String> names;
	private Map<String, String> apiHost;
	private Map<String, String> username;
	private Map<String, String> password;

	private Map<String, Foundation> foundations;

	public List<String> getFoundations() {
		return Collections.unmodifiableList(names);
	}

	public Foundation getFoundation(String name) {
		if (foundations == null) {
			buildFoundations();
		}
		return foundations.get(name);
	}

	///////////////////////////////////

	private void buildFoundations() {
		foundations = new HashMap<String, Foundation>();
		for (String name : names) {
			LOG.info("Adding '{}' foundation...", name);
			Foundation f = Foundation.builder().name(name).apiHost(apiHost.get(name)).username(username.get(name)).password(password.get(name)).build();	
			foundations.put(name, f);
			LOG.debug("added foudation {}", f);
		}
	}

	@Data
	@Builder
	public static class Foundation {
		private String name;
		private String apiHost;
		private String username;
		@ToString.Exclude private String password;

	}
}