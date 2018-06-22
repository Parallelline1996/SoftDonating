package com.softdonating.dao;

import java.util.List;

import com.softdonating.domain.Donate;

public interface DonateDao {

	boolean createDonate(Donate data);
	
	List<Donate> getUnconfirmedList(Integer userId);
	
	boolean confirmDonate(Integer donateId, Integer number);
	
	Donate findFirstDonate(String isbn);
	
	boolean deleteDonate(Integer donateId);
	
	List<Donate> donateList(Integer userId);
	
	Donate findDonate(Integer userId, Integer bookId);
	
	Donate findDonateById(Integer donateId);
}
