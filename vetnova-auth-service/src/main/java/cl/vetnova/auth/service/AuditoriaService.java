package cl.vetnova.auth.service;

import cl.vetnova.auth.model.AuditoriaAcceso;
import cl.vetnova.auth.model.Usuario;
import cl.vetnova.auth.repository.AuditoriaAccesoRepository;
import org.springframework.stereotype.Service;

@Service
public class AuditoriaService {
    private final AuditoriaAccesoRepository repository;

    public AuditoriaService(AuditoriaAccesoRepository repository) {
        this.repository = repository;
    }

    public void registrar(Usuario usuario, String accion, String ip, Boolean exitoso, String detalle) {
        repository.save(new AuditoriaAcceso(usuario, accion, ip, exitoso, detalle));
    }
}
