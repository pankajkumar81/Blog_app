package pankaj.blog.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import pankaj.blog.payloads.CategoryDto;
import pankaj.blog.services.CategoryService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	private Logger logger=LoggerFactory.getLogger(CategoryController.class);
	
	@PostMapping("/create")
	public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
		
		CategoryDto categoryDto2=categoryService.createCategory(categoryDto);
		logger.info("Category : {}", categoryDto2);
		return  new ResponseEntity<>(categoryDto2,HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,@PathVariable Integer categoryId){
		CategoryDto categoryDto2=categoryService.updateCategory(categoryDto,categoryId);
		return  new ResponseEntity<>(categoryDto2,HttpStatus.CREATED);
	}
	
	@GetMapping("/gets")
	public ResponseEntity<List<CategoryDto>> getAllCategory()
	{
		List<CategoryDto>  categoryDto=categoryService.getCategories();
		return new ResponseEntity<>(categoryDto,HttpStatus.OK);
	}
	
	@GetMapping("/get/{categoryId}")
	public ResponseEntity<CategoryDto> getById(@PathVariable Integer categoryId)
	{
		CategoryDto categoryDto=categoryService.getCategory(categoryId);
		return new ResponseEntity<>(categoryDto,HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{categoryId}")
	public String deleteCategory(@PathVariable Integer categoryId)
	{
		categoryService.deleteCategory(categoryId);
		return "Deleted Category !!";
	}
	
}
