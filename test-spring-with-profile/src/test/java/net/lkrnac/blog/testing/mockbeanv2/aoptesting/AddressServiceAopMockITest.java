package net.lkrnac.blog.testing.mockbeanv2.aoptesting;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.lkrnac.blog.testing.mockbeanv2.AopApplication;
import net.lkrnac.blog.testing.mockbeanv2.beans.AddressDao;
import net.lkrnac.blog.testing.mockbeanv2.beans.AddressService;

@ActiveProfiles({"AddressService-aop-mock-test", "aop"})
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AopApplication.class)
public class AddressServiceAopMockITest {
	@Autowired
	private AddressService addressService; 

	@Autowired
	private AddressDao addressDao;
	
	@Test
	public void testGetAddressForUser() {
		// GIVEN
		AddressDaoMock addressDaoMock = (AddressDaoMock) addressDao;
		Mockito.when(addressDaoMock.getMockDelegate().readAddress("john"))
			.thenReturn("5 Bright Corner");
 
		// WHEN 
		String actualAddress = addressService.getAddressForUser("john");
 
		// THEN  
		Assert.assertEquals("5 Bright Corner", actualAddress);
	}
}
