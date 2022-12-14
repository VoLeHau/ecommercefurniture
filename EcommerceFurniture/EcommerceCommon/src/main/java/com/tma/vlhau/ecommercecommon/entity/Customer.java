package com.tma.vlhau.ecommercecommon.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "customer")
public class Customer extends IdBasedEntity {


    @Column(length = 45, nullable = false, unique = true)
    private String email;

    @Column(length = 64, nullable = false)
    private String password;

    @Column(length = 45, nullable = false, name = "first_name")
    private String firstName;

    @Column(length = 45, nullable = false, name = "last_name")
    private String lastName;

    @Column(length = 15, nullable = false, name = "phone_number")
    private String phoneNumber;

    @Column(length = 64, nullable = false)
    private String addressLine1;

    @Column(length = 64, name = "address_line2")
    private String addressLine2;

    @Column(nullable = false, length = 45)
    private String city;

    @Column(nullable = false, length = 45)
    private String state;

    @Column(name = "postal_code", nullable = false, length = 10)
    private String postalCode;

    @Column(name = "verification_code", length = 64)
    private String verificationCode;

    private boolean enabled;

    @Column(name = "created_time")
    private Date createTime;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @Enumerated(EnumType.STRING)
    @Column(name= "authentication_type", length = 10)
    private AuthenticationType authenticationType;

    @Column(name = "reset_password_token", length = 30)
    private String resetPasswordToken;

    public String getFullName() {
        return firstName + " " + lastName;
    }

	public Customer(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", email=" + email + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", phoneNumber=" + phoneNumber + ", addressLine1=" + addressLine1
				+ ", addressLine2=" + addressLine2 + ", city=" + city + ", state=" + state + ", postalCode="
				+ postalCode + ", verificationCode=" + verificationCode + ", enabled=" + enabled + ", createTime="
				+ createTime + ", country=" + country + ", authenticationType=" + authenticationType + "]";
	}
	
	@Transient
	public String getAddress() {
		String address =firstName;
		if(lastName != null && !lastName.isEmpty()) {
			address += " " + lastName;
		}
		
		if(!addressLine1.isEmpty()) {
			address += ", " + addressLine1;
		}
		
		if(addressLine2 != null && !addressLine2.isEmpty()) {
			address += ", " + addressLine2;
		}
		
		
		address +=  ", " + country.getName();
		
	
		if(!postalCode.isEmpty()) {
			address += ". Postal Code : " + postalCode;
		}
		
		
		if(!phoneNumber.isEmpty()) {
			address += ". Phone Number : " + phoneNumber;
		}
		
		
		return address;
	}

    

}
