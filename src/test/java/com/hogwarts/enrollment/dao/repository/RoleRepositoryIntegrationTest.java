package com.hogwarts.enrollment.dao.repository;

import com.hogwarts.enrollment.constant.UserRole;
import com.hogwarts.enrollment.dao.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
public class RoleRepositoryIntegrationTest {
    @Autowired
    RoleRepository roleRepository;

    @Test
    public void whenRoleIsCreated_thenRoleIsCorrectlyEvaluated() {
        roleRepository.save(Role.builder().type(UserRole.ADMIN.name()).build());
        roleRepository.save(Role.builder().type(UserRole.TEACHER.name()).build());
        roleRepository.save(Role.builder().type(UserRole.STUDENT.name()).build());

        assertEquals(roleRepository.findByType(UserRole.ADMIN.name()).isAdmin(), true);
        assertEquals(roleRepository.findByType(UserRole.TEACHER.name()).isTeacher(), true);
        assertEquals(roleRepository.findByType(UserRole.STUDENT.name()).isStudent(), true);
        assertEquals(roleRepository.findByType(UserRole.ADMIN.name()).isStudent(), false);
        assertEquals(roleRepository.findByType(UserRole.ADMIN.name()).isTeacher(), false);
    }
}
