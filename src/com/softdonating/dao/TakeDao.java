package com.softdonating.dao;

import com.softdonating.domain.Take;

public interface TakeDao {

	boolean createTake(Take take, Integer donateId);
}
