package lk.ijse.furnitureapp_back_end.service.impl;

import lk.ijse.furnitureapp_back_end.dto.ProductDto;
import lk.ijse.furnitureapp_back_end.entity.Product;
import lk.ijse.furnitureapp_back_end.repo.ProductRepository;
import lk.ijse.furnitureapp_back_end.service.ProductService;
import lk.ijse.furnitureapp_back_end.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> productList = productRepository.findAll();

        return productList.stream().map(product ->
                new ProductDto(
                        product.getProductId(),
                        product.getName(),
                        product.getCategory().getName(),
                        product.getPrice(),
                        product.getDescription(),
                        product.getImageUrl())
        ).collect(Collectors.toList());
    }

    @Override
    public int saveProduct(ProductDto product) {
        if (productRepository.existsByName(product.getName())) {
            return VarList.Not_Acceptable;
        } else {
            // Assuming Product has a Category reference and the Category is already validated.
            Product newProduct = new Product(
                    product.getName(),
                    product.getCategory(),
                    product.getPrice(),
                    product.getDescription(),
                    product.getImageUrl()
            );
            productRepository.save(newProduct);
            return VarList.Created;
        }
    }

    @Override
    public int deleteProduct(String productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            productRepository.deleteById(productId);
            return VarList.OK;
        }
        return VarList.Not_Found;
    }

    @Override
    public int updateProduct(String productId, ProductDto product) {
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            Product existingProduct = productOptional.get();
            existingProduct.setName(product.getName());
            existingProduct.setCategory(product.getCategory());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setImageUrl(product.getImageUrl());

            productRepository.save(existingProduct);
            return VarList.OK; // Successfully updated
        }

        return VarList.Not_Found; // Return 404 if product doesn't exist
    }
}
