package net.anna.jwtappawss3.repository;

import net.anna.jwtappawss3.model.Event;
import net.anna.jwtappawss3.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event,Long> {


}
