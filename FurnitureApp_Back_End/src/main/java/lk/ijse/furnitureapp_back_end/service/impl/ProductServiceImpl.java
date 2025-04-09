package lk.ijse.furnitureapp_back_end.service.impl;

import lk.ijse.furnitureapp_back_end.dto.ProductDto;
import lk.ijse.furnitureapp_back_end.entity.Category;
import lk.ijse.furnitureapp_back_end.entity.Product;
import lk.ijse.furnitureapp_back_end.repo.CategoryRepository;
import lk.ijse.furnitureapp_back_end.repo.ProductRepository;
import lk.ijse.furnitureapp_back_end.service.ProductService;
import lk.ijse.furnitureapp_back_end.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ImageUploadService imageUploadService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        System.out.println("Product List = " + productList);

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

    public int saveProduct(ProductDto product) throws IOException {
        if (productRepository.existsByName(product.getName())) {
            return VarList.Not_Acceptable;
        } else {
            String uploadDir = "uploads/";
            String fileUrl = null;

            MultipartFile[] files = product.getImageFiles();


            if (files != null && files.length > 0) { // Check if files exist
                StringBuilder filePaths = new StringBuilder();
                for (MultipartFile file : files) {

                    String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
                    String savedPath = imageUploadService.saveFile(uploadDir, fileName, file);
                    filePaths.append(savedPath).append(",");
                }


                fileUrl = filePaths.length() > 0 ? filePaths.substring(0, filePaths.length() - 1) : null;
            }

            // Get Category by Name (Ensure category exists)
            Category category = categoryRepository.findByName(product.getCategoryName())
                    .orElseThrow(() -> new RuntimeException("Category not found: " + product.getCategoryName()));

            Product newProduct = new Product(
                    product.getName(),
                    category,
                    product.getPrice(),
                    product.getDescription(),
                    fileUrl
            );

            productRepository.save(newProduct);
            return VarList.Created;
        }
    }

    @Override
    public int deleteProduct(String productId) {
        Optional<Product> productOptional = productRepository.findById(UUID.fromString(productId));
        if (productOptional.isPresent()) {
            productRepository.deleteById(UUID.fromString(productId));
            return VarList.OK;
        }
        return VarList.Not_Found;
    }

    @Override
    public int updateProduct(String productId, ProductDto product) {
        Optional<Product> productOptional = productRepository.findById(UUID.fromString(productId));

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

    public Map<String, List<ProductDto>> getLatestThreeProductsPerCategory() {
        List<Category> categories = categoryRepository.findAll();
        Map<String, List<ProductDto>> latestProductDtos = new LinkedHashMap<>();

        for (Category category : categories) {
            List<Product> products = productRepository.findLastThreeByCategory(category.getCategoryId());

            List<ProductDto> productDtos = products.stream()
                    .map(product -> new ProductDto(
                            product.getProductId(),
                            product.getName(),
                            category.getName(), // or product.getCategory().getName()
                            product.getPrice(),
                            product.getDescription(),
                            product.getImageUrl()
                    ))
                    .toList();

            latestProductDtos.put(category.getName(), productDtos);
        }

        return latestProductDtos;
    }

    @Override
    public List<ProductDto> getProductsByCategoryName(String categoryName) {
        List<Product> products = productRepository.findByCategory_NameIgnoreCase(categoryName);

        List<ProductDto> dtos = new ArrayList<>();
        for (Product product : products) {
            ProductDto dto = new ProductDto();
            dto.setProductId(product.getProductId());
            dto.setName(product.getName());
            dto.setPrice(product.getPrice());
            dto.setDescription(product.getDescription());
            dto.setCategoryName(product.getCategory().getName()); // Assuming it's always not null
            dto.setImageUrl(product.getImageUrl()); // use setImageUrl instead of setImagePaths

            dtos.add(dto);
        }
        return dtos;
    }
}
