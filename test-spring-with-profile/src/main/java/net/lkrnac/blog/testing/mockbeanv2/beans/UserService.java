package net.lkrnac.blog.testing.mockbeanv2.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	private AddressService addressService;

	@Autowired
	public UserService(AddressService addressService) {
		this.addressService = addressService;
	}
	
	public String getUserDetails(String userName){
		String address = addressService.getAddressForUser(userName);
		return String.format("User %s, %s", userName, address);
	}
}
