package com.example.ecomm.services.admin.category;

import com.example.ecomm.dto.CategoryDto;
import com.example.ecomm.entity.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryDto categoryDto);

    List<Category> getAllCategories();
}
