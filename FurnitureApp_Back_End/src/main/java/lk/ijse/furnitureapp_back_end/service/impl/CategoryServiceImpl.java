package lk.ijse.furnitureapp_back_end.service.impl;

import lk.ijse.furnitureapp_back_end.dto.CategoryDto;
import lk.ijse.furnitureapp_back_end.entity.Category;
import lk.ijse.furnitureapp_back_end.repo.CategoryRepository;
import lk.ijse.furnitureapp_back_end.service.CategoryService;
import lk.ijse.furnitureapp_back_end.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDto> getAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();

        return categoryList.stream().map(category ->
                new CategoryDto(category.getCategoryId(), category.getName(), category.getStatus())
        ).collect(Collectors.toList());
    }

    @Override
    public int saveCategory(CategoryDto category) {
        if (categoryRepository.existsByName(category.getName())) {
            return VarList.Not_Acceptable;
        } else {
            categoryRepository.save(new Category(category.getName(), category.getStatus()));
            return VarList.Created;
        }
    }

    @Override
    public int deleteCategory(String categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(UUID.fromString(categoryId));
        if (categoryOptional.isPresent()) {
            categoryRepository.deleteById(UUID.fromString(categoryId));
            return VarList.OK;
        }
        return VarList.Not_Found;
    }

    @Override
    public int updateCategory(String categoryId, CategoryDto category) {
        Optional<Category> categoryOptional = categoryRepository.findById(UUID.fromString(categoryId));

        if (categoryOptional.isPresent()) {
            Category existingCategory = categoryOptional.get();
            existingCategory.setName(category.getName());
            existingCategory.setStatus(category.getStatus());

            categoryRepository.save(existingCategory);
            return VarList.OK; // Successfully updated
        }

        return VarList.Not_Found; // Return 404 if category doesn't exist
    }

}
