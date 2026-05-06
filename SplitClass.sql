DROP DATABASE IF EXISTS SplitClass;
CREATE DATABASE IF NOT EXISTS SplitClass;
USE SplitClass;
 

CREATE TABLE CATEGORIA (
    Id_categoria INT          NOT NULL AUTO_INCREMENT,
    Nombre       VARCHAR(50)  NOT NULL,
    Icono        VARCHAR(100),
    PRIMARY KEY (Id_categoria)
);
 
CREATE TABLE USUARIO (
    Id_Usuario      INT          NOT NULL AUTO_INCREMENT,
    Nombre          VARCHAR(100) NOT NULL,
    Email           VARCHAR(150) NOT NULL UNIQUE,
    Password        VARCHAR(255) NOT NULL,        
    Idioma        VARCHAR(10)  DEFAULT 'es',
    Alias           VARCHAR(50),
    IBAN            VARCHAR(34),
    Fecha_Creacion  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Fecha_Nacimiento DATE,
    PRIMARY KEY (Id_Usuario)
);

CREATE TABLE GRUPO (
    Id_Grupo          INT          NOT NULL AUTO_INCREMENT,
    Titulo            VARCHAR(100) NOT NULL,
    Descripcion       TEXT,
    Moneda            VARCHAR(10)  DEFAULT 'EUR',
    Fecha_creacion    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Fecha_eliminacion DATETIME,                        
    PRIMARY KEY (Id_Grupo)
);

CREATE TABLE MIEMBROS_GRUPO (
    Id_Miembros_Grupo INT         NOT NULL AUTO_INCREMENT,
    Id_Usuario        INT         NOT NULL,
    Id_Grupo          INT         NOT NULL,
    Rol               VARCHAR(20) NOT NULL DEFAULT 'miembro',
    PRIMARY KEY (Id_Miembros_Grupo),
    UNIQUE KEY uq_usuario_grupo (Id_Usuario, Id_Grupo),
    CONSTRAINT fk_mg_usuario FOREIGN KEY (Id_Usuario) REFERENCES USUARIO (Id_Usuario),
    CONSTRAINT fk_mg_grupo   FOREIGN KEY (Id_Grupo)   REFERENCES GRUPO   (Id_Grupo)
);

CREATE TABLE GASTO (
    Id_Gasto           INT            NOT NULL AUTO_INCREMENT,
    Concepto           VARCHAR(200)   NOT NULL,
    Monto_total        DECIMAL(10, 2) NOT NULL,
    Fecha              DATE           NOT NULL,
    Id_Grupo           INT            NOT NULL,
    Id_Categoria       INT,
    Id_Usuario_Pagador INT            NOT NULL,
    PRIMARY KEY (Id_Gasto),
    CONSTRAINT fk_gasto_grupo   FOREIGN KEY (Id_Grupo)           REFERENCES GRUPO    (Id_Grupo),
    CONSTRAINT fk_gasto_cat     FOREIGN KEY (Id_Categoria)       REFERENCES CATEGORIA(Id_categoria),
    CONSTRAINT fk_gasto_pagador FOREIGN KEY (Id_Usuario_Pagador) REFERENCES USUARIO  (Id_Usuario)
);
 
CREATE TABLE REPARTO_GASTO (
    ID_Reparto_Gasto INT            NOT NULL AUTO_INCREMENT,
    Id_Gasto         INT            NOT NULL,
    Id_Usuario       INT            NOT NULL,
    Cuota_deuda      DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (ID_Reparto_Gasto),
    UNIQUE KEY uq_reparto (Id_Gasto, Id_Usuario),
    CONSTRAINT fk_reparto_gasto   FOREIGN KEY (Id_Gasto)   REFERENCES GASTO   (Id_Gasto),
    CONSTRAINT fk_reparto_usuario FOREIGN KEY (Id_Usuario) REFERENCES USUARIO (Id_Usuario)
);

CREATE TABLE LIQUIDACION (
    Id_liquidacion   INT            NOT NULL AUTO_INCREMENT,
    Monto            DECIMAL(10, 2) NOT NULL,
    Fecha_movimiento DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Concepto         VARCHAR(200),
    Estado           VARCHAR(20)    NOT NULL DEFAULT 'pendiente',
    Id_Emisor        INT            NOT NULL,
    Id_Receptor      INT            NOT NULL,
    Id_Grupo         INT            NOT NULL,
    PRIMARY KEY (Id_liquidacion),
    CONSTRAINT fk_liq_emisor   FOREIGN KEY (Id_Emisor)   REFERENCES USUARIO (Id_Usuario),
    CONSTRAINT fk_liq_receptor FOREIGN KEY (Id_Receptor) REFERENCES USUARIO (Id_Usuario),
    CONSTRAINT fk_liq_grupo    FOREIGN KEY (Id_Grupo)    REFERENCES GRUPO   (Id_Grupo)
);

CREATE TABLE IDIOMA (
    Id_Idioma INT NOT NULL AUTO_INCREMENT,
    Nombre    VARCHAR(50) NOT NULL,
    Codigo    VARCHAR(5) NOT NULL UNIQUE,
    PRIMARY KEY (Id_Idioma)
);

INSERT INTO IDIOMA (Nombre, Codigo) VALUES 
('Español', 'ES'),
('Inglés', 'EN'),
('Francés', 'FR'),
('Alemán', 'DE'),
('Italiano', 'IT'),
('Portugués', 'PT'),
('Chino', 'ZH'),
('Japonés', 'JA'),
('Ruso', 'RU'),
('Árabe', 'AR');

INSERT INTO USUARIO (Nombre, Email, Password, Idioma, Alias, IBAN, Fecha_Nacimiento) VALUES 
('Alejandro García', 'aaa@email.com', '1234', 'es', 'Ale', 'ES1234567890123456789012', '1990-05-15'),
('Beatriz López', 'beatriz@email.com', 'hash_pw_2', 'es', 'Bea', 'ES9876543210987654321098', '1992-08-22'),
('Carlos Martínez', 'carlos@email.com', 'hash_pw_3', 'en', 'Carlitos', NULL, '1988-11-02'),
('Diana Pérez', 'diana@email.com', 'hash_pw_4', 'es', 'Di', 'ES1122334455667788990011', '1995-01-30'),
('Eduardo Rodríguez', 'edu@email.com', 'hash_pw_5', 'es', 'Edu', NULL, '1993-04-12'),
('Fernanda Ruiz', 'fer@email.com', 'hash_pw_6', 'es', 'Fer', 'ES2233445566778899001122', '1991-07-19'),
('Gabriel Sánchez', 'gabriel@email.com', 'hash_pw_7', 'en', 'Gabi', NULL, '1989-12-05'),
('Helena Castro', 'helena@email.com', 'hash_pw_8', 'es', 'Hel', 'ES3344556677889900112233', '1994-03-25'),
('Iván Torres', 'ivan@email.com', 'hash_pw_9', 'es', 'Iván', NULL, '1996-09-08'),
('Julia Morales', 'julia@email.com', 'hash_pw_10', 'es', 'Juli', 'ES4455667788990011223344', '1992-02-14'),
('Kevin White', 'kevin@email.com', 'hash_pw_11', 'en', 'Kev', NULL, '1990-10-10'),
('Laura Vidal', 'laura@email.com', 'hash_pw_12', 'es', 'Lau', 'ES5566778899001122334455', '1991-06-01'),
('Mario Gomez', 'mario@email.com', 'hash_pw_13', 'es', 'Mario', NULL, '1987-04-20'),
('Natalia Ortiz', 'natalia@email.com', 'hash_pw_14', 'es', 'Nati', 'ES6677889900112233445566', '1998-12-31'),
('Oscar León', 'oscar@email.com', 'hash_pw_15', 'es', 'Oscar', NULL, '1993-05-15'),
('Patricia Ramos', 'patricia@email.com', 'hash_pw_16', 'es', 'Patri', 'ES7788990011223344556677', '1994-08-11'),
('Quique Domínguez', 'quique@email.com', 'hash_pw_17', 'es', 'Quique', NULL, '1990-01-05'),
('Rosa Méndez', 'rosa@email.com', 'hash_pw_18', 'es', 'Rosa', 'ES8899001122334455667788', '1992-11-20'),
('Sergio Blanes', 'sergio@email.com', 'hash_pw_19', 'es', 'Sergi', NULL, '1989-03-30'),
('Teresa Soler', 'teresa@email.com', 'hash_pw_20', 'es', 'Tere', 'ES9900112233445566778899', '1995-07-07'),
('Urbano Lima', 'urbano@email.com', 'hash_pw_21', 'pt', 'Urbi', NULL, '1988-09-12'),
('Valeria Vega', 'valeria@email.com', 'hash_pw_22', 'es', 'Val', 'ES0011223344556677889900', '1996-04-22'),
('Walter Smith', 'walter@email.com', 'hash_pw_23', 'en', 'Walt', NULL, '1991-12-15'),
('Ximena Gil', 'ximena@email.com', 'hash_pw_24', 'es', 'Xime', 'ES1100110011001100110011', '1994-02-28'),
('Yago Costa', 'yago@email.com', 'hash_pw_25', 'es', 'Yago', NULL, '1993-10-05'),
('Zoe Herrero', 'zoe@email.com', 'hash_pw_26', 'es', 'Zoe', 'ES2200220022002200220022', '1997-06-18'),
('Adrian Fluxá', 'adrian@email.com', 'hash_pw_27', 'es', 'Adri', NULL, '1992-01-12'),
('Bárbara Cano', 'barbara@email.com', 'hash_pw_28', 'es', 'Barbi', 'ES3300330033003300330033', '1995-03-03'),
('Cristian Sol', 'cristian@email.com', 'hash_pw_29', 'es', 'Cris', NULL, '1990-11-11'),
('Daniela Paz', 'daniela@email.com', 'hash_pw_30', 'es', 'Dani', 'ES4400440044004400440044', '1994-09-25');