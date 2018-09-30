package net.lkrnac.blog.testing.mockbeanv2.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
	private AddressDao addressDao;
	
	@Autowired
	public AddressService(AddressDao addressDao) {
		this.addressDao = addressDao;
	}
	
	public String getAddressForUser(String userName){
		return addressDao.readAddress(userName);
	}
}
