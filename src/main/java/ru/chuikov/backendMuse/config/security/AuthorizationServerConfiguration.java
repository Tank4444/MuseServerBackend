package ru.chuikov.backendMuse.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import ru.chuikov.backendMuse.service.MuseUserDetailService;

import javax.annotation.Resource;

@Configuration
@EnableAuthorizationServer
@PropertySource("classpath:/properties/auth.properties")
@ComponentScan("ru.chuikov.backendMuse.config.security")
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Resource
    private Environment env;
    
    //60 ms = 1 min
    //60 * 60 (3600) = 1 h
    //60 * 60 * 60 (86.400) = 1 day
    
    private static String REALM="REALM";

    private final MuseUserDetailService museUserDetailService;

    private final UserApprovalHandler userApprovalHandler;

    private final AuthenticationManager authenticationManager;

    private final TokenStore tokenStore;

    @Autowired
    public AuthorizationServerConfiguration(MuseUserDetailService museUserDetailService, UserApprovalHandler userApprovalHandler, @Qualifier("authenticationManagerBean") AuthenticationManager authenticationManager, TokenStore tokenStore) {
        this.museUserDetailService = museUserDetailService;
        this.userApprovalHandler = userApprovalHandler;
        this.authenticationManager = authenticationManager;
        this.tokenStore = tokenStore;
    }


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        clients.
                .withClient(env.getRequiredProperty("CLIEN_ID"))
                .secret(env.getRequiredProperty("CLIENT_SECRET"))
                .authorizedGrantTypes(
                        env.getRequiredProperty("GRANT_TYPE"),
                        env.getRequiredProperty("AUTHORIZATION_CODE"),
                        env.getRequiredProperty("REFRESH_TOKEN"),
                        env.getRequiredProperty("IMPLICIT")
                )
                .scopes(env.getRequiredProperty("SCOPE_READ"),
                        env.getRequiredProperty("SCOPE_WRITE"),
                        env.getRequiredProperty("TRUST"))
                .accessTokenValiditySeconds(Integer.valueOf(
                        env.getRequiredProperty("ACCESS_TOKEN_VALIDITY_SECONDS")))
                .refreshTokenValiditySeconds(Integer.valueOf(
                        env.getRequiredProperty("FREFRESH_TOKEN_VALIDITY_SECONDS")));
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore).userApprovalHandler(userApprovalHandler)
                .authenticationManager(authenticationManager)
                .userDetailsService(museUserDetailService);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.realm(REALM);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore)
                .accessTokenConverter(accessTokenConverter())
                .authenticationManager(authenticationManager);
    }

}
