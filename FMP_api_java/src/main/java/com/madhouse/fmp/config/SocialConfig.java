package com.madhouse.fmp.config;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import com.madhouse.fmp.signin.SimpleSignInAdapter;

/**
 * Spring Social Configuration.
 * This configuration demonstrates the use of the simplified Spring Social configuration options from Spring Social 1.1.
 * 
 * @author Craig Walls
 */
@Configuration
@EnableSocial

public class SocialConfig implements SocialConfigurer{
	@Inject
	private DataSource dataSource;
	//
	// SocialConfigurer implementation methods
	//
	// 该回调方法用来允许应用添加需要支持的社交网络对应的连接工厂的实现
	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment env) {
		cfConfig.addConnectionFactory(new FacebookConnectionFactory(env.getProperty("facebook.appKey"), env.getProperty("facebook.appSecret")));
	}
	
	// getUserIdSource方法的实现中通过 Spring Security 来获取当前登录用户的信息
	@Override
	public UserIdSource getUserIdSource() {
		return new UserIdSource() {			
			@Override
			public String getUserId() {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				if (authentication == null) {
					throw new IllegalStateException("Unable to get a ConnectionRepository: no user signed in");
				}
				return authentication.getName();
			}
		};
	}
	
	// getUsersConnectionRepository方法中创建了一个基于数据库的 JdbcUsersConnectionRepository 类的实现对象
	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		return new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
	}
	
	//
	// API Binding Beans
	//
	
	@Bean
	@Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)
	public Facebook facebook(ConnectionRepository repository) {
		Connection<Facebook> connection = repository.findPrimaryConnection(Facebook.class);
		return connection != null ? connection.getApi() : null;
	}
	
	//
	// Web Controller and Filter Beans
	//
	@Bean
	public ProviderSignInController providerSignInController(ConnectionFactoryLocator connectionFactoryLocator, UsersConnectionRepository usersConnectionRepository) {
		return new ProviderSignInController(connectionFactoryLocator, usersConnectionRepository, new SimpleSignInAdapter(new HttpSessionRequestCache()));
	}
}
