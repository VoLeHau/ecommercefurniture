package com.tma.vlhau.ecommercebackend.brand.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Brand not found")
public class BrandRestNotFoundException extends Exception {
}
