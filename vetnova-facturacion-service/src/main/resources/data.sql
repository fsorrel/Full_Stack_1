-- Rango de folios inicial para boletas y facturas
INSERT INTO folios (tipo_documento, folio_desde, folio_hasta, folio_actual, folios_restantes, activo, sucursal)
SELECT 'BOLETA', 1, 100, 1, 100, true, 'Chillán'
WHERE NOT EXISTS (SELECT 1 FROM folios WHERE tipo_documento = 'BOLETA' AND sucursal = 'Chillán');

INSERT INTO folios (tipo_documento, folio_desde, folio_hasta, folio_actual, folios_restantes, activo, sucursal)
SELECT 'FACTURA', 1, 50, 1, 50, true, 'Chillán'
WHERE NOT EXISTS (SELECT 1 FROM folios WHERE tipo_documento = 'FACTURA' AND sucursal = 'Chillán');
