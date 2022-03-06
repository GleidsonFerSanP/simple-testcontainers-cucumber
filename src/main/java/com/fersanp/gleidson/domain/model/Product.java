package com.fersanp.gleidson.domain.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Product {

    private UUID id;
    private String name;
    private String description;
}
