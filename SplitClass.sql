DROP DATABASE IF EXISTS SplitClass;
CREATE DATABASE IF NOT EXISTS SplitClass;
USE SplitClass;

CREATE TABLE IDIOMA (
    Codigo VARCHAR(5) NOT NULL,
    Nombre VARCHAR(50) NOT NULL,
    PRIMARY KEY (Codigo)
);

CREATE TABLE CATEGORIA (
    Id_categoria INT NOT NULL AUTO_INCREMENT,
    Nombre VARCHAR(50) NOT NULL,
    PRIMARY KEY (Id_categoria)
);

CREATE TABLE ESTADO_GRUPO (
    Id_Estado INT NOT NULL AUTO_INCREMENT,
    Nombre_Estado VARCHAR(20) NOT NULL,
    PRIMARY KEY (Id_Estado)
);

CREATE TABLE USUARIO (
    Id_Usuario INT NOT NULL AUTO_INCREMENT,
    Nombre VARCHAR(100) NOT NULL,
    Email VARCHAR(150) NOT NULL UNIQUE,
    Password VARCHAR(255) NOT NULL,
    Telefono VARCHAR(20),
    Idioma VARCHAR(5) DEFAULT 'es',
    Alias VARCHAR(50),
    IBAN VARCHAR(34),
    Fecha_Creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Fecha_Nacimiento DATE,
    Activo BOOLEAN DEFAULT TRUE, 
    PRIMARY KEY (Id_Usuario),
    CONSTRAINT fk_usuario_idioma FOREIGN KEY (Idioma) REFERENCES IDIOMA (Codigo)
);

CREATE TABLE GRUPO (
    Id_Grupo INT NOT NULL AUTO_INCREMENT,
    Titulo VARCHAR(100) NOT NULL,
    Descripcion TEXT,
    Moneda VARCHAR(10) DEFAULT 'EUR',
    Fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Id_Estado INT NOT NULL,
    PRIMARY KEY (Id_Grupo),
    CONSTRAINT fk_grupo_estado FOREIGN KEY (Id_Estado) REFERENCES ESTADO_GRUPO (Id_Estado)
);

CREATE TABLE MIEMBROS_GRUPO (
    Id_Miembros_Grupo INT NOT NULL AUTO_INCREMENT,
    Id_Usuario INT NOT NULL,
    Id_Grupo INT NOT NULL,
    Rol VARCHAR(20) NOT NULL DEFAULT 'miembro',
    PRIMARY KEY (Id_Miembros_Grupo),
    UNIQUE KEY uq_usuario_grupo (Id_Usuario, Id_Grupo),
    CONSTRAINT fk_mg_usuario FOREIGN KEY (Id_Usuario) REFERENCES USUARIO (Id_Usuario),
    CONSTRAINT fk_mg_grupo FOREIGN KEY (Id_Grupo) REFERENCES GRUPO (Id_Grupo)
);

CREATE TABLE GASTO (
    Id_Gasto INT NOT NULL AUTO_INCREMENT,
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
    Monto DECIMAL(10, 2) NOT NULL,
    Fecha_movimiento DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Concepto VARCHAR(200),
    Estado VARCHAR(20) NOT NULL DEFAULT 'pendiente',
    Id_Emisor INT NOT NULL,
    Id_Receptor INT NOT NULL,
    Id_Grupo INT NOT NULL,
    PRIMARY KEY (Id_liquidacion),
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

INSERT INTO ESTADO_GRUPO (Nombre_Estado) VALUES 
('Borrador'), ('Activo'), ('Archivado'), ('Eliminado');

INSERT INTO USUARIO (Nombre, Email, Password, Idioma, Alias, IBAN, Fecha_Nacimiento, Activo) VALUES
('Alejandro García',  'aaa@email.com',      '1234',       'es', 'Ale',    'ES1234567890123456789012', '1990-05-15', 1),
('Beatriz López',     'beatriz@email.com',  'hash_pw_2',  'es', 'Bea',    'ES9876543210987654321098', '1992-08-22', 1),
('Carlos Martínez',   'carlos@email.com',   'hash_pw_3',  'en', 'Carlitos', NULL,                     '1988-11-02', 1),
('Diana Pérez',       'diana@email.com',    'hash_pw_4',  'es', 'Di',     'ES1122334455667788990011', '1995-01-30', 1),
('Eduardo Rodríguez', 'edu@email.com',      'hash_pw_5',  'es', 'Edu',    NULL,                      '1993-04-12', 1),
('Fernanda Ruiz',     'fer@email.com',      'hash_pw_6',  'es', 'Fer',    'ES2233445566778899001122', '1991-07-19', 1),
('Gabriel Sánchez',   'gabriel@email.com',  'hash_pw_7',  'en', 'Gabi',   NULL,                      '1989-12-05', 1),
('Helena Castro',     'helena@email.com',   'hash_pw_8',  'es', 'Hel',    'ES3344556677889900112233', '1994-03-25', 1),
('Iván Torres',       'ivan@email.com',     'hash_pw_9',  'es', 'Iván',   NULL,                      '1996-09-08', 1),
('Julia Morales',     'julia@email.com',    'hash_pw_10', 'es', 'Juli',   'ES4455667788990011223344', '1992-02-14', 1),
('Kevin White',       'kevin@email.com',    'hash_pw_11', 'en', 'Kev',    NULL,                      '1990-10-10', 1),
('Laura Vidal',       'laura@email.com',    'hash_pw_12', 'es', 'Lau',    'ES5566778899001122334455', '1991-06-01', 1),
('Mario Gomez',       'mario@email.com',    'hash_pw_13', 'es', 'Mario',  NULL,                      '1987-04-20', 1),
('Natalia Ortiz',     'natalia@email.com',  'hash_pw_14', 'es', 'Nati',   'ES6677889900112233445566', '1998-12-31', 1),
('Oscar León',        'oscar@email.com',    'hash_pw_15', 'es', 'Oscar',  NULL,                      '1993-05-15', 1),
('Patricia Ramos',    'patricia@email.com', 'hash_pw_16', 'es', 'Patri',  'ES7788990011223344556677', '1994-08-11', 1),
('Quique Domínguez',  'quique@email.com',   'hash_pw_17', 'es', 'Quique', NULL,                      '1990-01-05', 1),
('Rosa Méndez',       'rosa@email.com',     'hash_pw_18', 'es', 'Rosa',   'ES8899001122334455667788', '1992-11-20', 1),
('Sergio Blanes',     'sergio@email.com',   'hash_pw_19', 'es', 'Sergi',  NULL,                      '1989-03-30', 1),
('Teresa Soler',      'teresa@email.com',   'hash_pw_20', 'es', 'Tere',   'ES9900112233445566778899', '1995-07-07', 1),
('Urbano Lima',       'urbano@email.com',   'hash_pw_21', 'pt', 'Urbi',   NULL,                      '1988-09-12', 1),
('Valeria Vega',      'valeria@email.com',  'hash_pw_22', 'es', 'Val',    'ES0011223344556677889900', '1996-04-22', 1),
('Walter Smith',      'walter@email.com',   'hash_pw_23', 'en', 'Walt',   NULL,                      '1991-12-15', 1),
('Ximena Gil',        'ximena@email.com',   'hash_pw_24', 'es', 'Xime',   'ES1100110011001100110011', '1994-02-28', 1),
('Yago Costa',        'yago@email.com',     'hash_pw_25', 'es', 'Yago',   NULL,                      '1993-10-05', 1),
('Zoe Herrero',       'zoe@email.com',      'hash_pw_26', 'es', 'Zoe',    'ES2200220022002200220022', '1997-06-18', 1),
('Adrian Fluxá',      'adrian@email.com',   'hash_pw_27', 'es', 'Adri',   NULL,                      '1992-01-12', 1),
('Bárbara Cano',      'barbara@email.com',  'hash_pw_28', 'es', 'Barbi',  'ES3300330033003300330033', '1995-03-03', 1),
('Cristian Sol',      'cristian@email.com', 'hash_pw_29', 'es', 'Cris',   NULL,                      '1990-11-11', 1),
('Daniela Paz',       'daniela@email.com',  'hash_pw_30', 'es', 'Dani',   'ES4400440044004400440044', '1994-09-25', 1);

-- 5. GRUPOS (Con Id_Estado = 2 para 'Activo')
INSERT INTO GRUPO (Titulo, Descripcion, Moneda, Id_Estado) VALUES
('Viaje Cantabria',     'Viaje de fin de curso del grupo A',  'EUR', 2),
('Piso compartido',     'Gastos del piso de estudiantes',     'EUR', 2),
('Erasmus Berlín',      'Intercambio universitario Berlín',   'EUR', 2),
('Feria de Sevilla',    'Viaje semana feria',                  'EUR', 2),
('Cumple Ana',          'Organización cumpleaños sorpresa',    'EUR', 2),
('Road trip Portugal',  'Viaje en coche por Portugal',         'EUR', 2),
('Cena navidad',        'Cena de empresa navideña',            'EUR', 2),
('Esquí Andorra',       'Fin de semana en la nieve',          'EUR', 2);

INSERT INTO MIEMBROS_GRUPO (Id_Usuario, Id_Grupo, Rol) VALUES
(1, 1, 'admin'), (2, 1, 'miembro'), (3, 1, 'miembro'), (4, 1, 'miembro'),
(5, 2, 'admin'), (6, 2, 'miembro'), (7, 2, 'miembro'),
(8, 3, 'admin'), (9, 3, 'miembro'), (10, 3, 'miembro'), (11, 3, 'miembro'),
(12, 4, 'admin'), (13, 4, 'miembro'), (14, 4, 'miembro'),
(15, 5, 'admin'), (16, 5, 'miembro');

INSERT INTO GASTO (Concepto, Monto_total, Fecha, Id_Grupo, Id_Categoria, Id_Usuario_Pagador) VALUES
('Cena restaurante',     60.00,  '2025-06-01', 1, 1, 1),
('Gasolina ida',         45.00,  '2025-06-02', 1, 2, 2),
('Alquiler apartamento', 320.00, '2025-06-01', 1, 3, 3),
('Supermercado marzo',   87.50,  '2025-03-05', 2, 1, 5),
('Factura luz febrero',  65.20,  '2025-02-28', 2, 5, 6),
('Internet mensual',     30.00,  '2025-03-01', 2, 5, 7),
('Vuelo Berlín',         210.00, '2025-09-10', 3, 2, 8),
('Hotel 3 noches',       270.00, '2025-09-11', 3, 3, 9),
('Entradas feria',       48.00,  '2025-04-15', 4, 4, 12),
('Alquiler coche',       150.00, '2025-04-14', 4, 2, 13);

INSERT INTO REPARTO_GASTO (Id_Gasto, Id_Usuario, Cuota_deuda) VALUES
(1, 1, 15.00), (1, 2, 15.00), (1, 3, 15.00), (1, 4, 15.00),
(2, 1, 22.50), (2, 2, 22.50),
(4, 5, 29.17), (4, 6, 29.17), (4, 7, 29.16),
(5, 5, 21.73), (5, 6, 21.73), (5, 7, 21.74),
(7, 8, 70.00), (7, 9, 70.00);

INSERT INTO LIQUIDACION (Monto, Concepto, Estado, Id_Emisor, Id_Receptor, Id_Grupo) VALUES
(15.00,  'Pago cena Cantabria',      'completado', 2,  1, 1),
(15.00,  'Pago cena Cantabria',      'completado', 3,  1, 1),
(15.00,  'Pago cena Cantabria',      'pendiente',  4,  1, 1),
(29.17,  'Pago supermercado marzo',  'pendiente',  6,  5, 2),
(29.16,  'Pago supermercado marzo',  'pendiente',  7,  5, 2),
(70.00,  'Pago vuelo Berlín',        'pendiente',  9,  8, 3);

SELECT * FROM USUARIO;