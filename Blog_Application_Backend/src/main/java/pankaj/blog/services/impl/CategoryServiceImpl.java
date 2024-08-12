package pankaj.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pankaj.blog.entity.Category;
import pankaj.blog.exceptions.ResourceNotFoundException;
import pankaj.blog.payloads.CategoryDto;
import pankaj.blog.repositories.CategoryRepository;
import pankaj.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private Logger logger=LoggerFactory.getLogger(CategoryServiceImpl.class);
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		
		Category category=this.dtoToCategory(categoryDto);
		logger.info("Category : {}", category);
		Category saveCategory=categoryRepository.save(category);
		logger.info("Category : {}", category);
		// TODO Auto-generated method stub
		return this.categoryToDto(saveCategory);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		
		Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", categoryId));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category updateCategory=categoryRepository.save(category);
		
		return this.categoryToDto(updateCategory);
	}

	@Override
	public List<CategoryDto> getCategories() {
		
		List<Category> categories=categoryRepository.findAll();
		List<CategoryDto> categoryDtos=categories.stream().map(category->this.categoryToDto(category)).collect(Collectors.toList());
		return categoryDtos;
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		
		Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", categoryId));
		return this.categoryToDto(category);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","categoryId", categoryId));
		categoryRepository.deleteById(categoryId);
	}
	
	//Conversion Dto to Category
	public Category dtoToCategory (CategoryDto categoryDto) {
		return this.modelMapper.map(categoryDto, Category.class);
	}
	
	//Conversion Category to Dto
	public CategoryDto categoryToDto(Category category) {
		return this.modelMapper.map(category, CategoryDto.class);
		
	}

}
