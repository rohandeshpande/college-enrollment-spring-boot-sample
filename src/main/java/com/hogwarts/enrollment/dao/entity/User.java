package com.hogwarts.enrollment.dao.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "User")
@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId")
    private Integer userId;

    @Basic
    @Column(name = "Name")
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RoleId")
    private Role role;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "StudentSection",
            joinColumns = @JoinColumn(name = "StudentId"),
            inverseJoinColumns = @JoinColumn(name = "SectionId")
    )
    private final Set<Section> sections = new HashSet<>();

    public void addSection(Section section) {
        sections.add(section);
        section.getUsers().add(this);
    }

    public void removeSection(Section section) {
        sections.remove(section);
        section.getUsers().remove(this);
    }

    @Transient
    private String roleName;
}
