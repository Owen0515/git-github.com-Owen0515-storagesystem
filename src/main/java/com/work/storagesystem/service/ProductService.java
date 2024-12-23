package com.work.storagesystem.service;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.work.storagesystem.dto.product.ProductUpdateDTO;
import com.work.storagesystem.model.Product;

import jakarta.servlet.http.HttpServletRequest;

@Service
public interface ProductService {
	
	/**
	 * 新增商品
	 */
	Product createProduct(Product product,HttpServletRequest req);
	
	/**
	 * 查詢所有商品
	 */
	Page<Product> getAllProducts(String productName
								,BigDecimal minPrice
								,BigDecimal maxPrice
								,Integer minQuantity
								,Integer maxQuantity
								,Boolean deleted
								,int page
								,int size);
	
	/**
	 * 查詢單一商品
	 */
	Product getProductById(Long id);
	
	/**
	 * 更新商品
	 */
	Product updateProduct(Long id, ProductUpdateDTO productUpdateDTO,HttpServletRequest req);
	
	/**
	 * 更新商品數量
	 */
	Product updateProductQuantity(Long id, String operationType, Integer quantity,HttpServletRequest req);
	
	/**
	 * 刪除商品
	 */
	void deleteProduct(Long id,HttpServletRequest req);
	
}
