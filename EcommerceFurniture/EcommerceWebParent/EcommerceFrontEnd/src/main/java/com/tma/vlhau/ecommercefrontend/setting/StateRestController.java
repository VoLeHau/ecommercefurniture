package com.tma.vlhau.ecommercefrontend.setting;

import com.tma.vlhau.ecommercecommon.entity.Country;
import com.tma.vlhau.ecommercecommon.entity.State;
import com.tma.vlhau.ecommercecommon.entity.StateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StateRestController {

    @Autowired
    private StateRepository stateRepository;

    @GetMapping("/settings/list_states_by_country/{id}")
    public List<StateDTO> listByCountry(@PathVariable("id") Integer countryId) {
        List<State> listStates = stateRepository.findByCountryOrderByNameAsc(new Country(countryId));

        List<StateDTO> result = new ArrayList<>();

        for  (State state : listStates) {
            result.add(new StateDTO(state.getId(), state.getName()));
        }
        return result;
    }

}
