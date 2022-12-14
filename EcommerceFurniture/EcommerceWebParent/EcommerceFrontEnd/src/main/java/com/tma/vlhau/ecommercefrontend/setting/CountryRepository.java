package com.tma.vlhau.ecommercefrontend.setting;

import com.tma.vlhau.ecommercecommon.entity.Country;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CountryRepository extends CrudRepository<Country, Integer> {

    List<Country> findAllByOrderByNameAsc();
    
    @Query("SELECT c FROM Country c WHERE c.code = ?1")
    Country findByCode(String code);
}
