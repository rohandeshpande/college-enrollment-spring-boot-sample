package com.hogwarts.enrollment.dao.entity;

import com.hogwarts.enrollment.constant.UserRole;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.OffsetDateTime;

@Entity
@Table(name = "RoleMeta")
@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RoleId")
    private Integer roleId;

    @Basic
    @Column(name = "Type")
    private String type;

    public boolean isAdmin() {
        return UserRole.ADMIN.name().equals(this.type);
    }

    public boolean isStudent() {
        return UserRole.STUDENT.name().equals(this.type);
    }

    public boolean isTeacher() {
        return UserRole.TEACHER.name().equals(this.type);
    }
}
