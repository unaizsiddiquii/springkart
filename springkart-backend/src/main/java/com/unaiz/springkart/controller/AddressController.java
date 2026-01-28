package com.unaiz.springkart.controller;

import com.unaiz.springkart.dto.AddressDTO;
import com.unaiz.springkart.entity.User;
import com.unaiz.springkart.service.AddressService;
import com.unaiz.springkart.util.AuthUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final AuthUtil authUtil;

    @PostMapping("/addresses")
    ResponseEntity<AddressDTO> createAddress(@RequestBody @Valid AddressDTO addressDTO) {
        User user = authUtil.loggedInUser();
        return new ResponseEntity<>(addressService.createAddress(user, addressDTO), HttpStatus.CREATED);
    }

    @GetMapping("/addresses/{id}")
    ResponseEntity<AddressDTO> getAddressById(@PathVariable Long id) {
        return new ResponseEntity<>(addressService.getAddressById(id), HttpStatus.FOUND);
    }

    @GetMapping("/addresses/users/addresses")
    ResponseEntity<List<AddressDTO>> getAddressByUser() {

        User user = authUtil.loggedInUser();
        return new ResponseEntity<>(addressService.getAddressByUser(user), HttpStatus.FOUND);
    }

    @GetMapping("/addresses")
    ResponseEntity<List<AddressDTO>> getAllAAddresses() {
        return new ResponseEntity<>(addressService.getAllAddresses(), HttpStatus.OK);
    }

    @PutMapping("/addresses/{id}")
    ResponseEntity<AddressDTO> updateAddress(@PathVariable Long id, @RequestBody @Valid AddressDTO addressDTO) {
        return new ResponseEntity<>(addressService.updateAddress(id, addressDTO), HttpStatus.OK);
    }

    @DeleteMapping("/addresses/{id}")
    ResponseEntity<String> updateAddress(@PathVariable Long id) {
        return new ResponseEntity<>(addressService.deleteAddress(id), HttpStatus.OK);
    }
}
