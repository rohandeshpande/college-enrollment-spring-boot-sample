package com.hogwarts.enrollment.dao.repository;

import com.hogwarts.enrollment.dao.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<Integer> countAllBySections_SectionId(Integer sectionId);

    List<User> findAllBySections_SectionIdOrderByUserIdAsc(Integer sectionId, Pageable pageable);
}
