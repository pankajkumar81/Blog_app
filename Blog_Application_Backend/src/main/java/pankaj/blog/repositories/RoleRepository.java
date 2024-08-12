package pankaj.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pankaj.blog.entity.Role;

@Repository
public interface  RoleRepository extends JpaRepository<Role, Integer>{

}
