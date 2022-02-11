package com.example.pizzamaker.service.impl;

import com.example.pizzamaker.model.ProductType;
import com.example.pizzamaker.repository.ProductTypeRepository;
import com.example.pizzamaker.service.ProductTypeService;

import java.util.List;

public class ProductTypeServiceImpl implements ProductTypeService {
    private final ProductTypeRepository productTypeRepository =new ProductTypeRepository();

    @Override
    public ProductType read(int id) {
        return productTypeRepository.read(id);
    }

    @Override
    public List<ProductType> readAll() {
        return productTypeRepository.readAll();
    }


    @Override
    public void create(ProductType productType) {

    }

    @Override
    public ProductType update(int id, ProductType table) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public ProductType read(String name) {
        return productTypeRepository.read(name);
    }
}
