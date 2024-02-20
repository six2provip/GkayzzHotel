package com.ps20652.Hotel.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Service;

import com.ps20652.Hotel.dao.AccountDAO;
import com.ps20652.Hotel.dao.CustomerDAO;
import com.ps20652.Hotel.entity.Account;
import com.ps20652.Hotel.entity.Customer;
import com.ps20652.Hotel.entity.Provider;
import com.ps20652.Hotel.entity.Role;
import com.ps20652.Hotel.services.CustomerService;
import com.ps20652.Hotel.services.EmailService;
import com.ps20652.Hotel.services.RoleService;

@Service
public class FacebookConnectionSignup implements ConnectionSignUp {

    @Autowired
    public AccountDAO userRepository;
    // @Autowired
    // public PasswordEncoder passwordEncoder;
    @Autowired
    public RoleService roleService;
    @Autowired
    public CustomerService customerService;
    @Autowired
    public CustomerDAO customerDAO;
    @Autowired
    public EmailService emailService;

    @Override
    public String execute(Connection<?> connection) {
        // Kiểm tra xem username đã tồn tại hay chưa
        String email = connection.getDisplayName();
        if (userRepository.findByUsername(email) != null) {
            return email;
        }

        // Nếu username chưa tồn tại, tạo tài khoản mới
        Account user = new Account();
        user.setUsername(email);
        String rawPassword = "123456789";
        user.setPassword(rawPassword);
        user.setProvider(Provider.FACEBOOK);

        userRepository.save(user);
        return user.getUsername();
    }

    public void processOAuthPostLogin(String username) {
        Account existUser = userRepository.findByUsername(username);

        if (existUser == null) {
            Account newUser = new Account();
            newUser.setUsername(username);
            newUser.setPassword("security");
            newUser.setProvider(Provider.FACEBOOK);
            Integer id = 1;
            Long ID = id.longValue();
            Role role = roleService.findbyId(ID);
            newUser.setRole(role);
            newUser.setIsAuthenticated(true);
            userRepository.save(newUser);
            

        }

    }

    public void processOAuthPostLogincreate(String firstName, String lastName, String email, String phone, Account account) {
        boolean existEmail = customerDAO.existsByEmail(email);

        if(!existEmail){

            Customer newCustomer = new Customer();

            newCustomer.setAccount(account);
            newCustomer.setEmail(email);
            newCustomer.setFirstName(firstName);
            newCustomer.setLastName(lastName);
            newCustomer.setPhone(phone);

            customerDAO.save(newCustomer);

            emailService.sendAccountLinkedEmail(email);

        }

    }

}