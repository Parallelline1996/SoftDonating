package com.softdonating.dao;

import java.util.List;

import com.softdonating.domain.Take;

public interface TakeDao {

	boolean createTake(Take take, Integer donateId);
	
	Integer numberOfTake(Integer userId);
	
	Integer numberOfDonate(Integer userId);
	
	List<Take> takeList(Integer userId);
	
	List<Take> donateList(Integer userId);
}
