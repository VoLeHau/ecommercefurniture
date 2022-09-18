package com.tma.vlhau.ecommercefrontend.shipping;

import com.tma.vlhau.ecommercecommon.entity.Country;
import com.tma.vlhau.ecommercecommon.entity.ShippingRate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ShippingRateRepository extends CrudRepository<ShippingRate, Integer> {

    @Query("SELECT s FROM ShippingRate s WHERE s.country=:country AND s.state LIKE %:state%")
    ShippingRate findByCountryAndState(Country country, String state);

}
