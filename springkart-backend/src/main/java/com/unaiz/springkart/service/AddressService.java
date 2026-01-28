package com.unaiz.springkart.service;

import com.unaiz.springkart.dto.AddressDTO;
import com.unaiz.springkart.entity.User;
import jakarta.transaction.Transactional;

import java.util.List;

public interface AddressService {

    @Transactional
    AddressDTO createAddress(User user, AddressDTO addressDTO);

    AddressDTO getAddressById(Long id);

    List<AddressDTO> getAddressByUser(User user);

    List<AddressDTO> getAllAddresses();

    @Transactional
    AddressDTO updateAddress(Long id, AddressDTO addressDTO);

    @Transactional
    String deleteAddress(Long id);

}
