CREATE DATABASE IF NOT EXISTS SplitClass;
USE SplitClass;
 

CREATE TABLE CATEGORIA (
    Id_categoria INT          NOT NULL AUTO_INCREMENT,
    Nombre       VARCHAR(50)  NOT NULL,
    Icono        VARCHAR(100),
    PRIMARY KEY (Id_categoria)
);
 
CREATE TABLE USUARIO (
    Id_Usuario INT          NOT NULL AUTO_INCREMENT,
    Nombre     VARCHAR(100) NOT NULL,
    Email      VARCHAR(150) NOT NULL UNIQUE,
    DNI        VARCHAR(20)  UNIQUE,
    Lenguaje   VARCHAR(10)  DEFAULT 'es',
    Alias      VARCHAR(50),
    IBAN       VARCHAR(34),
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
    Id_Gasto          INT            NOT NULL AUTO_INCREMENT,
    Concepto          VARCHAR(200)   NOT NULL,
    Monto_total       DECIMAL(10, 2) NOT NULL,
    Fecha             DATE           NOT NULL,
    Id_Grupo          INT            NOT NULL,
    Id_Categoria      INT,
    Id_Usuario_Pagador INT           NOT NULL,
    PRIMARY KEY (Id_Gasto),
    CONSTRAINT fk_gasto_grupo    FOREIGN KEY (Id_Grupo)           REFERENCES GRUPO    (Id_Grupo),
    CONSTRAINT fk_gasto_cat      FOREIGN KEY (Id_Categoria)       REFERENCES CATEGORIA(Id_categoria),
    CONSTRAINT fk_gasto_pagador  FOREIGN KEY (Id_Usuario_Pagador) REFERENCES USUARIO  (Id_Usuario)
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
    Id_liquidacion  INT            NOT NULL AUTO_INCREMENT,
    Monto           DECIMAL(10, 2) NOT NULL,
    Fecha_movimiento DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Concepto        VARCHAR(200),
    Estado          VARCHAR(20)    NOT NULL DEFAULT 'pendiente', 
    Id_Emisor       INT            NOT NULL,   
    Id_Receptor     INT            NOT NULL,   
    Id_Grupo        INT            NOT NULL,
    PRIMARY KEY (Id_liquidacion),
    CONSTRAINT fk_liq_emisor   FOREIGN KEY (Id_Emisor)  REFERENCES USUARIO (Id_Usuario),
    CONSTRAINT fk_liq_receptor FOREIGN KEY (Id_Receptor) REFERENCES USUARIO (Id_Usuario),
    CONSTRAINT fk_liq_grupo    FOREIGN KEY (Id_Grupo)    REFERENCES GRUPO   (Id_Grupo)
);