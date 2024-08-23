package pl.maciejsusala.psychobot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.maciejsusala.psychobot.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    boolean existsByLogin(String login);
    boolean existsByEmail(String email);
}
