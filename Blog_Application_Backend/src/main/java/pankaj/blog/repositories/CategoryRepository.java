package pankaj.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import pankaj.blog.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
