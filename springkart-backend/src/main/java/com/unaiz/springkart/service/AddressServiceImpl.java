package com.unaiz.springkart.service;

import com.unaiz.springkart.dto.AddressDTO;
import com.unaiz.springkart.entity.Address;
import com.unaiz.springkart.entity.User;
import com.unaiz.springkart.exception.ResourceNotFoundException;
import com.unaiz.springkart.repository.AddressRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AddressServiceImpl implements AddressService {

    AddressRepository addressRepository;
    ModelMapper modelMapper;


    @Override
    @Transactional
    public AddressDTO createAddress(User user, AddressDTO addressDTO) {
        Address address = modelMapper.map(addressDTO, Address.class);

        List<Address> addressList = user.getAddresses();
        addressList.add(address);
        user.setAddresses(addressList);
        address.setUser(user);

        Address savedAddress = addressRepository.save(address);
        return modelMapper.map(savedAddress, AddressDTO.class);
    }

    @Override
    public AddressDTO getAddressById(Long id) {
        Address address = addressRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Address", "id", id));
        return modelMapper.map(address, AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getAddressByUser(User user) {

        if (user.getAddresses() != null && !user.getAddresses().isEmpty()) {
            return user.getAddresses().stream()
                    .map(address -> modelMapper.map(address, AddressDTO.class))
                    .toList();
        }
        throw new ResourceNotFoundException("Address", "userId", user.getUserId());
    }

    @Override
    public List<AddressDTO> getAllAddresses() {

        List<Address> addresses = addressRepository.findAll();
        return addresses.stream()
                .map(address -> modelMapper.map(address, AddressDTO.class))
                .toList();
    }

    @Override
    @Transactional
    public AddressDTO updateAddress(Long id, AddressDTO addressDTO) {
        Address address = addressRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Address", "id", id));

        address.setBuildingName(addressDTO.getBuildingName());
        address.setStreet(addressDTO.getStreet());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setCountry(addressDTO.getCountry());
        address.setPinCode(addressDTO.getPinCode());

        Address updatedAddress = addressRepository.save(address);

        return modelMapper.map(updatedAddress, AddressDTO.class);
    }

    @Override
    @Transactional
    public String deleteAddress(Long id) {
        Address address = addressRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Address", "id", id));
        addressRepository.delete(address);
        return "Address with id " + id + " deleted successfully.";
    }
}
