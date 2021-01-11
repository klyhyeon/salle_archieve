package com.example.demo.controller;

import java.io.File;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.demo.application.ProductService;
import com.example.demo.domain.Login;
import com.example.demo.domain.Member;
import com.example.demo.domain.Product;
import com.example.demo.domain.UuidImgname;
import com.example.demo.validation.SellProductValidation;

@Controller
public class SellProductController {
	
	@Autowired
    ProductService productService;
	
	@Autowired
	UuidImgname uuidImgname;

	@RequestMapping(value = "/sell/register", method = RequestMethod.GET)
    public String sellAttempt(Model model) {

    	model.addAttribute("product", new Product());	
        return "sell/register";
    }
    
    @RequestMapping(value = "/sell/done", method = RequestMethod.POST)
    public String sellHandle(@ModelAttribute("product") Product product, Errors errors,
    		HttpSession httpSession) throws Exception {
    	
    	//파일 업로드 작업 수행(destination에 파일을 보내줌)

    	Login login = (Login) httpSession.getAttribute("login");
    	product.setPr_email(login.getEmail());
    	
    	product.setPr_title_alias(product.getPr_title().replaceAll("\s", ""));
    	
		//ajax로 받은 img_file 정보를 넘겨줌 
		product.setPr_img_1(product_file.getPr_img_1());
		product.setPr_img_2(product_file.getPr_img_2());
		product.setPr_img_3(product_file.getPr_img_3());
		product.setPr_img_4(product_file.getPr_img_4());
		product.setPr_img_5(product_file.getPr_img_5());
    	
		new SellProductValidation().validate(product, errors);
		
		if (errors.hasErrors()) {
			return "sell/register";
		}
		

		productService.registerProduct(product);

    	return "home";
    }
    
    //상품등록 이미지파일 업로드
    Product product_file = new Product();
    
    @Value("${file.upload.path}")
    String fileUploadPath; 

    @RequestMapping(value= "/sell/ajax", method= RequestMethod.POST)
    public void ajax(HttpServletRequest req) throws Exception {
    	    	
    	//formdata를 받은 req를 multipartfile로 타입 변환해줌
    	MultipartHttpServletRequest multi = (MultipartHttpServletRequest) req;
    	Iterator<String> iterator = multi.getFileNames(); 	
    	MultipartFile multipartFile = null;
    	
    	int reps = 0;
    	
    	while(iterator.hasNext()) {
    		
    		multipartFile = multi.getFile(iterator.next());
    		
    		
    		String fileOriname = multipartFile.getOriginalFilename();
    		String filename = uuidImgname.makeFilename(fileOriname);
    		String filepathSave = fileUploadPath + File.separator + filename;
    		String filepath = "/resources/img/imgUpload/" + filename;
    		
    		multipartFile.transferTo(new File(filepathSave));

    		switch(reps) {
    		case 0: 
    			product_file.setPr_img_1(filepath);
    			break;
    		case 1: 
    			product_file.setPr_img_2(filepath);
    			break;
    		case 2: 
    			product_file.setPr_img_3(filepath);
    			break;
    		case 3: 
    			product_file.setPr_img_4(filepath);
    			break;
    		case 4: 
    			product_file.setPr_img_5(filepath);
    			break;
    		}
    		
    		reps++;
    	}
    }
    
    //주소검색 API URL 연동
	@RequestMapping(value = "/sell/region", method = RequestMethod.GET)
    public String sellRegionGet() {

        return "sell/region";
    }


	
    
    

}
