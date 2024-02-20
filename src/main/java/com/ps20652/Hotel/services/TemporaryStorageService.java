package com.ps20652.Hotel.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ps20652.Hotel.DTO.CustomerDTO;

@Service
public class TemporaryStorageService {

    private Map<String, CustomerDTO> temporaryStorage = new HashMap<>();

    public void saveCustomerDTO(CustomerDTO customerDTO) {
        temporaryStorage.put(customerDTO.getEmail(), customerDTO);
    }

    public CustomerDTO getCustomerDTOByEmail(String email) {
        return temporaryStorage.get(email);
    }

    public void removeCustomerDTOByEmail(String email) {
        temporaryStorage.remove(email);
    }
}
