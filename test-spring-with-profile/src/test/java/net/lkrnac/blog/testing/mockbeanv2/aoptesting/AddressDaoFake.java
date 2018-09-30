package net.lkrnac.blog.testing.mockbeanv2.aoptesting;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import net.lkrnac.blog.testing.mockbeanv2.beans.AddressDao;

@Primary
@Repository
@Profile("AddressService-aop-fake-test")
public class AddressDaoFake extends AddressDao{
	public String readAddress(String userName) {
		return userName + "'s address";
	}
}
