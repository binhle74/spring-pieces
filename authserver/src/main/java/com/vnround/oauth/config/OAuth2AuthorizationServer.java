package com.vnround.oauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServer extends AuthorizationServerConfigurerAdapter {
	private static final String CLIENT_ID = "binhle";
	private static final String CLIENT_SECRET = "binhle";
	private static final String GRANT_TYPE_PASSWORD = "password";
	private static final String AUTHORIZATION_CODE = "authorization_code";
	private static final String REFRESH_TOKEN = "refresh_token";
	private static final String SCOPE_READ = "read";
	private static final String SCOPE_WRITE = "write";
	private static final String TRUST = "trust";
	private static final String IMPLICIT = "implicit";
	private static final int ACCESS_TOKEN_VALIDITY_SECONDS = 1*60*60;
	private static final int REFRESH_TOKEN_VALIDITY_SECONDS = 6*60*60;
	private static final String REDIRECT_URI = "http://localhost:9000/login";
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()")
				.checkTokenAccess("isAuthenticated()")
				.allowFormAuthenticationForClients();
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
			.withClient(CLIENT_ID)
			.secret(passwordEncoder.encode(CLIENT_SECRET))
			.authorizedGrantTypes(GRANT_TYPE_PASSWORD, AUTHORIZATION_CODE, REFRESH_TOKEN, IMPLICIT)
			.scopes(SCOPE_READ, SCOPE_WRITE, TRUST)
			.authorities("READ_ONLY_CLIENT")
			.accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)
			.refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_SECONDS)
			.redirectUris(REDIRECT_URI)
			.resourceIds("resource_id", "resource_server");
		
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
		.tokenStore(tokenStore())
		.authenticationManager(this.authenticationManager)
		.accessTokenConverter(accessTokenConverter());
	}
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey("as466g");
		return converter;
	}
	
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}
}
