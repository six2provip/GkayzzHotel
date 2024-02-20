// package com.ps20652.Hotel.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.social.connect.ConnectionFactory;
// import org.springframework.social.connect.ConnectionFactoryLocator;
// import org.springframework.social.connect.UsersConnectionRepository;
// import org.springframework.social.facebook.connect.FacebookConnectionFactory;
// import org.springframework.social.oauth2.OAuth2Operations;
// import org.springframework.social.oauth2.OAuth2Parameters;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.GetMapping;
// import javax.servlet.http.HttpServletRequest;

// @Controller
// public class SocialController {

//     @Autowired
//     ConnectionFactoryLocator connectionFactoryLocator;
//     @Autowired
//     UsersConnectionRepository connectionRepository;

    
//      @GetMapping("/login/facebook")
//     public String loginWithFacebook(HttpServletRequest request) {
//         String redirectUrl = getRedirectUrl(request, "facebook");
//         OAuth2Operations oAuth2Operations = getOAuthOperations("facebook");

//         OAuth2Parameters parameters = new OAuth2Parameters();
//         parameters.setRedirectUri(redirectUrl);
//         parameters.setScope("public_profile,email");

//         return "redirect:" + oAuth2Operations.buildAuthorizeUrl(parameters);
//     }

//     private OAuth2Operations getOAuthOperations(String providerId) {
//         ConnectionFactory<?> connectionFactory = connectionFactoryLocator.getConnectionFactory(providerId);
//         if (connectionFactory instanceof FacebookConnectionFactory) {
//             return ((FacebookConnectionFactory) connectionFactory).getOAuthOperations();
//         }
//         // Handle other providers as needed
//         return null;
//     }

//     private String getRedirectUrl(HttpServletRequest request, String providerId) {
//         return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/login/" + providerId + "/callback";
//     }
// }
