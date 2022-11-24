package net.anna.jwtappawss3.repository;

import net.anna.jwtappawss3.model.File;
import net.anna.jwtappawss3.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
    File findByPath(String path);
}
