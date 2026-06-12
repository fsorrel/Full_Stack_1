package cl.vetnova.auth.repository;

import cl.vetnova.auth.model.SesionToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SesionTokenRepository extends JpaRepository<SesionToken, Long> {
    Optional<SesionToken> findByToken(String token);
}
