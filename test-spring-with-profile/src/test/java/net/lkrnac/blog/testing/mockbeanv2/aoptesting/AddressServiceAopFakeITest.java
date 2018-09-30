package net.lkrnac.blog.testing.mockbeanv2.aoptesting;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.lkrnac.blog.testing.mockbeanv2.AopApplication;
import net.lkrnac.blog.testing.mockbeanv2.beans.AddressService;

@ActiveProfiles({"AddressService-aop-fake-test", "aop"})
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AopApplication.class)
public class AddressServiceAopFakeITest {
	@Autowired
	private AddressService addressService; 

	@Test
	public void testGetAddressForUser() {
		// GIVEN - Spring context
 
		// WHEN 
		String actualAddress = addressService.getAddressForUser("john");
 
		// THEN  
		Assert.assertEquals("john's address", actualAddress);
	}
}
