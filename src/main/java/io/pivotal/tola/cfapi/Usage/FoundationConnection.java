package io.pivotal.tola.cfapi.Usage;

import org.cloudfoundry.reactor.ConnectionContext;
import org.cloudfoundry.reactor.tokenprovider.PasswordGrantTokenProvider;

import reactor.core.publisher.Mono;

public class FoundationConnection {

    private ConnectionContext connectionContext;
    private PasswordGrantTokenProvider tokenProvider;

    public FoundationConnection(ConnectionContext connectionContext, PasswordGrantTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
        this.connectionContext = connectionContext;
    } 

    public String getToken() {
        Mono<String> token = tokenProvider.getToken(connectionContext);
        return token.block();
    }


}