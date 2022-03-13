package com.hogwarts.enrollment.dao.repository;

import com.hogwarts.enrollment.dao.entity.Section;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends CrudRepository<Section, Integer> {
}
