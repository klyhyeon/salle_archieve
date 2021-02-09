package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.Member;
import com.example.demo.domain.Product;

@Mapper
public interface ProductMapper {
	
	void registerProduct(Product product);
	
	int getCountProduct();

	void deleteProduct(int pr_id);
	
	void updateProduct(Product product);
	
	//void deleteImg(int pr_id, String pr_img);
	
	void deleteImg1(int pr_id);
	void deleteImg2(int pr_id);
	void deleteImg3(int pr_id);
	void deleteImg4(int pr_id);
	void deleteImg5(int pr_id);

	
	//ProductList를 가져옴
	List<Product> getProductList();

	//Category별 ProductList를 가져옴
	List<Product> getCategoryProductList(String pr_category);

	//Product id를 받아서 모든 product 정보를 가져옴
	Product getProductInfo(int pr_id);
	
	//Product, Member table 조합해서 모든 정보를 가져옴
	String getMemberProductInfo(String email);
	
	//검색어 결과 출력
	List<Product> search(String searchWord, String searchWordNoSpace);
	
	//검색어 결과 유무
	int searchCount(String searchWord, String searchWordNoSpace);

	
}
