package com.hogwarts.enrollment.dao.repository;

import com.hogwarts.enrollment.dao.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import springfox.documentation.annotations.Cacheable;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

    @Cacheable("roleEntities")
    Role findByType(String type);
}
