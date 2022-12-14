package com.tma.vlhau.ecommercefrontend.shipping;

import com.tma.vlhau.ecommercecommon.entity.Address;
import com.tma.vlhau.ecommercecommon.entity.Customer;
import com.tma.vlhau.ecommercecommon.entity.ShippingRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShippingRateService {

    @Autowired
    private ShippingRateRepository shippingRateRepository;

    public ShippingRate getShippingRateForCustomer(Customer customer) {
        String state = customer.getState();

        if (state != null || !state.isEmpty()) {
            state = customer.getState();
        } else {
            return new ShippingRate();
        }

        return shippingRateRepository.findByCountryAndState(customer.getCountry(), state);
    }

    public ShippingRate getShippingRateForAddress(Address address) {
        String state = address.getState();

        if (state != null || state.isEmpty()) {
            state = address.getState();
        }
        System.out.println("Country: " + address.getCountry());
        System.out.println("State: " + state);

        return shippingRateRepository.findByCountryAndState(address.getCountry(), state);
    }

}
