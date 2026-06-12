package cl.vetnova.auth.service;

import cl.vetnova.auth.dto.RolRequest;
import cl.vetnova.auth.dto.RolResponse;
import cl.vetnova.auth.exception.BusinessRuleException;
import cl.vetnova.auth.exception.ResourceNotFoundException;
import cl.vetnova.auth.model.RolPermiso;
import cl.vetnova.auth.repository.RolPermisoRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RolPermisoService {
    private static final Logger log = LoggerFactory.getLogger(RolPermisoService.class);
    private final RolPermisoRepository repository;

    public RolPermisoService(RolPermisoRepository repository) {
        this.repository = repository;
    }

    public List<RolResponse> listar() {
        return repository.findAll().stream().map(RolResponse::from).toList();
    }

    public RolPermiso buscarEntidad(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado: " + id));
    }

    public RolPermiso buscarPorNombre(String nombre) {
        return repository.findByNombreRolIgnoreCase(nombre).orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado: " + nombre));
    }

    @Transactional
    public RolResponse crear(RolRequest request) {
        if (repository.existsByNombreRolIgnoreCase(request.nombreRol())) {
            throw new BusinessRuleException("Ya existe un rol con ese nombre");
        }
        RolPermiso rol = new RolPermiso(request.nombreRol().toUpperCase(), request.descripcion(), request.permisos());
        RolPermiso guardado = repository.save(rol);
        log.info("event=rol_created rolId={} nombre={}", guardado.getId(), guardado.getNombreRol());
        return RolResponse.from(guardado);
    }

    @Transactional
    public RolResponse actualizar(Long id, RolRequest request) {
        RolPermiso rol = buscarEntidad(id);
        rol.setNombreRol(request.nombreRol().toUpperCase());
        rol.setDescripcion(request.descripcion());
        rol.setPermisos(request.permisos());
        log.info("event=rol_updated rolId={}", id);
        return RolResponse.from(repository.save(rol));
    }
}
