package ru.clevertec.cleverbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.cleverbank.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
