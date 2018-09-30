package net.lkrnac.blog.testing.mockbeanv2.beans;

import org.springframework.stereotype.Repository;

@Repository
public class AddressDao {
	public String readAddress(String userName) {
		return "3 Dark Corner";
	}
}
