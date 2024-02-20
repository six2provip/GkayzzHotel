package com.ps20652.Hotel.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

import com.ps20652.Hotel.dao.AccountDAO;
import com.ps20652.Hotel.dao.CustomerDAO;
import com.ps20652.Hotel.entity.Account;
import com.ps20652.Hotel.services.AccountService;
import com.ps20652.Hotel.services.CustomOAuth2UserService;
import com.ps20652.Hotel.services.EmailService;
import com.ps20652.Hotel.services.impl.CustomOAuth2User;
import com.ps20652.Hotel.services.impl.FacebookConnectionSignup;
import com.ps20652.Hotel.services.impl.FacebookSignInAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private FacebookConnectionSignup facebookConnectionSignup;
    @Autowired
    private CustomOAuth2UserService oauth2UserService;
    @Autowired
    private FacebookConnectionSignup userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private AccountDAO accountDAO;
    

 
    // @Value("${spring.social.facebook.appSecret}")
    // String appSecret;
    
    // @Value("${spring.social.facebook.appId}")
    // String appId;
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SpringSecurityDialect springSecurityDialect(){
        return new SpringSecurityDialect();
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER) // Tiết lộ bean AuthenticationManager
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    // @Override
    // protected void configure(HttpSecurity http) throws Exception {
    //     http.authorizeRequests()
    //         .antMatchers("/", "/login").permitAll()
    //         .anyRequest().authenticated()
    //         .and()
     
    //         .formLogin()
    //     .loginPage("/login")
    //     .defaultSuccessUrl("/?success=true")
    //     .failureUrl("/login?success=false") // Đường dẫn khi đăng nhập không thành công
    //     .permitAll()
    //     .and()
    //                    .logout()    
    //                     .logoutUrl("/logout")
    //                     .logoutSuccessUrl("/?logout=true")
    //                    .and()
    //                 .exceptionHandling()
    //                     .accessDeniedPage("/access-denied") // Chuyển hướng đến trang access-denied khi bị cấm truy cập (403)
    //         .and()
    //         .oauth2Login()
    //             .loginPage("/login")
    //             .userInfoEndpoint()
    //                 .userService(oauth2UserService)
    //                 .and()
               
                // .successHandler(new AuthenticationSuccessHandler() {
                //     @Override
                //     public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                //             Authentication authentication) throws IOException, ServletException {
            
                //         CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
            
                //         userService.processOAuthPostLogin(oauthUser.getEmail());
            
                //         response.sendRedirect("/");
    //                 }
    //             })
    //             .and()
    //             .csrf().disable(); 
    // }
    

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/admin/accounts", "/admin/revenue-chart", "/admin/history").hasRole("ADMIN")
            .antMatchers("/admin/products", "/admin/feedback", "/admin/orders", "/admin/vouchers", "/admin/category").hasAnyRole("ADMIN", "STAFF")
            .antMatchers("/cart", "/pay").authenticated() // Các URL yêu cầu xác thực
            .anyRequest().permitAll() // Các URL còn lại cho phép truy cập không yêu cầu xác thực
            .and()
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/?success=true")
                .failureUrl("/login?success=false") // Đường dẫn khi đăng nhập không thành công
                .permitAll()
            .and()
            .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                    .userService(oauth2UserService)
                .and()
                .successHandler(new AuthenticationSuccessHandler() {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();

        // Thực hiện xử lý đăng nhập thành công ở đây

        userService.processOAuthPostLogin(oauthUser.getEmail());

        Account account = accountDAO.findByUsername(oauthUser.getEmail());

        userService.processOAuthPostLogincreate(oauthUser.getFirstName(), oauthUser.getLastName(), oauthUser.getEmail(), "0", account);



        // Tạo danh sách quyền cho người dùng (ở đây là ROLE_CUSTOMER)
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + account.getRole().getRoleName()));

        // Tạo UserDetails với thông tin từ OAuth2 và danh sách quyền
        UserDetails userDetails = new User(oauthUser.getEmail(), "", authorities);

        // Tạo Authentication mới với UserDetails và các thông tin khác
        UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(
                userDetails, authentication.getCredentials(), authorities);

        // Đặt Authentication mới vào SecurityContext


        SecurityContextHolder.getContext().setAuthentication(newAuth);


        // Chuyển hướng sau khi đăng nhập thành công
        response.sendRedirect("/?success=true");
    }
})

            .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/?logout=true")
            .and()
            .exceptionHandling()
                .accessDeniedPage("/access-denied") // Chuyển hướng đến trang access-denied khi bị cấm truy cập (403)
            .and()
            .csrf().disable();
    }
    

//   @Bean
//     public ProviderSignInController providerSignInController() {
//         ConnectionFactoryLocator connectionFactoryLocator = 
//             connectionFactoryLocator();
//         UsersConnectionRepository usersConnectionRepository = 
//             getUsersConnectionRepository(connectionFactoryLocator);
//         ((InMemoryUsersConnectionRepository) usersConnectionRepository)
//             .setConnectionSignUp(facebookConnectionSignup);
//         return new ProviderSignInController(connectionFactoryLocator, 
//             usersConnectionRepository, new FacebookSignInAdapter());
//     }
    
//     private ConnectionFactoryLocator connectionFactoryLocator() {
//         ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
//         registry.addConnectionFactory(new FacebookConnectionFactory(appId, appSecret));
//         return registry;
//     }
    
//     private UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator 
//         connectionFactoryLocator) {
//         return new InMemoryUsersConnectionRepository(connectionFactoryLocator);
//     }

    

}
