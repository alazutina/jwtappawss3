package net.anna.jwtappawss3.repository;

import net.anna.jwtappawss3.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
User findByUsername(String name);
}
