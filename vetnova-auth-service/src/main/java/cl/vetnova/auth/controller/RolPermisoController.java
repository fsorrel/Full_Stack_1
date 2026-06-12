package cl.vetnova.auth.controller;

import cl.vetnova.auth.dto.ApiResponse;
import cl.vetnova.auth.dto.RolRequest;
import cl.vetnova.auth.dto.RolResponse;
import cl.vetnova.auth.service.RolPermisoService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roles")
public class RolPermisoController {
    private final RolPermisoService service;

    public RolPermisoController(RolPermisoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RolResponse>>> listar() {
        return ResponseEntity.ok(ApiResponse.ok("Roles encontrados", service.listar()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RolResponse>> crear(@Valid @RequestBody RolRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Rol creado", service.crear(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RolResponse>> actualizar(@PathVariable Long id, @Valid @RequestBody RolRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Rol actualizado", service.actualizar(id, request)));
    }
}
