
CREATE DATABASE alquiler_vehiculos;

USE alquiler_vehiculos;

-- Tabla de usuarios (clientes)
DROP TABLE IF EXISTS  usuarios;
CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    dni CHAR(8) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    direccion VARCHAR(200),
    fecha_registro DATE 
);

-- Tabla de vehículos
DROP TABLE IF EXISTS  vehiculos;
CREATE TABLE vehiculos (
    id_vehiculo INT AUTO_INCREMENT PRIMARY KEY,
    marca VARCHAR(50),
    modelo VARCHAR(50),
    año_de_fabrica INT,
    matricula VARCHAR(20) UNIQUE NOT NULL,
    precio DOUBLE,
    estado BOOLEAN NOT NULL
);

-- Tabla de reservas
DROP TABLE IF EXISTS  reservas;
CREATE TABLE  reservas (
    id_reserva INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT,
    id_vehiculo INT,
    fecha_reserva DATE,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    estado BOOLEAN NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_vehiculo) REFERENCES vehiculos(id_vehiculo)
);

-- Tabla de pagos
DROP TABLE IF EXISTS pagos;
CREATE TABLE pagos (
    id_pago INT AUTO_INCREMENT PRIMARY KEY,
    id_reserva INT,
    metodo_pago varchar(20) NOT NULL,
    monto double NOT NULL,
    fecha_pago DATE,
    FOREIGN KEY (id_reserva) REFERENCES reservas(id_reserva)
);

-- Tabla de recogida y devolución
DROP TABLE IF EXISTS recogida_devolucion;
CREATE TABLE recogida_devolucion (
    id_recogida_devolucion INT AUTO_INCREMENT PRIMARY KEY,
    id_reserva INT,
    lugar_recogida VARCHAR(100),
    lugar_devolucion VARCHAR(100),
    fecha_recogida DATE,
    fecha_devolucion DATE,
    FOREIGN KEY (id_reserva) REFERENCES reservas(id_reserva)
);