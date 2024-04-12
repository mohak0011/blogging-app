package com.mohak.bloggingapp.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mohak.bloggingapp.entities.Category;
import com.mohak.bloggingapp.exceptions.ResourceNotFoundException;
import com.mohak.bloggingapp.payloads.CategoryDto;
import com.mohak.bloggingapp.payloads.UserDto;
import com.mohak.bloggingapp.repositories.CategoryRepo;
import com.mohak.bloggingapp.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    // Method to create a new category
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        // Convert CategoryDto to Category entity
        Category category = this.dtoToCategory(categoryDto);
        // Save the category in the repository
        Category savedCategory = this.categoryRepo.save(category);
        // Convert the saved Category back to CategoryDto and return it
        return this.categoryToDto(savedCategory);
    }

    // Method to update an existing category
    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        // Find the category by id, throw exception if not found
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
        // Update category properties
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        // Save the updated category in the repository
        Category updatedCategory = this.categoryRepo.save(category);
        // Convert the updated Category back to CategoryDto and return it
        return this.categoryToDto(updatedCategory);
    }

    // Method to delete a category by id
    @Override
    public void deleteCategory(Integer categoryId) {
        // Find the category by id, throw exception if not found
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
        // Delete the category from the repository
        this.categoryRepo.delete(category);
    }

    // Method to get a category by id
    @Override
    public CategoryDto getCategory(Integer categoryId) {
        // Find the category by id, throw exception if not found
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
        // Convert the found Category to CategoryDto and return it
        return this.categoryToDto(category);
    }

    // Method to get all categories
    @Override
    public List<CategoryDto> getCategories() {
        // Retrieve all categories from the repository
        List<Category> categories = this.categoryRepo.findAll();
        // Convert each Category to CategoryDto using stream and collect them to a list
       // List<CategoryDto> categoryDtos = categories.stream().map(this::categoryToDto).collect(Collectors.toList());
    	List<CategoryDto>categoryDtos=categories.stream().map(category->this.categoryToDto(category)).collect(Collectors.toList());
        // Return the list of CategoryDtos
        return categoryDtos;
    }

    // Converts a CategoryDto object to a Category object using ModelMapper
    public Category dtoToCategory(CategoryDto categoryDto) {
        return this.modelMapper.map(categoryDto, Category.class);
    }

    // Converts a Category object to a CategoryDto object using ModelMapper
    public CategoryDto categoryToDto(Category category) {
        return this.modelMapper.map(category, CategoryDto.class);
    }
}
