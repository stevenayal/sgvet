CREATE TABLE PROVEEDOR (
    ID INT PRIMARY KEY,
    NOMBRE VARCHAR(50),
    RAZONSOCIAL VARCHAR(100),
    TELEFONO VARCHAR(20),
    CORREO VARCHAR(100)
);

INSERT INTO PROVEEDOR (ID, NOMBRE, RAZONSOCIAL, TELEFONO, CORREO) VALUES
(1, 'Distribuidora Animalia', 'Animalia S.A.', '0981-123456', 'contacto@animalia.com'),
(2, 'VetPro Insumos', 'VetPro SRL', '0981-654321', 'ventas@vetpro.com'),
(3, 'Mascotas y Más', 'Mascotas y Más Ltda.', '0981-789012', 'info@mascotasymas.com');

CREATE TABLE RRHH (
    ID INT PRIMARY KEY,
    NOMBRE VARCHAR(50),
    APELLIDO VARCHAR(50),
    CEDULA VARCHAR(20),
    TELEFONO VARCHAR(20),
    CORREO VARCHAR(100),
    CARGO VARCHAR(50),
    ESPECIALIDAD VARCHAR(50)
);

INSERT INTO RRHH (ID, NOMBRE, APELLIDO, CEDULA, TELEFONO, CORREO, CARGO, ESPECIALIDAD) VALUES
(1, 'Ana', 'Gómez', '1234567', '0981-111111', 'ana.gomez@vet.com', 'Veterinario', 'Cirugía'),
(2, 'Luis', 'Martínez', '2345678', '0981-222222', 'luis.martinez@vet.com', 'Asistente', 'Clínica'),
(3, 'María', 'Fernández', '3456789', '0981-333333', 'maria.fernandez@vet.com', 'Recepcionista', 'Administración');
