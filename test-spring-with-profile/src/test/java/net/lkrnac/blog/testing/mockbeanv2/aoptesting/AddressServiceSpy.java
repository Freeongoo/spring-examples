package net.lkrnac.blog.testing.mockbeanv2.aoptesting;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import lombok.Getter;
import net.lkrnac.blog.testing.mockbeanv2.beans.AddressDao;
import net.lkrnac.blog.testing.mockbeanv2.beans.AddressService;

@Primary
@Service
@Profile("UserService-aop-test")
//@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AddressServiceSpy extends AddressService{
	@Getter
	private AddressService spyDelegate;
	
	@Autowired
	public AddressServiceSpy(AddressDao addressDao) {
		super(null);
		spyDelegate = Mockito.spy(new AddressService(addressDao));
	}
	
	public String getAddressForUser(String userName){
		return spyDelegate.getAddressForUser(userName);
	}
}
