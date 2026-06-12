# VetNova - Backend de Microservicios

Plataforma backend para una clínica veterinaria con sucursales en Chillán, Los Ángeles y Talca.
Proyecto de Desarrollo Full Stack 1 (Duoc UC).

Cada microservicio es un proyecto Maven independiente con Spring Boot 3.3.5, Java 17 y su propia
base de datos H2 en archivo (se crea sola al levantar, no hay que instalar nada). La comunicación
entre microservicios es REST con WebClient.

## Microservicios y puertos

| # | Microservicio | Carpeta | Puerto | Base H2 (archivo) |
|---|---------------|---------|--------|-------------------|
| 1 | Autenticación e Identidad | vetnova-auth-service | 8081 | ./data/authdb |
| 2 | Catálogo | vetnova-catalogo-service | 8082 | ./data/catalogodb |
| 3 | Inventario | vetnova-inventario-service | 8083 | ./data/inventariodb |
| 4 | Ventas y Pedidos | vetnova-ventas-service | 8084 | ./data/ventasdb |
| 5 | Envío y Logística | vetnova-envio-service | 8085 | ./data/enviodb |
| 6 | Agenda y Horas | vetnova-agenda-service | 8086 | ./data/agendadb |
| 7 | Ficha Clínica | vetnova-ficha-service | 8087 | ./data/fichadb |
| 8 | Soporte y Reclamos | vetnova-soporte-service | 8088 | ./data/soportedb |
| 9 | Laboratorio y Exámenes | vetnova-laboratorio-service | 8089 | ./data/labdb |
| 10 | Facturación / SII | vetnova-facturacion-service | 8090 | ./data/facturaciondb |
| 11 | Reportes Central | vetnova-reportes-service | 8091 | ./data/reportesdb |
| 12 | Notificaciones | vetnova-notificaciones-service | 8092 | ./data/notificacionesdb |

La consola H2 de cada servicio queda en `http://localhost:PUERTO/h2-console`
(JDBC URL: la misma `spring.datasource.url` del application.properties, usuario `sa`, sin contraseña).

## Compilar los 12 de una vez (Windows)

En la raíz del proyecto, con PowerShell:

```powershell
.\compilar-todos.ps1
```

El script compila cada microservicio con `mvn clean package`, avisa si alguna carpeta
no existe y muestra un resumen OK/FAIL al final.

## Cómo levantar un microservicio

Dentro de la carpeta del servicio:

```bash
mvn spring-boot:run
```

## Orden recomendado para levantar

No es obligatorio levantar los 12 para probar un flujo, pero el orden lógico es:

1. `vetnova-auth-service` (8081) — lo validan Soporte y Laboratorio
2. `vetnova-notificaciones-service` (8092) — recibe avisos de Inventario y Envío
3. `vetnova-inventario-service` (8083) — lo consulta Ventas y Envío
4. `vetnova-ventas-service` (8084) — lo consultan Envío y Facturación
5. `vetnova-envio-service` (8085)
6. El resto en cualquier orden: catálogo (8082), agenda (8086), ficha clínica (8087),
   soporte (8088), laboratorio (8089), facturación (8090), reportes (8091)

Nota: si Notificaciones está apagado, Inventario y Envío igual funcionan
(el aviso es informativo, solo queda un warning en el log). Si Inventario está
apagado, Ventas responde 502 con mensaje controlado en vez de caerse.

## Comunicación entre microservicios

| Origen | Destino | Endpoint consumido | Para qué |
|--------|---------|--------------------|----------|
| Soporte | Auth (8081) | GET /api/usuarios/{id}/existe | Validar usuario antes de crear ticket |
| Laboratorio | Auth (8081) | GET /api/usuarios/{id}/existe | Validar usuario antes de crear orden de examen |
| Ventas | Inventario (8083) | GET /api/v1/inventario/productos/{id}/stock?idSucursal= | Validar stock al crear la orden |
| Ventas | Inventario (8083) | POST /api/v1/inventario/movimientos | Descontar stock al confirmar el pago |
| Envío | Ventas (8084) | GET /api/v1/ordenes/{id}/existe | Validar la orden antes de crear el despacho |
| Envío | Inventario (8083) | POST /api/v1/inventario/movimientos | Mover stock en transferencias entre sucursales |
| Envío | Notificaciones (8092) | POST /api/v1/notificaciones | Avisar cambio de estado del envío |
| Inventario | Notificaciones (8092) | POST /api/v1/notificaciones | Alerta de stock crítico |
| Facturación | Ventas (8084) | GET /api/v1/ordenes/{id}/existe | Asociar el documento a una orden real |

## Flujo de prueba completo (Postman)

La colección `vetnova.postman_collection.json` en la raíz trae todos estos requests listos.

1. **Auth**: `GET http://localhost:8081/api/usuarios/1/existe` → `{"id":1,"existe":true}` (hay usuarios de ejemplo en data.sql).
2. **Soporte**: `POST http://localhost:8088/api/tickets` con un usuario válido → crea ticket validando contra Auth.
3. **Laboratorio**: `POST http://localhost:8089/api/ordenes-examen` → crea orden de examen validando contra Auth.
4. **Inventario**: `GET http://localhost:8083/api/v1/inventario/productos/1/stock?idSucursal=1` → stock disponible.
5. **Ventas**: `POST http://localhost:8084/api/v1/ordenes`

```json
{
  "clienteId": 1,
  "idSucursal": 1,
  "detalles": [
    { "productoId": 1, "nombreProducto": "Alimento perro adulto 15kg", "cantidad": 2, "precioUnitario": 35990 }
  ]
}
```

   Respuesta: orden `PENDIENTE` con subtotal, IVA 19% y total calculados.

6. **Ventas - pago**: `POST http://localhost:8084/api/v1/ordenes/1/pagos`

```json
{ "metodo": "DEBITO", "monto": 85656.2, "referencia": "TRX-0001" }
```

   El monto debe ser igual al total de la orden. El pago aprobado deja la orden `CONFIRMADA`
   y descuenta el stock en Inventario (se puede verificar repitiendo el paso 4).

7. **Envío**: `POST http://localhost:8085/api/v1/envios`

```json
{ "ordenId": 1, "tipoEnvio": "DOMICILIO", "idSucursalOrigen": 1, "direccionEntrega": "Av. Libertad 123, Chillán" }
```

   Valida la orden contra Ventas y crea el despacho con número de guía y tracking `PREPARANDO`.
   Luego `PUT /api/v1/envios/1/estado` con `{"estado":"EN_RUTA"}` y `{"estado":"ENTREGADO"}`.

8. **Facturación**: `POST http://localhost:8090/api/v1/documentos`

```json
{ "ordenId": 1, "clienteId": 1, "tipo": "BOLETA", "folio": "1", "neto": 71980, "iva": 0, "total": 0, "rutEmisor": "76.123.456-7", "sucursal": "Chillán" }
```

   Valida la orden contra Ventas y calcula IVA y total si vienen en 0.

9. **Notificaciones**: `GET http://localhost:8092/api/v1/notificaciones` → se ven las alertas
   que generaron Inventario (stock crítico) y Envío (cambios de estado).
10. **Reportes**: `GET http://localhost:8091/api/v1/reportes` (Swagger en `/swagger-ui.html`).

## Errores

Todos los servicios responden errores con el mismo formato JSON:

```json
{
  "success": false,
  "message": "Stock insuficiente para el producto 1...",
  "path": "/api/v1/ordenes",
  "status": 400,
  "errors": {},
  "timestamp": "..."
}
```

- 404: recurso no existe
- 400: validación de datos o regla de negocio
- 502: el microservicio remoto no respondió

## Diagramas

`diagrama_microservicios_vetnova.html` (raíz del repo) tiene el diagrama de clases de cada
microservicio en Mermaid, coherente con el código. Se abre directo en el navegador.
