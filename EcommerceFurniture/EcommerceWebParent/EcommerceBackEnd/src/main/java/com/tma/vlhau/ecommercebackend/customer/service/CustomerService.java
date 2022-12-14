package com.tma.vlhau.ecommercebackend.customer.service;

import com.tma.vlhau.ecommercebackend.customer.repository.CustomerRepository;
import com.tma.vlhau.ecommercebackend.setting.country.CountryRepository;
import com.tma.vlhau.ecommercecommon.entity.Country;
import com.tma.vlhau.ecommercecommon.entity.Customer;
import com.tma.vlhau.ecommercecommon.exception.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class CustomerService {

    public static final int CUSTOMERS_PER_PAGE = 10;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Page<Customer> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);

        if (sortDir.equals("asc")) {
            sort = sort.ascending();
        } else {
            sort = sort.descending();
        }

        Pageable pageable = PageRequest.of(pageNum - 1, CUSTOMERS_PER_PAGE, sort);

        if (keyword != null && !keyword.isEmpty()) {
            return customerRepository.findAll(keyword, pageable);
        }

        return customerRepository.findAll(pageable);
    }

    public void save(Customer customerInForm) {
        Customer customerInDB = customerRepository.findById(customerInForm.getId()).get();
        if (!(customerInForm.getPassword() == null)) {
            String encodedPassword = passwordEncoder.encode(customerInForm.getPassword());
            customerInForm.setPassword(encodedPassword);
        } else {
            customerInForm.setPassword(customerInDB.getPassword());
        }
        
        /* Th??m m???i*/
        customerInForm.setEnabled(customerInDB.isEnabled());
        customerInForm.setCreateTime(customerInDB.getCreateTime());
        customerInForm.setVerificationCode(customerInDB.getVerificationCode());
        customerInForm.setAuthenticationType(customerInDB.getAuthenticationType());
        customerInForm.setResetPasswordToken(customerInDB.getResetPasswordToken());

        customerRepository.save(customerInForm);
    }

    public Customer get(Integer id) throws CustomerNotFoundException {
        try {
            return customerRepository.findById(id).get();
        } catch (NoSuchElementException exception) {
            throw new CustomerNotFoundException("Could not find any customer with id = " + id);
        }
    }

    public List<Country> listAllCountries() {
        return countryRepository.findAllByOrderByNameAsc();
    }

    public boolean isEmailUnique(Integer id, String email) {
        Customer existCustomer = customerRepository.findByEmail(email);

        if (existCustomer != null && existCustomer.getId() != id) {
            return false;
        }

        return true;
    }

    public void delete(Integer id) throws CustomerNotFoundException {
        Long count = customerRepository.countById(id);

        if (count == null || count == 0)
            throw new CustomerNotFoundException("Could not find any customers with id = " + id);

        customerRepository.deleteById(id);
    }

    @Transactional
    public void updateCustomerEnabledStatus(Integer id, boolean enabled) {
        customerRepository.updateEnabledStatus(id, enabled);
    }

    public List<Customer> listALl() {
        return customerRepository.findAll();
    }


}
