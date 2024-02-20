package com.ps20652.Hotel.RestController;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.ps20652.Hotel.DTO.CustomerDTO;
import com.ps20652.Hotel.DTO.OtpVerificationRequest;
import com.ps20652.Hotel.dao.CustomerDAO;
import com.ps20652.Hotel.dao.RoleDAO;
import com.ps20652.Hotel.entity.Account;
import com.ps20652.Hotel.entity.Customer;
import com.ps20652.Hotel.entity.Role;
import com.ps20652.Hotel.services.AccountService;
import com.ps20652.Hotel.services.CustomerService;
import com.ps20652.Hotel.services.EmailService;
import com.ps20652.Hotel.services.RoleService;
import com.ps20652.Hotel.services.TemporaryStorageService;

@RestController
@RequestMapping("/api")
public class CustomerRest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerDAO customerdao;

    @Autowired
    private RoleService roleService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TemporaryStorageService temporaryStorageService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/getCustomer")
    public ResponseEntity<List<Customer>> getCustomers() {
        List<Customer> customers = customerService.findAll();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    // @PostMapping("/customer/create")
    // public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
    // try {
    // Customer createdCustomer = customerService.create(customer);
    // return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    // } catch (Exception e) {
    // return new ResponseEntity<>("Failed to create customer.",
    // HttpStatus.INTERNAL_SERVER_ERROR);
    // }
    // }

    @PostMapping("/customer/send-otp")
    public ResponseEntity<?> sendOtpToEmail(@RequestBody CustomerDTO customerDTO) {
        try {

            if (customerdao.existsByEmail(customerDTO.getEmail())) {
                return new ResponseEntity<>("Email already exists. OTP not sent.", HttpStatus.BAD_REQUEST);
            }

            // Generate and send OTP via email
            String otp = emailService.generateOtp();
            emailService.sendOtpEmail(customerDTO.getEmail(), otp);

            // Lưu thông tin vào CustomerDTO
            customerDTO.setOtp(otp);

            // Lưu thông tin và OTP vào một cơ sở dữ liệu tạm thời hoặc cache (nếu cần)
            temporaryStorageService.saveCustomerDTO(customerDTO);

            return new ResponseEntity<>("OTP sent successfully.", HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>("Failed to send OTP.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Mapping to verify OTP and create the customer account
    @PostMapping("/customer/verify-otp")
    public ResponseEntity<?> verifyOtpAndCreateAccount(@RequestBody OtpVerificationRequest otpRequest,
            Authentication authentication) {

                
        try {
            // Lấy thông tin và OTP từ cơ sở dữ liệu tạm thời hoặc cache
            CustomerDTO customerDTO = temporaryStorageService.getCustomerDTOByEmail(otpRequest.getEmail());

            if (customerDTO != null && customerDTO.getOtp().equals(otpRequest.getOtp())) {
                // Xác thực OTP thành công, lấy accountId từ cookie
                if (authentication != null && authentication.isAuthenticated()) {
                    String username = authentication.getName();

                    // Lấy accountId của người dùng đã đăng nhập
                    Long accountId = accountService.findAccountIdByUsername(username);

                    Account account = accountService.findbyId(accountId);

                    Customer createdCustomer = customerService.createCustomerFromDTO(customerDTO);

                    // Gán tài khoản với accountId cho trường account của Customer
                    createdCustomer.setAccount(account);

                    // Lưu Customer vào cơ sở dữ liệu
                    customerService.create(createdCustomer);

                    // Xóa thông tin và OTP từ cơ sở dữ liệu tạm thời hoặc cache
                    temporaryStorageService.removeCustomerDTOByEmail(customerDTO.getEmail());

                    Account acc = accountService.findbyId(accountId);

                    acc.setIsAuthenticated(true);

                    Integer id = 1;
                    Long ID = id.longValue();

                    Role role = roleService.findbyId(ID);

                    acc.setRole(role);

                    accountService.update(acc);

                    emailService.sendAccountLinkedEmail(createdCustomer.getEmail());
                   
                    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                    System.out.println("getCredentials: " + auth.getCredentials());
                    System.out.println("ROLE:" + auth.getAuthorities());
                    Authentication updatedAuthentication = new UsernamePasswordAuthenticationToken(
                    auth.getPrincipal(), auth.getCredentials(), acc.getAuthorities(role.getRoleName()));
                    SecurityContextHolder.getContext().setAuthentication(updatedAuthentication);
                    System.out.println("ROLE:" + updatedAuthentication.getAuthorities());
                    
                    

                    return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);

                } else {
                    return null;
                }

                // Tạo tài khoản Customer

            } else {
                return new ResponseEntity<>("Invalid OTP. Please try again.", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>("Tạo lỗi", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    


    
    // private void autoLogin(String username, String password) {
    //     Authentication auth = authenticationManager.authenticate(
    //             new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList()));
    //     SecurityContextHolder.getContext().setAuthentication(auth);
    // }
    
    
    

    @GetMapping("/getCustomer/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.findbyId(id);
        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/customer/{id}")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer, @PathVariable("id") Long id) {
        try {
            Customer updatedCustomer = customerService.update(customer);
            if (updatedCustomer != null) {
                return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Customer not found.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update customer.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/customer/checkEmail")
    public ResponseEntity<String> checkEmailAvailability(@RequestParam String email) {
        boolean isEmailAvailable = customerdao.existsByEmail(email);
        return ResponseEntity.ok(Boolean.toString(isEmailAvailable));
    }

}
