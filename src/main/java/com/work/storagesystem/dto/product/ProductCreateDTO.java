package com.work.storagesystem.dto.product;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductCreateDTO {

    @NotBlank(message = "請輸入產品名稱")
    private String productName;

    @Size(max = 500, message = "產品描述請勿超過500個字")
    private String productDescription;

    @NotNull(message = "請輸入產品價格")
    @DecimalMin(value = "0.0", inclusive = false, message = "產品價格必須大於1")
    private BigDecimal price;

    @NotNull(message = "請輸入產品數量")
    @Min(value = 0, message = "產品數量必須大於1")
    private Integer quantity;
    
}

