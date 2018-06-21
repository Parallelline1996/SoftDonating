package com.softdonating.serviceImpl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.softdonating.service.NormalService;

@Service
@Qualifier("normalServiceImpl")
public class NormalServiceImpl implements NormalService {

}
