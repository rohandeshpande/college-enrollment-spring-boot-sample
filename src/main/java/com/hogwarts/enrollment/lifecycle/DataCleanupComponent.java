package com.hogwarts.enrollment.lifecycle;

import com.hogwarts.enrollment.dao.repository.NativeSQL;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Component
public class DataCleanupComponent {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void purgeAllData() {
        Query disableForeignKeyChecksQuery = entityManager.createNativeQuery(NativeSQL.DISABLE_FOREIGN_KEY_CHECKS);
        disableForeignKeyChecksQuery.executeUpdate();

        Query truncateStudentSectionQuery = entityManager.createNativeQuery(NativeSQL.TRUNCATE_TABLE_STUDENT_SECTION);
        truncateStudentSectionQuery.executeUpdate();

        Query truncateSectionQuery = entityManager.createNativeQuery(NativeSQL.TRUNCATE_TABLE_SECTION);
        truncateSectionQuery.executeUpdate();

        Query truncateCourseQuery = entityManager.createNativeQuery(NativeSQL.TRUNCATE_TABLE_COURSE);
        truncateCourseQuery.executeUpdate();

        Query truncateUserQuery = entityManager.createNativeQuery(NativeSQL.TRUNCATE_TABLE_USER);
        truncateUserQuery.executeUpdate();

        Query truncateRoleMetaQuery = entityManager.createNativeQuery(NativeSQL.TRUNCATE_TABLE_ROLE_META);
        truncateRoleMetaQuery.executeUpdate();

        Query enableForeignKeyChecksQuery = entityManager.createNativeQuery(NativeSQL.ENABLE_FOREIGN_KEY_CHECKS);
        enableForeignKeyChecksQuery.executeUpdate();
    }
}
