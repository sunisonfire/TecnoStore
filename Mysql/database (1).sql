-- =====================================================
-- TecnoStore - Script completo para MySQL
-- =====================================================
DROP DATABASE IF EXISTS tecnostore;

CREATE DATABASE tecnostore
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE tecnostore;

-- ===================== PERSONAS =====================
CREATE TABLE persona (
    id_persona     INT AUTO_INCREMENT PRIMARY KEY,
    nombre         VARCHAR(50)  NOT NULL,
    apellido       VARCHAR(50)  NOT NULL,
    email          VARCHAR(100) NOT NULL UNIQUE,
    identificacion VARCHAR(20)  NOT NULL UNIQUE,
    telefono       VARCHAR(20)
);

-- Cliente y Administrador heredan de Persona (cada uno apunta a su persona)
CREATE TABLE cliente (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    id_persona INT NOT NULL UNIQUE,
    CONSTRAINT fk_cliente_persona
        FOREIGN KEY (id_persona) REFERENCES persona(id_persona)
        ON DELETE CASCADE
);

CREATE TABLE administrador (
    id_administrador INT AUTO_INCREMENT PRIMARY KEY,
    id_persona       INT NOT NULL UNIQUE,
    username         VARCHAR(50) NOT NULL UNIQUE,
    contrasena       BIGINT NOT NULL,
    CONSTRAINT fk_administrador_persona
        FOREIGN KEY (id_persona) REFERENCES persona(id_persona)
        ON DELETE CASCADE
);

-- ===================== PRODUCTOS =====================
CREATE TABLE marca (
    id_marca INT AUTO_INCREMENT PRIMARY KEY,
    nombre   VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE celular (
    id_celular        INT AUTO_INCREMENT PRIMARY KEY,
    modelo            VARCHAR(100) NOT NULL,
    precio            DECIMAL(12,2) NOT NULL CHECK (precio >= 0),
    stock             INT NOT NULL DEFAULT 0 CHECK (stock >= 0),
    id_marca          INT NOT NULL,
    sistema_operativo ENUM('IOS','ANDROID') NOT NULL,
    gama              ENUM('ALTA','MEDIA','BAJA') NOT NULL,
    CONSTRAINT fk_celular_marca
        FOREIGN KEY (id_marca) REFERENCES marca(id_marca)
);

-- ===================== VENTAS =====================
CREATE TABLE venta (
    id_venta     INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente   INT NOT NULL,
    fecha_hora   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    metodo_pago  ENUM('PSE','TARJETA','NEQUI','TRANSFERENCIA') NOT NULL,
    estado       ENUM('PENDIENTE','ENVIADO','CANCELADO') NOT NULL DEFAULT 'PENDIENTE',
    lugar        ENUM('LOCAL','DOMICILIO') NOT NULL,
    subtotal     DECIMAL(14,2) NOT NULL DEFAULT 0,
    total        DECIMAL(14,2) NOT NULL DEFAULT 0,
    CONSTRAINT fk_venta_cliente
        FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente)
);

-- El subtotal del detalle NO se guarda: Java lo calcula (cantidad * precio_unitario)
CREATE TABLE detalle_venta (
    id_detalle_venta INT AUTO_INCREMENT PRIMARY KEY,
    id_venta         INT NOT NULL,
    id_celular       INT NOT NULL,
    cantidad         INT NOT NULL CHECK (cantidad > 0),
    precio_unitario  DECIMAL(12,2) NOT NULL CHECK (precio_unitario >= 0),
    CONSTRAINT fk_detalle_venta
        FOREIGN KEY (id_venta) REFERENCES venta(id_venta)
        ON DELETE CASCADE,
    CONSTRAINT fk_detalle_celular
        FOREIGN KEY (id_celular) REFERENCES celular(id_celular)
);

-- ===================== DATOS DE PRUEBA =====================
INSERT INTO marca (nombre) VALUES ('Samsung'), ('Apple'), ('Xiaomi');

INSERT INTO celular (modelo, precio, stock, id_marca, sistema_operativo, gama) VALUES
('Galaxy S24',    3500000, 10, 1, 'ANDROID', 'ALTA'),
('iPhone 15',     4800000,  5, 2, 'IOS',     'ALTA'),
('Redmi Note 13',  900000, 20, 3, 'ANDROID', 'MEDIA');