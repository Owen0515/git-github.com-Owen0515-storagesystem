package com.work.storagesystem.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.work.storagesystem.dto.product.ProductUpdateDTO;
import com.work.storagesystem.exception.BadRequestException;
import com.work.storagesystem.exception.EntityNotFoundException;
import com.work.storagesystem.model.Product;
import com.work.storagesystem.model.User;
import com.work.storagesystem.repository.ProductRepository;
import com.work.storagesystem.service.AuthService;
import com.work.storagesystem.service.ProductService;
import com.work.storagesystem.service.RecordService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private RecordService recordService;
    @Autowired
    private AuthService authService;
    
    /**
     * 新增商品
     */
    public Product createProduct(Product product,HttpServletRequest req) {
    	User user = authService.findUserByRequest(req);
    	Product dbProduct = productRepository.save(product);
    	recordService.createRecord(dbProduct,"CREATE",dbProduct.getQuantity(),user.getUsername());
        return dbProduct;
    }

    /**
     * 查詢所有商品
     */
    public Page<Product> getAllProducts(String productName, BigDecimal minPrice, BigDecimal maxPrice, Integer minQuantity, Integer maxQuantity,Boolean deleted, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Specification<Product> spec = Specification.where(null);

        if (productName != null && !productName.isBlank()) {
            spec = spec.and((root, query, criteriaBuilder) -> 
                criteriaBuilder.like(root.get("productName"), "%" + productName + "%")
            );
        }
        if (minPrice != null) {
            spec = spec.and((root, query, criteriaBuilder) -> 
                criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice)
            );
        }
        if (maxPrice != null) {
            spec = spec.and((root, query, criteriaBuilder) -> 
                criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice)
            );
        }
        if (minQuantity != null) {
            spec = spec.and((root, query, criteriaBuilder) -> 
                criteriaBuilder.greaterThanOrEqualTo(root.get("quantity"), minQuantity)
            );
        }
        if (maxQuantity != null) {
            spec = spec.and((root, query, criteriaBuilder) -> 
                criteriaBuilder.lessThanOrEqualTo(root.get("quantity"), maxQuantity)
            );
        }
        if (deleted != null) {
            spec = spec.and((root, query, criteriaBuilder) -> 
                criteriaBuilder.equal(root.get("deleted"), deleted)
            );
        }
        return productRepository.findAll(spec, pageable);
    }

    /**
     * 查詢單一商品
     */
    public Product getProductById(Long id) {
    	Product product = productRepository.findById(id)
    			.orElseThrow(() -> new EntityNotFoundException("找不到對應產品"));
        return product;
    }

    /**
     * 更新商品
     */
    public Product updateProduct(Long id, ProductUpdateDTO productUpdateDTO,HttpServletRequest req) {
    	User user = authService.findUserByRequest(req);
    	Product product = getProductById(id);
    	if(product == null) {
    		throw new EntityNotFoundException("產品 ID 不存在");
    	}
        if (productUpdateDTO.getProductName() != null) {
            product.setProductName(productUpdateDTO.getProductName());
        }
        if (productUpdateDTO.getProductDescription() != null) {
            product.setProductDescription(productUpdateDTO.getProductDescription());
        }
        if (productUpdateDTO.getPrice() != null) {
            product.setPrice(productUpdateDTO.getPrice());
        }
        if (productUpdateDTO.getQuantity() != null) {
            product.setQuantity(productUpdateDTO.getQuantity());
        }
        recordService.createRecord(product,"UPDATE",0,user.getUsername());
        return productRepository.save(product);
    }
    
    /**
     * 更新商品數量
     */
    public Product updateProductQuantity(Long id, String operationType, Integer quantity,HttpServletRequest req) {
    	User user = authService.findUserByRequest(req);
        Product product = getProductById(id);
        if (quantity <= 0) {
            throw new BadRequestException("無效的操作");
        }
        // 數量增加
        if ("INCREASE".equalsIgnoreCase(operationType)) {
            product.setQuantity(product.getQuantity() + quantity);
        // 數量減少
        } else if ("DECREASE".equalsIgnoreCase(operationType)) {
            if (product.getQuantity() < quantity) {
                throw new BadRequestException("庫存不足");
            }
            product.setQuantity(product.getQuantity() - quantity);
        } else {
            throw new BadRequestException("無效的操作類型: " + operationType);
        }
        Product updatedProduct = productRepository.save(product);
        recordService.createRecord(product,operationType.toUpperCase(),quantity,user.getUsername());
        return updatedProduct;
    }

    /**
     * 刪除商品(軟性)
     */
    public void deleteProduct(Long id,HttpServletRequest req) {
    	User user = authService.findUserByRequest(req);
        Product product = getProductById(id);
    	if(product == null) {
    		throw new EntityNotFoundException("產品 ID 不存在");
    	}
        product.setDeleted(true);
        productRepository.save(product);
        recordService.createRecord(product,"DELETE",product.getQuantity(),user.getUsername());
    }
}

