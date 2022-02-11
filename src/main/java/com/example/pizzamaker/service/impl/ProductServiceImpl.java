package com.example.pizzamaker.service.impl;

import com.example.pizzamaker.model.Ingredient;
import com.example.pizzamaker.model.Product;
import com.example.pizzamaker.model.commons.ProductToIngredient;
import com.example.pizzamaker.model.dto.ProductDto;
import com.example.pizzamaker.repository.ProductRepository;
import com.example.pizzamaker.repository.ProductToIngredientRepository;
import com.example.pizzamaker.service.ProductService;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository = new ProductRepository();
    ProductToIngredientRepository productToIngredientRepository = new ProductToIngredientRepository();


    @Override
    public Product readProduct(int id) {
        Product product = new Product();
        List<ProductDto> fromDb = productRepository.read(id);

        product.setId(fromDb.get(0).getId());
        product.setProductTypeId(fromDb.get(0).getProductTypeId());
        product.setPrice(fromDb.get(0).getPrice());
        product.setName(fromDb.get(0).getName());
        product.setImagePath(fromDb.get(0).getImagePath());
        product.setCurrency(fromDb.get(0).getCurrency());

        return product;
    }

    @Override
    public ProductDto read(int id) {
        ProductDto productDto = new ProductDto();
        List<ProductDto> fromDb = productRepository.read(id);

        productDto.setId(fromDb.get(0).getId());
        productDto.setProductTypeId(fromDb.get(0).getProductTypeId());
        productDto.setPrice(fromDb.get(0).getPrice());
        productDto.setName(fromDb.get(0).getName());
        productDto.setImagePath(fromDb.get(0).getImagePath());
        productDto.setCurrency(fromDb.get(0).getCurrency());
        productDto.setIngredients(new LinkedList<>());

        fromDb.forEach(item -> {
            Ingredient ingredient = new Ingredient(item.getIngredientId(), item.getIngredientName());
            productDto.getIngredients().add(ingredient);
        });

        return productDto;
    }

    @Override
    public List<ProductDto> readAll() {

        List<ProductDto> fromDb = productRepository.readAll();
        List<ProductDto> data = new LinkedList<>();
        fromDb.forEach(item -> {
            int i = 0;
            for (; i < data.size(); i++) {
                if (data.get(i).getId() == item.getId()) {
                    break;
                }
            }
            if (i != data.size()) {
                data.get(i).getIngredients().add(new Ingredient(item.getIngredientId(), item.getIngredientName()));
            } else {
                ProductDto productDto = new ProductDto();
                productDto.setId(item.getId());
                productDto.setProductTypeId(item.getProductTypeId());
                productDto.setName(item.getName());
                productDto.setPrice(item.getPrice());
                productDto.setImagePath(item.getImagePath());
                productDto.setCurrency(item.getCurrency());
                productDto.setIngredients(new LinkedList<>());
                productDto.getIngredients().add(new Ingredient(item.getIngredientId(), item.getIngredientName()));
                data.add(productDto);
            }
            item.setId(-1);

        });


        return data;
    }

    @Override
    public List<ProductDto> readAllByProductType(int productTypeId) {
        return readAll().stream().filter(item -> item.getProductTypeId() == productTypeId).collect(Collectors.toList());
    }

    @Override
    public void create(ProductDto productDto) {

        if (productDto == null) {
            return;
        }

        Product product = new Product();
        productRepository.create(product);

        productDto.getIngredients().forEach(item -> {
            ProductToIngredient productToIngredient = new ProductToIngredient();

            productToIngredient.setIngredientId(item.getId());
            List<ProductDto> productDtos = productRepository.readAll();
            int id = productDtos.get(productDtos.size() - 1).getId();
            productToIngredient.setProductId(id);
            productToIngredientRepository.create(productToIngredient);
        });

    }


    @Override
    public ProductDto update(int id, ProductDto productDto) {
        if (productRepository.read(id) != null) {
            Product p = new Product();
            p.setId(productDto.getId());
            p.setProductTypeId(productDto.getProductTypeId());
            p.setName(productDto.getName());
            p.setPrice(productDto.getPrice());
            p.setImagePath(productDto.getImagePath());
            p.setCurrency(productDto.getCurrency());
            productRepository.update(p);
        }

        if (productToIngredientRepository.readByProduct(id) != null) {
            List<Ingredient> ingredients = productDto.getIngredients();
            ingredients.forEach(item -> {
                ProductToIngredient productToIngredient = new ProductToIngredient(0, item.getId(), productDto.getId());
                productToIngredientRepository.update(productToIngredient);
            });
        }
        return productDto;
    }

    @Override
    public void delete(int id) {
        productRepository.delete(id);
        productToIngredientRepository.deleteByProduct(id);
    }
}
