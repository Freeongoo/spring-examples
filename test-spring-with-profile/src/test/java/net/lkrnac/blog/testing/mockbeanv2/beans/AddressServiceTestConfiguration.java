package net.lkrnac.blog.testing.mockbeanv2.beans;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("UserService-test")
@Configuration
public class AddressServiceTestConfiguration {
	@Bean
	@Primary
	public AddressService addressServiceSpy(AddressService addressService) {
		return Mockito.spy(addressService);
	}
}
