package com.softdonating.dao;

import java.util.List;

import com.softdonating.domain.Donate;

public interface DonateDao {

	boolean createDonate(Donate data);
	
	List<Donate> getUnconfirmedList(Integer userId);
	
	boolean confirmDonate(Integer donateId);
	
	Donate findFirstDonate(String isbn);
	
	boolean deleteDonate(Integer donateId);
}
