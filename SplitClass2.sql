DROP DATABASE IF EXISTS SplitClass;
CREATE DATABASE IF NOT EXISTS SplitClass;
USE SplitClass;

CREATE TABLE IDIOMA (
	id_idioma INT NOT NULL AUTO_INCREMENT,
    Codigo VARCHAR(5) NOT NULL UNIQUE,
    Nombre VARCHAR(50) NOT NULL,
    PRIMARY KEY (id_idioma)
);

CREATE TABLE CATEGORIA (
    id_categoria INT NOT NULL AUTO_INCREMENT,
    Nombre VARCHAR(50) NOT NULL,
    PRIMARY KEY (id_categoria)
);

CREATE TABLE ESTADO_GRUPO (
    id_Estado INT NOT NULL AUTO_INCREMENT,
    nombreEstado VARCHAR(20) NOT NULL,
    PRIMARY KEY (id_Estado)
);
CREATE TABLE ESTADO_LIQUIDACION (
    id_estado_liq INT NOT NULL AUTO_INCREMENT,
    nombreEstadoLiq VARCHAR(20) NOT NULL,
    PRIMARY KEY (id_estado_liq)
);

CREATE TABLE USUARIO (
    id_Usuario INT NOT NULL AUTO_INCREMENT,
    codUsuario VARCHAR(100) NOT NULL UNIQUE,
    Nombre VARCHAR(100) NOT NULL,
    Email VARCHAR(150) NOT NULL UNIQUE,
    Password VARCHAR(255) NOT NULL,
    Telefono VARCHAR(20),
    id_idioma INT DEFAULT 1, 
    Alias VARCHAR(50),
    IBAN VARCHAR(34),
    Fecha_Creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Fecha_Nacimiento DATE,
    verificacionActividad BOOLEAN DEFAULT true, 
    PRIMARY KEY (id_Usuario),
    CONSTRAINT fk_usuario_idioma FOREIGN KEY (id_idioma) REFERENCES IDIOMA (id_idioma) ON DELETE SET NULL
);

CREATE TABLE GRUPO (
    Id_Grupo INT NOT NULL AUTO_INCREMENT,
    codGrupo VARCHAR(100) NOT NULL UNIQUE,
    Titulo VARCHAR(100) NOT NULL, 
    Descripcion TEXT,
    Moneda VARCHAR(10) DEFAULT 'EUR',
    Fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	fecha_eliminacion DATE, 
    Id_Estado INT NOT NULL,
    PRIMARY KEY (id_Grupo),
    CONSTRAINT fk_grupo_estado FOREIGN KEY (id_Estado) REFERENCES ESTADO_GRUPO (id_Estado)
);

CREATE TABLE MIEMBROS_GRUPO (
    id_miembros INT NOT NULL AUTO_INCREMENT,
    codMiembrosGrupo VARCHAR(100) NOT NULL UNIQUE,
    id_Usuario INT NOT NULL,
    id_Grupo INT NOT NULL,
    PRIMARY KEY (id_miembros),
    UNIQUE KEY uq_usuario_grupo (id_Usuario, id_Grupo),
    CONSTRAINT fk_mg_usuario FOREIGN KEY (id_Usuario) REFERENCES USUARIO (id_Usuario) ON DELETE CASCADE,
    CONSTRAINT fk_mg_grupo FOREIGN KEY (id_Grupo) REFERENCES GRUPO (id_Grupo) ON DELETE CASCADE
);

CREATE TABLE GASTO (
    Id_Gasto INT NOT NULL AUTO_INCREMENT,
    codGasto VARCHAR(100) NOT NULL UNIQUE,
    Concepto VARCHAR(200) NOT NULL,
    Monto_total DECIMAL(10, 2) NOT NULL,
    Fecha DATE NOT NULL,
    Id_Grupo INT NOT NULL,
    Id_Categoria INT,
    Id_Usuario_Pagador INT NOT NULL,
    PRIMARY KEY (Id_Gasto),
    CONSTRAINT fk_gasto_grupo FOREIGN KEY (Id_Grupo) REFERENCES GRUPO (Id_Grupo),
    CONSTRAINT fk_gasto_cat FOREIGN KEY (Id_Categoria) REFERENCES CATEGORIA (Id_categoria),
    CONSTRAINT fk_gasto_pagador FOREIGN KEY (Id_Usuario_Pagador) REFERENCES USUARIO (Id_Usuario)
);

CREATE TABLE REPARTO_GASTO (
    ID_Reparto_Gasto INT NOT NULL AUTO_INCREMENT,
    codRepartoGasto VARCHAR(20) NOT NULL UNIQUE,
    Id_Gasto INT NOT NULL,
    Id_Usuario INT NOT NULL,
    Cuota_deuda DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (ID_Reparto_Gasto),
    UNIQUE KEY uq_reparto (Id_Gasto, Id_Usuario),
    CONSTRAINT fk_reparto_gasto FOREIGN KEY (Id_Gasto) REFERENCES GASTO (Id_Gasto),
    CONSTRAINT fk_reparto_usuario FOREIGN KEY (Id_Usuario) REFERENCES USUARIO (Id_Usuario)
);

CREATE TABLE LIQUIDACION (
    Id_liquidacion INT NOT NULL AUTO_INCREMENT,
    codLiquidacion VARCHAR(20) NOT NULL UNIQUE,
    Monto DECIMAL(10, 2) NOT NULL,
    Fecha_movimiento DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Concepto VARCHAR(200),
    id_estado_liq INT NOT NULL,
    Id_Emisor INT NOT NULL,
    Id_Receptor INT NOT NULL,
    Id_Grupo INT NOT NULL,
    PRIMARY KEY (Id_liquidacion),
    CONSTRAINT fk_liq_estado FOREIGN KEY (id_estado_liq) REFERENCES ESTADO_LIQUIDACION (id_estado_liq),
    CONSTRAINT fk_liq_emisor FOREIGN KEY (Id_Emisor) REFERENCES USUARIO (Id_Usuario),
    CONSTRAINT fk_liq_receptor FOREIGN KEY (Id_Receptor) REFERENCES USUARIO (Id_Usuario),
    CONSTRAINT fk_liq_grupo FOREIGN KEY (Id_Grupo) REFERENCES GRUPO (Id_Grupo)
);


INSERT INTO IDIOMA (Nombre, Codigo) VALUES
('Español', 'es'), ('Inglés', 'en'), ('Francés', 'fr'), ('Alemán', 'de'),
('Italiano', 'it'), ('Portugués', 'pt'), ('Chino', 'zh'), ('Japonés', 'ja'),
('Ruso', 'ru'), ('Árabe', 'ar');

INSERT INTO CATEGORIA (Nombre) VALUES
('Comida'), ('Transporte'), ('Alojamiento'), ('Ocio'), ('Otros');

INSERT INTO ESTADO_GRUPO (nombreEstado) VALUES 
('Borrador'), ('Activo'), ('Archivado'), ('Eliminado');

-- Insertamos los estados de tu nueva subtabla
INSERT INTO ESTADO_LIQUIDACION (nombreEstadoLiq) VALUES
('Pendiente'), ('Iniciado'), ('Finalizado');

INSERT INTO USUARIO (codUsuario, Nombre, Email, Password, id_idioma, Alias, IBAN, Fecha_Nacimiento, verificacionActividad) VALUES
('USR001', 'Alejandro García',  'aaa@email.com',      '1234',       1, 'Ale',    'ES1234567890123456789012', '1990-05-15', 1),
('USR002', 'Beatriz López',     'beatriz@email.com',  'hash_pw_2',  1, 'Bea',    'ES9876543210987654321098', '1992-08-22', 1),
('USR003', 'Carlos Martínez',   'carlos@email.com',   'hash_pw_3',  2, 'Carlitos', NULL,                     '1988-11-02', 1),
('USR004', 'Diana Pérez',       'diana@email.com',    'hash_pw_4',  1, 'Di',     'ES1122334455667788990011', '1995-01-30', 1),
('USR005', 'Eduardo Rodríguez', 'edu@email.com',      'hash_pw_5',  1, 'Edu',    NULL,                      '1993-04-12', 1),
('USR006', 'Fernanda Ruiz',     'fer@email.com',      'hash_pw_6',  1, 'Fer',    'ES2233445566778899001122', '1991-07-19', 1),
('USR007', 'Gabriel Sánchez',   'gabriel@email.com',  'hash_pw_7',  2, 'Gabi',   NULL,                      '1989-12-05', 1),
('USR008', 'Helena Castro',     'helena@email.com',   'hash_pw_8',  1, 'Hel',    'ES3344556677889900112233', '1994-03-25', 1),
('USR009', 'Iván Torres',       'ivan@email.com',     'hash_pw_9',  1, 'Iván',   NULL,                      '1996-09-08', 1),
('USR010', 'Julia Morales',     'julia@email.com',    'hash_pw_10', 1, 'Juli',   'ES4455667788990011223344', '1992-02-14', 1),
('USR011', 'Kevin White',       'kevin@email.com',    'hash_pw_11', 2, 'Kev',    NULL,                      '1990-10-10', 1),
('USR012', 'Laura Vidal',       'laura@email.com',    'hash_pw_12', 1, 'Lau',    'ES5566778899001122334455', '1991-06-01', 1),
('USR013', 'Mario Gomez',       'mario@email.com',    'hash_pw_13', 1, 'Mario',  NULL,                      '1987-04-20', 1),
('USR014', 'Natalia Ortiz',     'natalia@email.com',  'hash_pw_14', 1, 'Nati',   'ES6677889900112233445566', '1998-12-31', 1),
('USR015', 'Oscar León',        'oscar@email.com',    'hash_pw_15', 1, 'Oscar',  NULL,                      '1993-05-15', 1),
('USR016', 'Patricia Ramos',    'patricia@email.com', 'hash_pw_16', 1, 'Patri',  'ES7788990011223344556677', '1994-08-11', 1);

INSERT INTO GRUPO (codGrupo, Titulo, Descripcion, Moneda, id_Estado) VALUES
('GRP001', 'Viaje Cantabria',     'Viaje de fin de curso del grupo A',  'EUR', 2),
('GRP002', 'Piso compartido',     'Gastos del piso de estudiantes',     'EUR', 2),
('GRP003', 'Erasmus Berlín',      'Intercambio universitario Berlín',   'EUR', 2),
('GRP004', 'Feria de Sevilla',    'Viaje semana feria',                  'EUR', 2),
('GRP005', 'Cumple Ana',          'Organización cumpleaños sorpresa',    'EUR', 2);

-- Eliminada la columna Rol que quitaste
INSERT INTO MIEMBROS_GRUPO (codMiembrosGrupo, id_Usuario, id_Grupo) VALUES
('MBR001', 1, 1), ('MBR002', 2, 1), ('MBR003', 3, 1), ('MBR004', 4, 1),
('MBR005', 5, 2), ('MBR006', 6, 2), ('MBR007', 7, 2),
('MBR008', 8, 3), ('MBR009', 9, 3), ('MBR010', 10, 3), ('MBR011', 11, 3),
('MBR012', 12, 4), ('MBR013', 13, 4), ('MBR014', 14, 4),
('MBR015', 15, 5), ('MBR016', 16, 5);

INSERT INTO GASTO (codGasto, Concepto, Monto_total, Fecha, id_Grupo, id_Categoria, id_Usuario_Pagador) VALUES
('GST001', 'Cena restaurante',     60.00,  '2025-06-01', 1, 1, 1),
('GST002', 'Gasolina ida',         45.00,  '2025-06-02', 1, 2, 2),
('GST003', 'Alquiler apartamento', 320.00, '2025-06-01', 1, 3, 3),
('GST004', 'Supermercado marzo',   87.50,  '2025-03-05', 2, 1, 5),
('GST005', 'Factura luz febrero',  65.20,  '2025-02-28', 2, 5, 6),
('GST006', 'Internet mensual',     30.00,  '2025-03-01', 2, 5, 7);

INSERT INTO REPARTO_GASTO (codRepartoGasto, id_Gasto, id_Usuario, Cuota_deuda) VALUES
('RPT001', 1, 1, 15.00), ('RPT002', 1, 2, 15.00), ('RPT003', 1, 3, 15.00), ('RPT004', 1, 4, 15.00),
('RPT005', 2, 1, 22.50), ('RPT006', 2, 2, 22.50),
('RPT007', 4, 5, 29.17), ('RPT008', 4, 6, 29.17), ('RPT009', 4, 7, 29.16);

-- Enlazado correctamente a id_estado_liq: 1 (Pendiente), 3 (Finalizado)
INSERT INTO LIQUIDACION (codLiquidacion, Monto, Concepto, id_estado_liq, id_Emisor, id_Receptor, id_Grupo) VALUES
('LIQ001', 15.00, 'Pago cena Cantabria',     3, 2, 1, 1),
('LIQ002', 15.00, 'Pago cena Cantabria',     3, 3, 1, 1),
('LIQ003', 15.00, 'Pago cena Cantabria',     1, 4, 1, 1),
('LIQ004', 29.17, 'Pago supermercado marzo', '1', 6, 5, 2),
('LIQ005', 29.16, 'Pago supermercado marzo', '1', 7, 5, 2);

ALTER TABLE GASTO ADD COLUMN activo BOOLEAN DEFAULT true;

SELECT * FROM USUARIO;
SELECT * FROM IDIOMA;
SELECT * FROM CATEGORIA;
SELECT * FROM GASTO;
SELECT * FROM GRUPO;

-- Obtiene todos los miembros que pertenecen a un grupo
SELECT
    u.id_Usuario,
    u.codUsuario,
    u.Nombre,
    u.Email,
    u.Alias
FROM USUARIO u
INNER JOIN MIEMBROS_GRUPO mg
    ON u.id_Usuario = mg.id_Usuario
WHERE mg.id_Grupo = 3;


-- Muestra todos los gastos pagados por un usuario concreto
SELECT
    g.Id_Gasto,
    g.codGasto,
    g.Concepto,
    g.Monto_total,
    g.Fecha,
    gr.Titulo AS Grupo
FROM GASTO g
INNER JOIN GRUPO gr
    ON g.Id_Grupo = gr.Id_Grupo
WHERE g.Id_Usuario_Pagador = 2;

SELECT
    g.Id_Gasto,
    g.codGasto,
    g.Concepto,
    g.Monto_total,
    g.Fecha,
    u.Nombre AS Pagado_Por,
    c.Nombre AS Categoria
FROM GASTO g
INNER JOIN GRUPO gr
    ON g.Id_Grupo = gr.Id_Grupo
INNER JOIN USUARIO u
    ON g.Id_Usuario_Pagador = u.id_Usuario
LEFT JOIN CATEGORIA c
    ON g.Id_Categoria = c.id_categoria
WHERE gr.Titulo = 'Piso compartido'
ORDER BY g.Fecha;

SELECT 
    g.Id_Grupo,
    g.codGrupo,
    g.Titulo AS Nombre_Grupo,
    g.Descripcion,
    g.Moneda,
    eg.nombreEstado AS Estado_Grupo
FROM GRUPO g
INNER JOIN MIEMBROS_GRUPO mg ON g.Id_Grupo = mg.id_Grupo
INNER JOIN ESTADO_GRUPO eg ON g.Id_Estado = eg.id_Estado
WHERE mg.id_Usuario = 1; -- Cambia el '1' por el ID del usuario que quieras consultar