package com.ps20652.Hotel.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ps20652.Hotel.dao.AccountDAO;
import com.ps20652.Hotel.entity.Account;

import com.ps20652.Hotel.services.AccountService;

@RestController
@RequestMapping("/api")
public class AccountRest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountDAO accountDAO;

    

    @GetMapping("/getAccount")
    public ResponseEntity<List<Account>> getAccounts() {
        List<Account> accounts = accountService.findAll();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @PostMapping("/account/create")
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
        try {
            Account createdAccount = accountService.create(account);
            return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create account.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAccount/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        Account account = accountService.findbyId(id);
        if (account != null) {
            return new ResponseEntity<>(account, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/account/{id}")
    public ResponseEntity<?> updateCustomer(@RequestBody Account account, @PathVariable("id") Long id) {
        try {
            Account updatedAccount = accountService.update(account);
            if (updatedAccount != null) {
                return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Customer not found.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update customer.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//     @PostMapping("/account/login")
// public ResponseEntity<?> login(@RequestBody Account account, HttpServletResponse response) {
//     try {
//         // Thực hiện logic kiểm tra đăng nhập ở đây
//         boolean loginSuccessful = accountService.checkLogin(account);

//         if (loginSuccessful) {
//             // Nếu đăng nhập thành công, lưu thông tin tài khoản vào cookie
//             // Lưu ý: Tránh lưu mật khẩu vào cookie vì có thể không an toàn
//             // Dưới đây, tôi chỉ lưu username vào cookie để minh họa

//             Long accountId = accountService.findAccountIdByUsername(account.getUsername());
//             String accountIdString = String.valueOf(accountId);

//             Cookie accountIdCookie = new Cookie("accountIdCookie", accountIdString);
//             accountIdCookie.setMaxAge(7 * 24 * 60 * 60); // Thời gian sống của cookie (7 ngày)
//             accountIdCookie.setPath("/"); // Đặt đường dẫn của cookie

//             response.addCookie(accountIdCookie);

//             // Trả về một đối tượng JSON để thông báo là đăng nhập thành công
//             Map<String, String> responseMap = new HashMap<>();
//             responseMap.put("message", "Login successful.");
//             return new ResponseEntity<>(responseMap, HttpStatus.OK);
//         } else {
//             // Nếu đăng nhập thất bại, trả về thông báo lỗi
//             return new ResponseEntity<>("Invalid login credentials.", HttpStatus.UNAUTHORIZED);
//         }
//     } catch (Exception e) {
//         return new ResponseEntity<>("Failed to perform login.", HttpStatus.INTERNAL_SERVER_ERROR);
//     }
// }




    @PostMapping("/account/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        try {
            // Xóa cookie accountIdCookie bằng cách tạo một cookie mới với cùng tên và đặt MaxAge của nó thành 0
            Cookie accountIdCookie = new Cookie("accountIdCookie", null);
            accountIdCookie.setMaxAge(0);
            accountIdCookie.setPath("/");

            response.addCookie(accountIdCookie);

            // Ở đây, bạn có thể thực hiện các thao tác đăng xuất bổ sung như xóa phiên hoặc vô hiệu hóa mã xác thực

            return new ResponseEntity<>("Đăng xuất thành công.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Không thể thực hiện đăng xuất.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/account/checkUsername")
    public ResponseEntity<String> checkUsernameAvailability(@RequestParam String username) {
        boolean isUsernameAvailable = accountDAO.existsByUsername(username);
        return ResponseEntity.ok(Boolean.toString(isUsernameAvailable));
    }

}
