package lk.ijse.furnitureapp_back_end.service;

import jakarta.validation.Valid;
import lk.ijse.furnitureapp_back_end.dto.ProductDto;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ProductService {
    List<ProductDto> getAllProducts();

    int saveProduct(@Valid ProductDto product) throws IOException;

    int deleteProduct(String productId);

    int updateProduct(String productId, @Valid ProductDto product);

    Map<String, List<ProductDto>> getLatestThreeProductsPerCategory();

    List<ProductDto> getProductsByCategoryName(String categoryName);
}
