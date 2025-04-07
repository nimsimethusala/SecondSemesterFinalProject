package lk.ijse.furnitureapp_back_end.controller;

import jakarta.validation.Valid;
import lk.ijse.furnitureapp_back_end.dto.ProductDto;
import lk.ijse.furnitureapp_back_end.dto.ResponseDTO;
import lk.ijse.furnitureapp_back_end.entity.Product;
import lk.ijse.furnitureapp_back_end.service.ProductService;
import lk.ijse.furnitureapp_back_end.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAll() {
        try {
            List<ProductDto> products = productService.getAllProducts();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO(VarList.OK, "Products Retrieved Successfully", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> save(
            @RequestParam("name") String name,
            @RequestParam("categoryName") String categoryName,
            @RequestParam("price") double price,
            @RequestParam("description") String description,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) {
        try {
            // Create DTO object
            ProductDto product = new ProductDto();
            product.setName(name);
            product.setCategoryName(categoryName);
            product.setPrice(price);
            product.setDescription(description);

            if (imageFile != null && !imageFile.isEmpty()) {
                product.setImageFiles(new MultipartFile[]{imageFile});
            }

//            System.out.println(product.getImageFiles());
//            System.out.println(Arrays.toString(product.getImageFiles()));

            int res = productService.saveProduct(product);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO(VarList.Created, "Product Saved Successfully", res));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{productId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> delete(@PathVariable String productId) {
        try {
            int res = productService.deleteProduct(productId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO(VarList.OK, "Product Deleted Successfully", res));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    @PutMapping("/update/{productId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> update(@PathVariable String productId, @Valid @RequestBody ProductDto product) {
        try {
            product.setProductId(UUID.fromString(productId));
            int res = productService.updateProduct(productId, product);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO(VarList.OK, "Product Updated Successfully", res));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    @GetMapping("/latest-per-category")
    public ResponseEntity<Map<String, List<ProductDto>>> getLatestProductsByCategory() {
        return ResponseEntity.ok(productService.getLatestThreeProductsPerCategory());
    }
}
