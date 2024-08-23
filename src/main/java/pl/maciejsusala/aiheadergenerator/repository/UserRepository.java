package pl.maciejsusala.aiheadergenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.maciejsusala.aiheadergenerator.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long> {
}
