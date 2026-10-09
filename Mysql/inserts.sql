USE tecnostore;

-- ===================== PERSONAS =====================
INSERT INTO persona (nombre, apellido, email, identificacion, telefono) VALUES
('Carlos',   'Gómez',    'carlos.gomez@email.com',    '1012345678', '3001234567'),
('Laura',    'Martínez', 'laura.martinez@email.com',  '1023456789', '3109876543'),
('Andrés',   'Rojas',    'andres.rojas@email.com',    '1034567890', '3205551122'),
('Valentina','Pérez',    'valentina.perez@email.com', '1045678901', '3157778899'),
('Sofía',    'Ramírez',  'sofia.ramirez@email.com',   '1056789012', '3012223344'),
('Juan',     'Torres',   'juan.torres@email.com',     '1067890123', '3128889900');

-- ===================== CLIENTES (personas 1 a 4) =====================
INSERT INTO cliente (id_persona) VALUES (1), (2), (3), (4);

-- ===================== ADMINISTRADORES (personas 5 y 6) =====================
INSERT INTO administrador (id_persona, username, contrasena) VALUES
(5, 'sofia_admin', 123456),
(6, 'juan_admin',  654321);

-- ===================== MARCAS =====================
INSERT INTO marca (nombre) VALUES
('Samsung'), ('Apple'), ('Xiaomi'), ('Motorola');

-- ===================== CELULARES =====================
INSERT INTO celular (modelo, precio, stock, id_marca, sistema_operativo, gama) VALUES
('Galaxy S24',    3500000, 10, 1, 'ANDROID', 'ALTA'),
('Galaxy A15',     850000, 25, 1, 'ANDROID', 'MEDIA'),
('iPhone 15',     4800000,  5, 2, 'IOS',     'ALTA'),
('iPhone 13',     2900000,  8, 2, 'IOS',     'MEDIA'),
('Redmi Note 13',  900000, 20, 3, 'ANDROID', 'MEDIA'),
('Redmi 12C',      520000, 30, 3, 'ANDROID', 'BAJA'),
('Moto G54',       780000, 15, 4, 'ANDROID', 'MEDIA'),
('Moto Edge 40',  2100000,  6, 4, 'ANDROID', 'ALTA');

-- ===================== VENTAS =====================
INSERT INTO venta (id_cliente, fecha_hora, metodo_pago, estado, lugar, subtotal, total) VALUES
(1, '2026-09-28 10:15:00', 'PSE',           'ENVIADO',   'DOMICILIO', 4540000, 4540000),
(2, '2026-09-30 15:40:00', 'TARJETA',       'PENDIENTE', 'LOCAL',     4800000, 4800000),
(3, '2026-10-02 09:05:00', 'NEQUI',         'ENVIADO',   'DOMICILIO', 2580000, 2580000),
(4, '2026-10-05 18:20:00', 'TRANSFERENCIA', 'CANCELADO', 'DOMICILIO', 3750000, 3750000),
(1, '2026-10-07 12:30:00', 'NEQUI',         'PENDIENTE', 'LOCAL',     2100000, 2100000);

-- ===================== DETALLES DE VENTA =====================
-- (el subtotal de cada línea lo calcula MySQL solo)
INSERT INTO detalle_venta (id_venta, id_celular, cantidad, precio_unitario) VALUES
-- Venta 1: S24 x1 + Redmi 12C x2
(1, 1, 1, 3500000),
(1, 6, 2,  520000),
-- Venta 2: iPhone 15 x1
(2, 3, 1, 4800000),
-- Venta 3: Redmi Note 13 x2 + Moto G54 x1
(3, 5, 2,  900000),
(3, 7, 1,  780000),
-- Venta 4: iPhone 13 x1 + Galaxy A15 x1
(4, 4, 1, 2900000),
(4, 2, 1,  850000),
-- Venta 5: Moto Edge 40 x1
(5, 8, 1, 2100000);