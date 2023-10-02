package com.tuan.blogs.service.implement;

import com.tuan.blogs.model.user.Address;
import com.tuan.blogs.repository.AddressRepository;
import com.tuan.blogs.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImplement implements AddressService {

    @Autowired
    private AddressRepository addressRepo;

    @Override
    public Address createAddress(Address address) {
        addressRepo.save(address);
        return address;
    }
}
