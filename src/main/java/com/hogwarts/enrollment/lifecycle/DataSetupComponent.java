package com.hogwarts.enrollment.lifecycle;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.hogwarts.enrollment.dao.entity.Role;
import com.hogwarts.enrollment.dao.entity.User;
import com.hogwarts.enrollment.dao.repository.RoleRepository;
import com.hogwarts.enrollment.dao.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class DataSetupComponent {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @PostConstruct
    private void loadDataFromCsvOnStartup() {
        List<Role> roles = readCsv(Role.class, "seedData/roles.csv");
        roles.forEach( role -> {
            roleRepository.save(role);
        });

        List<User> users = readCsv(User.class, "seedData/users.csv");
        users.forEach( u -> {
            User user = User.builder().name(u.getName()).role(roleRepository.findByType(u.getRoleName())).build();
            userRepository.save(user);
        });
    }

    private <T> List<T> readCsv(Class<T> type, String fileName) {
        try {
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
            CsvMapper mapper = new CsvMapper();
            File file = new ClassPathResource(fileName).getFile();
            MappingIterator<T> readValues =
                    mapper.reader(type).with(bootstrapSchema).readValues(file);
            return readValues.readAll();
        } catch (Exception e) {
            log.error("Error occurred while loading list from file " + fileName, e);
            return Collections.emptyList();
        }
    }
}
