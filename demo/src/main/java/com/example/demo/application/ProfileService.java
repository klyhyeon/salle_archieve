package com.example.demo.application;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Product;
import com.example.demo.mapper.ProfileMapper;

@Transactional
@Service
public class ProfileService implements ProfileMapper {
	
    @Autowired
    ProfileMapper profileMapper;

	@Override
	public List<Product> getSellerProductList(String email) {

		return profileMapper.getSellerProductList(email);
	}

	@Override
	public int getTotalProduct(String email) {

		return profileMapper.getTotalProduct(email);
	}
	

}
