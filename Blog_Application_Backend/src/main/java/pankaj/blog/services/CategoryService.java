package pankaj.blog.services;

import java.util.List;

import pankaj.blog.payloads.CategoryDto;

public interface CategoryService {
	
	CategoryDto createCategory(CategoryDto categoryDto);
	CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
	List<CategoryDto> getCategories();
	CategoryDto getCategory(Integer id);
	void deleteCategory(Integer id);

}
