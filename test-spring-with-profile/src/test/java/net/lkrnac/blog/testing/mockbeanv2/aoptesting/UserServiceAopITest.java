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
import net.lkrnac.blog.testing.mockbeanv2.beans.AddressService;
import net.lkrnac.blog.testing.mockbeanv2.beans.UserService;

@ActiveProfiles({"UserService-aop-test", "aop"})
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AopApplication.class)
public class UserServiceAopITest {
	@Autowired
	private UserService userService;

	@Autowired
	private AddressService addressService;
	
	@Test
	public void testGetUserDetails() {
		// GIVEN
		AddressServiceSpy addressServiceSpy = (AddressServiceSpy) addressService;

		// WHEN
		String actualUserDetails = userService.getUserDetails("john");
  
		// THEN 
		Assert.assertEquals("User john, 3 Dark Corner", actualUserDetails);
		Mockito.verify(addressServiceSpy.getSpyDelegate()).getAddressForUser("john");
	}
}
 