package net.lkrnac.blog.testing.mockbeanv2.aoptesting;

import org.mockito.Mockito;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import lombok.Getter;
import net.lkrnac.blog.testing.mockbeanv2.beans.AddressDao;

@Primary
@Repository
@Profile("AddressService-aop-mock-test")
public class AddressDaoMock extends AddressDao{
	@Getter
	private AddressDao mockDelegate = Mockito.mock(AddressDao.class);
	
	public String readAddress(String userName) {
		return mockDelegate.readAddress(userName);
	}
}
