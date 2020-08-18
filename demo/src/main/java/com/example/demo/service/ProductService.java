package com.example.demo.service;

import com.example.demo.dto.request.ProductRequest;
import com.example.demo.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {
     List<ProductResponse> addProduct(List<ProductRequest> productRequestList, int discount);
     List<ProductResponse> getProducts();
}
