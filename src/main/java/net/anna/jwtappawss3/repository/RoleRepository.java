package net.anna.jwtappawss3.repository;

import net.anna.jwtappawss3.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long>
{
Role findByName(String name);
}
