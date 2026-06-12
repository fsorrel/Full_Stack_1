package cl.vetnova.auth.controller;

import cl.vetnova.auth.dto.ApiResponse;
import cl.vetnova.auth.dto.UsuarioResponse;
import cl.vetnova.auth.service.UsuarioService;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UsuarioResponse>>> listar() {
        return ResponseEntity.ok(ApiResponse.ok("Usuarios encontrados", service.listar()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuarioResponse>> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Usuario encontrado", service.buscar(id)));
    }

    @GetMapping("/{id}/existe")
    public ResponseEntity<Map<String, Object>> existe(@PathVariable Long id) {
        return ResponseEntity.ok(Map.of("id", id, "existe", service.existe(id)));
    }

    @GetMapping("/{id}/rol")
    public ResponseEntity<Map<String, Object>> rol(@PathVariable Long id) {
        UsuarioResponse usuario = service.buscar(id);
        return ResponseEntity.ok(Map.of("id", id, "rol", usuario.rol(), "activo", usuario.activo()));
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<ApiResponse<UsuarioResponse>> activar(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Usuario activado", service.activar(id)));
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<ApiResponse<UsuarioResponse>> desactivar(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Usuario desactivado", service.desactivar(id)));
    }
}
