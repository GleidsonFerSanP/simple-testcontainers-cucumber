package com.fersanp.gleidson.domain.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class Order {
    private UUID id;
    private LocalDateTime date;
    private List<Product> products;
}
