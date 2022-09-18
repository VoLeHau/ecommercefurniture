package com.tma.vlhau.ecommercebackend.setting.state;

import com.tma.vlhau.ecommercecommon.entity.Country;
import com.tma.vlhau.ecommercecommon.entity.State;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StateRepository extends CrudRepository<State, Integer> {

    List<State> findByCountryOrderByNameAsc(Country country);

}
