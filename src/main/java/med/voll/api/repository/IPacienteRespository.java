package med.voll.api.repository;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.paciente.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IPacienteRespository extends JpaRepository<Paciente, Long> {
    Page<Paciente> findAllByAtivoTrue(Pageable pageable);

    @Query("""
        SELECT p.ativo 
        FROM Paciente p
        WHERE 
        p.id = :id
        """)
    Boolean findAtivoById(@NotNull Long id);
}
