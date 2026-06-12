package cl.vetnova.auth.service;

import cl.vetnova.auth.dto.UpdatePerfilRequest;
import cl.vetnova.auth.dto.UsuarioResponse;
import cl.vetnova.auth.exception.ResourceNotFoundException;
import cl.vetnova.auth.model.Usuario;
import cl.vetnova.auth.repository.UsuarioRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {
    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);
    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<UsuarioResponse> listar() {
        return repository.findAll().stream().map(UsuarioResponse::from).toList();
    }

    public Usuario buscarEntidad(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + id));
    }

    public UsuarioResponse buscar(Long id) {
        return UsuarioResponse.from(buscarEntidad(id));
    }

    public boolean existe(Long id) {
        return repository.existsById(id);
    }

    @Transactional
    public UsuarioResponse actualizarPerfil(Long id, UpdatePerfilRequest request) {
        Usuario usuario = buscarEntidad(id);
        usuario.setNombre(request.nombre());
        usuario.setTelefono(request.telefono());
        log.info("event=usuario_profile_updated usuarioId={}", id);
        return UsuarioResponse.from(repository.save(usuario));
    }

    @Transactional
    public UsuarioResponse activar(Long id) {
        Usuario usuario = buscarEntidad(id);
        usuario.setActivo(true);
        log.info("event=usuario_activated usuarioId={}", id);
        return UsuarioResponse.from(repository.save(usuario));
    }

    @Transactional
    public UsuarioResponse desactivar(Long id) {
        Usuario usuario = buscarEntidad(id);
        usuario.setActivo(false);
        log.info("event=usuario_deactivated usuarioId={}", id);
        return UsuarioResponse.from(repository.save(usuario));
    }
}
