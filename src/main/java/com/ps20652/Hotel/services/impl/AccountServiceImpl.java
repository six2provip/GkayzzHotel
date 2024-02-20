package com.ps20652.Hotel.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ps20652.Hotel.dao.AccountDAO;

import com.ps20652.Hotel.entity.Account;
import com.ps20652.Hotel.entity.Provider;
import com.ps20652.Hotel.services.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountDAO accountDAO;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public List<Account> findAll() {
		return accountDAO.findAll();
	}

	@Override
	public Account create(Account account) {
		// Kiểm tra xem username đã tồn tại chưa
		if (accountDAO.existsByUsername(account.getUsername())) {
			// Username đã tồn tại, có thể xử lý thông báo lỗi hoặc ném exception ở đây
			throw new RuntimeException("Username already exists.");
			// Hoặc có thể return null hoặc một giá trị đặc biệt để chỉ ra tạo tài khoản
			// không thành công
			// return null;
		} else {
			// Nếu username không tồn tại, thì tiến hành kiểm tra và lưu tài khoản
			if (account.getPassword().length() < 9) {
				// Nếu mật khẩu có ít hơn 8 ký tự, ném ra một ngoại lệ hoặc xử lý thông báo lỗi
				throw new RuntimeException("Password must be at least 9 characters long.");
			}

			// Mã hóa mật khẩu và lưu tài khoản
			String rawPassword = account.getPassword();

			String encodedPassword = passwordEncoder.encode(rawPassword);
			account.setPassword(encodedPassword);
			account.setProvider(Provider.LOCAL);
			

			return accountDAO.save(account);

			

		}
	}

	@Override
	public Account findbyId(Long id) {
		return accountDAO.findByAccountId(id);
	}

	@Override
	public Account update(Account account) {
		return accountDAO.save(account);
	}

	@Override
	public boolean checkLogin(Account account) {
		// Lấy thông tin tài khoản từ cơ sở dữ liệu bằng username
		Account storedAccount = accountDAO.findByUsername(account.getUsername());

		// Kiểm tra xem tài khoản có tồn tại không và mật khẩu khớp không
		if (storedAccount != null) {
			// Giải mã hóa mật khẩu từ cơ sở dữ liệu và so sánh
			return passwordEncoder.matches(account.getPassword(), storedAccount.getPassword());
		} else {
			return false;
		}
	}

	@Override
	public Long findAccountIdByUsername(String username) {
		// Gọi đến phương thức của accountDAO để lấy thông tin tài khoản
		Account account = accountDAO.findByUsername(username);

		// Kiểm tra nếu tài khoản tồn tại và trả về accountId
		return (account != null) ? account.getAccountId() : null;
	}

}