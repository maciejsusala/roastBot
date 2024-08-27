package pl.maciejsusala.roastbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.maciejsusala.roastbot.model.UserModel;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    boolean existsByLogin(String login);
    boolean existsByEmail(String email);
    Optional<UserModel> findByLogin(String login);

}
