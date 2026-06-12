-- Canales de notificación disponibles
INSERT INTO canal_notificacion (nombre, tipo, activo, sucursal)
SELECT 'Correo institucional', 'EMAIL', true, 'TODAS'
WHERE NOT EXISTS (SELECT 1 FROM canal_notificacion WHERE nombre = 'Correo institucional');

INSERT INTO canal_notificacion (nombre, tipo, activo, sucursal)
SELECT 'SMS clientes', 'SMS', true, 'TODAS'
WHERE NOT EXISTS (SELECT 1 FROM canal_notificacion WHERE nombre = 'SMS clientes');
