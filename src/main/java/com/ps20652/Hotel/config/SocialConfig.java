// package com.ps20652.Hotel.config;
// import java.io.Serializable;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.social.UserIdSource;
// import org.springframework.social.config.annotation.EnableSocial;
// import org.springframework.social.config.annotation.SocialConfigurerAdapter;
// import org.springframework.social.connect.ConnectionFactoryLocator;
// import org.springframework.social.connect.UsersConnectionRepository;
// import org.springframework.social.connect.web.ProviderSignInUtils;
// import org.springframework.social.facebook.connect.FacebookConnectionFactory;

// @Configuration
// @EnableSocial
// public class SocialConfig  extends SocialConfigurerAdapter {

//     // Cấu hình Facebook Connection Factory
//     @Bean
//     public FacebookConnectionFactory facebookConnectionFactory() {
//         return new FacebookConnectionFactory("373228195428047", "05bb7b7fe7b75a28d3e43f9f42bc6015");
//     }

//     // Cấu hình Social Connection Factory Locator
//     @Bean
//     public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator, UsersConnectionRepository connectionRepository) {
//         return new ProviderSignInUtils(connectionFactoryLocator, connectionRepository);
//     }
//      @Override
//     public UserIdSource getUserIdSource() {
//         return new UserIdSource() {
//             @Override
//             public String getUserId() {
//                 // Triển khai logic để lấy userId tùy thuộc vào yêu cầu của bạn
//                 return "exampleUserId";
//             }
//         };
// }
// }

