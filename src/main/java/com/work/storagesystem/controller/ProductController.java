package com.work.storagesystem.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.work.storagesystem.dto.product.ProductCreateDTO;
import com.work.storagesystem.dto.product.ProductUpdateDTO;
import com.work.storagesystem.model.Product;
import com.work.storagesystem.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 新增商品
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductCreateDTO productCreateDTO,HttpServletRequest req) {
        Product product = new Product();
        product.setProductName(productCreateDTO.getProductName());
        product.setProductDescription(productCreateDTO.getProductDescription());
        product.setPrice(productCreateDTO.getPrice());
        product.setQuantity(productCreateDTO.getQuantity());
        product.setDeleted(false);
        Product createdProduct = productService.createProduct(product,req);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    /**
     * 查詢所有商品
     */
    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Integer minQuantity,
            @RequestParam(required = false) Integer maxQuantity,
            @RequestParam(required = false) Boolean deleted,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Product> products = productService.getAllProducts(productName, minPrice, maxPrice, minQuantity, maxQuantity,deleted,page,size);
        return ResponseEntity.ok(products);
    }

    /**
     * 查詢單一商品
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    /**
     * 更新商品內容
     */
    @PutMapping("/{id}/admin")
    public ResponseEntity<Product> updateProductAsAdmin(@PathVariable Long id, @Valid @RequestBody ProductUpdateDTO productUpdateDTO,HttpServletRequest req) {
        Product updatedProduct = productService.updateProduct(id, productUpdateDTO,req);
        return ResponseEntity.ok(updatedProduct);
    }
    
    /**
     * 更新商品數量
     */
    @PutMapping("/{id}/quantity")
    public ResponseEntity<Product> updateProductQuantity(
            @PathVariable Long id,
            @RequestParam String operationType,
            @RequestParam Integer quantity,
            HttpServletRequest req) {
        Product updatedProduct = productService.updateProductQuantity(id, operationType, quantity,req);
        return ResponseEntity.ok(updatedProduct);
    }

    /**
     * 刪除商品(軟性)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id,HttpServletRequest req) {
        productService.deleteProduct(id,req); 
        return ResponseEntity.noContent().build();
    }
}
