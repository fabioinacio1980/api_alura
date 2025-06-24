package med.voll.api.repository;

import med.voll.api.domain.consultas.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IConsultaRepository extends JpaRepository<Consulta, Long> {
}
