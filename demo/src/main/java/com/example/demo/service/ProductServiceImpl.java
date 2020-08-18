package com.example.demo.service;

import com.example.demo.dto.request.ProductRequest;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ProductResponse> addProduct(List<ProductRequest> productRequestList, int discount) {
        // wywołanie metody, która zlicza sumaryczną wartość dodawanych produktów,
        // konieczne by przyznawać rabat proporcjonalnie do ceny
        int totalValue = countTotalValue(productRequestList);
        // kiedy rabat jest większy od wartości całej listy produktów wtedy zostaje zwrócona użytkownikowi
        // lista z nazwami produktów oraz zniżką w wysokości 0
        if(totalValue < discount){
            return productRequestList.stream()
                    .map(tmp -> modelMapper.map(tmp, ProductResponse.class))
                    .collect(Collectors.toList());
        }
        int addedDiscount = 0;
        List<Product> products = new ArrayList<>();
        for(int i = 0; i < productRequestList.size(); i++){
            Product product = modelMapper.map(productRequestList.get(i),Product.class);
            //sprawdzenie czy nie została wprowadzona błędna cena produktu jeżeli jest zła
            // użytkownikowi zostaje zwrócona lista z nazwą produktu oraz rabatem w wysokości 0
            // produkty nie zostają dodane do bazy danych
            if(product.getPrice() < 0){
                return productRequestList.stream()
                        .map(tmp -> modelMapper.map(tmp, ProductResponse.class))
                        .collect(Collectors.toList());
            }
            // konieczne rzutowanie typów ponieważ w przeciwnym wypadku wynikiem jest 0
            int productDiscount = ((int)(((double)productRequestList.get(i).getPrice() / (double)totalValue) * discount));
            // dodanie do ostatniego produktu pozostałego rabatu, którego nie dało się równomiernie rozdzielić
            if((i + 1) == productRequestList.size() && discount != addedDiscount){
                product.setDiscount(discount - addedDiscount);
            }else{
                product.setDiscount(productDiscount);
            }
            addedDiscount += productDiscount;
            product.setPriceWithDiscount(product.getPrice() - product.getDiscount());
            products.add(product);
            productRepository.save(product);
        }
        return products.stream()
                .map(product -> modelMapper.map(product,ProductResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getProducts() {
        return productRepository.findAll().stream()
                .map(tmp -> modelMapper.map(tmp,ProductResponse.class))
                .collect(Collectors.toList());
    }

    private int countTotalValue(List<ProductRequest> productRequestList){
        int totalValue = 0;
        for(ProductRequest request : productRequestList){
            totalValue += request.getPrice();
        }
        return totalValue;
    }
}
