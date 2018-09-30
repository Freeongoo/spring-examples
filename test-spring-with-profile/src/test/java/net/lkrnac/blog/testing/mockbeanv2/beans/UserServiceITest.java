package net.lkrnac.blog.testing.mockbeanv2.beans;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.lkrnac.blog.testing.mockbeanv2.SimpleApplication;

@ActiveProfiles("UserService-test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SimpleApplication.class)
public class UserServiceITest {
	@Autowired
	private UserService userService;

	@Autowired
	private AddressService addressService;
 
	@Test
	public void testGetUserDetails() {
		// GIVEN - Spring scanned by SimpleApplication class

		// WHEN
		String actualUserDetails = userService.getUserDetails("john");
 
		// THEN
		Assert.assertEquals("User john, 3 Dark Corner", actualUserDetails);
		Mockito.verify(addressService).getAddressForUser("john");
	}
} 
 