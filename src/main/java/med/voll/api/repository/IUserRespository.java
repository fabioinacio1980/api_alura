package med.voll.api.repository;

import med.voll.api.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface IUserRespository extends JpaRepository<User, Long> {


    UserDetails findByUsername (String username);
}

