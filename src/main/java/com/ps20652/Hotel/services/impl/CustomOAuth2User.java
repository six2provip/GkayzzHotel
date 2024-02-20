package com.ps20652.Hotel.services.impl;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2User implements OAuth2User {
 
    private OAuth2User oauth2User;
     
    public CustomOAuth2User(OAuth2User oauth2User) {
        this.oauth2User = oauth2User;
    }
 
    @Override
    public Map<String, Object> getAttributes() {
        return oauth2User.getAttributes();
    }
 
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oauth2User.getAuthorities();
    }
 
    @Override
    public String getName() {  
        return oauth2User.getAttribute("name");
    }
 
    public String getEmail() {
        return oauth2User.<String>getAttribute("email");     
    }

    public String getFirstName() {
        String fullName = oauth2User.getAttribute("name");
        if (fullName != null && !fullName.isEmpty()) {
            // Tách tên thành mảng các phần bằng khoảng trắng
            String[] names = fullName.split("\\s+");
            
            if (names.length > 1) {
                return names[0]; // Trả về first_name
            }
        }
        // Nếu không thể tách hoặc không có tên, trả về giá trị đầy đủ
        return fullName;
    }

    public String getLastName() {
        String fullName = oauth2User.getAttribute("name");
        if (fullName != null && !fullName.isEmpty()) {
            // Tách tên thành mảng các phần bằng khoảng trắng
            String[] names = fullName.split("\\s+");
            
            if (names.length > 1) {
                // Nếu có ít nhất hai phần, trả về phần cuối cùng làm last_name
                return names[names.length - 1];
            } else if (names.length == 1) {
                // Nếu chỉ có một phần, trả về giá trị này làm last_name
                return names[0];
            }
        }
        // Nếu không thể tách hoặc không có tên, trả về giá trị rỗng cho last_name
        return "";
    }
    
    
    
}