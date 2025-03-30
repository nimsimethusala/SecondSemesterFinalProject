package lk.ijse.furnitureapp_back_end.service;

import lk.ijse.furnitureapp_back_end.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    int saveCategory(CategoryDto category);

    int deleteCategory(String categoryId);

    int updateCategory(String categoryId, CategoryDto category);

    List<CategoryDto> getAllCategories();
}
