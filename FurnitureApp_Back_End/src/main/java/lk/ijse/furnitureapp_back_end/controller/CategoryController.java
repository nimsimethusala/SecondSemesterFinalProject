package lk.ijse.furnitureapp_back_end.controller;

import jakarta.validation.Valid;
import lk.ijse.furnitureapp_back_end.dto.CategoryDto;
import lk.ijse.furnitureapp_back_end.dto.ResponseDTO;
import lk.ijse.furnitureapp_back_end.entity.Category;
import lk.ijse.furnitureapp_back_end.service.CategoryService;
import lk.ijse.furnitureapp_back_end.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/getAll")
//    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<ResponseDTO> getAll() {
        try {
            List<CategoryDto> categories = categoryService.getAllCategories();
            System.out.println(categories);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO(VarList.OK, "Categories Retrieved Successfully", categories));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<ResponseDTO> save(@Valid @RequestBody CategoryDto category) {
        try {
            int res = categoryService.saveCategory(category);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO(VarList.Created, "Category Saved Successfully", res));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    @PostMapping("/delete/{categoryId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<ResponseDTO> delete(@PathVariable String categoryId) {
        System.out.println(categoryId);
        try {
            int res = categoryService.deleteCategory(categoryId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO(VarList.OK, "Category Deleted Successfully", res));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    @PutMapping("/update/{categoryId}")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> update(@PathVariable String categoryId, @Valid @RequestBody CategoryDto category) {
        try {
            category.setCategoryId(UUID.fromString(categoryId));
            int res = categoryService.updateCategory(categoryId, category);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO(VarList.OK, "Category Updated Successfully", res));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    @GetMapping("/names")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<ResponseDTO> getCategoryNames() {
        try {
            List<String> categoryNames = categoryService.getAllCategoryNames();

            if (!categoryNames.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseDTO(VarList.OK, "Category names retrieved successfully", categoryNames));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(VarList.Not_Found, "No category names found", null));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }
}
