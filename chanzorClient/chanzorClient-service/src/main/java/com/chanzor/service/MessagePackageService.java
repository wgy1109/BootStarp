package com.chanzor.service;

import java.util.List;

import com.chanzor.entity.MessagePackage;

public interface MessagePackageService {
	List<MessagePackage> findMessagePackageByType(Integer type) throws Exception;

	MessagePackage selMessagePackageById(Integer id) throws Exception;
}
