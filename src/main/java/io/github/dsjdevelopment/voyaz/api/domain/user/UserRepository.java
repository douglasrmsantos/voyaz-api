package io.github.dsjdevelopment.voyaz.api.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findByLogin(String login);

    Boolean existsByLoginAndActiveTrue(String login);

    Boolean existsByLoginAndActiveFalse(String login);

    User getReferenceByLogin(String login);

    Boolean existsByIdAndActiveTrue(Long id);
}
