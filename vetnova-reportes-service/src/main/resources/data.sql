-- Reporte base de ejemplo
INSERT INTO reportes (tipo, sucursal, desde, hasta, generado_por, generado_en, estado)
SELECT 'VENTA', 'Chillán', DATEADD('DAY', -30, CURRENT_DATE), CURRENT_DATE, 1, CURRENT_DATE, 'GENERADO'
WHERE NOT EXISTS (SELECT 1 FROM reportes WHERE tipo = 'VENTA' AND sucursal = 'Chillán');
