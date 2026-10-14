USE tecnostore;

-- ===================== MARCAS =====================
INSERT INTO marca (nombre) VALUES
('Samsung'), ('Apple'), ('Xiaomi'), ('Motorola');

-- ===================== CELULARES =====================
INSERT INTO celular (modelo, precio, stock, id_marca, sistema_operativo, gama) VALUES
('Galaxy S24',    3500000, 10, (SELECT id_marca FROM marca WHERE nombre = 'Samsung'),  'ANDROID', 'ALTA'),
('Galaxy A15',     850000, 25, (SELECT id_marca FROM marca WHERE nombre = 'Samsung'),  'ANDROID', 'MEDIA'),
('iPhone 15',     4800000,  5, (SELECT id_marca FROM marca WHERE nombre = 'Apple'),    'IOS',     'ALTA'),
('iPhone 13',     2900000,  8, (SELECT id_marca FROM marca WHERE nombre = 'Apple'),    'IOS',     'MEDIA'),
('Redmi Note 13',  900000, 20, (SELECT id_marca FROM marca WHERE nombre = 'Xiaomi'),   'ANDROID', 'MEDIA'),
('Redmi 12C',      520000, 30, (SELECT id_marca FROM marca WHERE nombre = 'Xiaomi'),   'ANDROID', 'BAJA'),
('Moto G54',       780000, 15, (SELECT id_marca FROM marca WHERE nombre = 'Motorola'), 'ANDROID', 'MEDIA'),
('Moto Edge 40',  2100000,  6, (SELECT id_marca FROM marca WHERE nombre = 'Motorola'), 'ANDROID', 'ALTA');

-- ===================== PERSONAS =====================
INSERT INTO persona (nombre, apellido, email, identificacion, telefono) VALUES
('Sara',    'Ramirez',    'sara.rami@email.com',    '1012345678', '3001234567'),
('Ana',     'Gamboa', 'anita@email.com',  '1023456789', '3109876543'),
('Santiago',    'Mendoza',    'santiago.mita@email.com',    '1034567890', '3205551122'),
('Isabella', 'Gualdron',    'isavs@email.com', '1045678901', '3157778899'),
('Danna',     'Tellez',  'sunisonfire@email.com',   '1056789012', '3012223344'),
('Juan',      'Tellez',   'juanisonfire@email.com',     '1067890123', '3128889900');

-- ===================== CLIENTES =====================
INSERT INTO cliente (id_persona) VALUES
((SELECT id_persona FROM persona WHERE email = 'sara.rami@email.com')),
((SELECT id_persona FROM persona WHERE email = 'anita@email.com')),
((SELECT id_persona FROM persona WHERE email = 'santiago.mita@email.com')),
((SELECT id_persona FROM persona WHERE email = 'isavs@email.com'));

-- ===================== ADMINISTRADORES =====================
INSERT INTO administrador (id_persona, username, contrasena) VALUES
((SELECT id_persona FROM persona WHERE email = 'sunisonfire@email.com'), 'sofia_admin', 123456),
((SELECT id_persona FROM persona WHERE email = 'juanisonfire@email.com'),   'juan_admin',  654321);

-- ===================== VENTAS =====================
-- (id_cliente 1 a 4 = Carlos, Laura, Andrés, Valentina, en ese orden)
INSERT INTO venta (id_cliente, fecha_hora, metodo_pago, estado, lugar, subtotal, total) VALUES
(1, '2026-09-28 10:15:00', 'PSE',           'ENVIADO',   'DOMICILIO', 4540000, 4540000),
(2, '2026-09-30 15:40:00', 'TARJETA',       'PENDIENTE', 'LOCAL',     4800000, 4800000),
(3, '2026-10-02 09:05:00', 'NEQUI',         'ENVIADO',   'DOMICILIO', 2580000, 2580000),
(4, '2026-10-05 18:20:00', 'TRANSFERENCIA', 'CANCELADO', 'DOMICILIO', 3750000, 3750000),
(1, '2026-10-07 12:30:00', 'NEQUI',         'PENDIENTE', 'LOCAL',     2100000, 2100000);

-- ===================== DETALLES DE VENTA =====================
INSERT INTO detalle_venta (id_venta, id_celular, cantidad, precio_unitario) VALUES
(1, (SELECT id_celular FROM celular WHERE modelo = 'Galaxy S24'),    1, 3500000),
(2, (SELECT id_celular FROM celular WHERE modelo = 'iPhone 15'),     1, 4800000),
(3, (SELECT id_celular FROM celular WHERE modelo = 'Redmi Note 13'), 2,  900000);