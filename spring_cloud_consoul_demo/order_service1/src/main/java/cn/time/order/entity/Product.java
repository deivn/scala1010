package cn.time.order.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
public class Product {
    private Long id;

    private String productName;

    private Integer status;

    private BigDecimal price;

    private String productDesc;

    private String caption;

    private Integer inventory;
}
