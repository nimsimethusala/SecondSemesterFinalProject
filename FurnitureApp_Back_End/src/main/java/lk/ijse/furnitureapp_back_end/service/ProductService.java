package lk.ijse.furnitureapp_back_end.service;

import jakarta.validation.Valid;
import lk.ijse.furnitureapp_back_end.dto.ProductDto;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    List<ProductDto> getAllProducts();

    int saveProduct(@Valid ProductDto product) throws IOException;

    int deleteProduct(String productId);

    int updateProduct(String productId, @Valid ProductDto product);
}
