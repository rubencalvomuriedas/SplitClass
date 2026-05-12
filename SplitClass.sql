DROP DATABASE IF EXISTS SplitClass;
CREATE DATABASE IF NOT EXISTS SplitClass;
USE SplitClass;


CREATE TABLE idiomas (
    id_idioma INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    codigo VARCHAR(10) NOT NULL UNIQUE
);

CREATE TABLE roles_miembro (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    nombre_rol VARCHAR(20) NOT NULL UNIQUE 
);

CREATE TABLE estados_liquidacion (
    id_estado INT AUTO_INCREMENT PRIMARY KEY,
    nombre_estado VARCHAR(20) NOT NULL UNIQUE 
);

CREATE TABLE categorias (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    cod_categoria VARCHAR(20) UNIQUE NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    icono VARCHAR(50)
);


CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    cod_usuario VARCHAR(20) UNIQUE NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    telefono VARCHAR(20),
    id_idioma INT,
    alias VARCHAR(50),
    iban VARCHAR(34),
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_nacimiento DATE,
    FOREIGN KEY (id_idioma) REFERENCES idiomas(id_idioma) ON DELETE SET NULL
);

CREATE TABLE grupos (
    id_grupo INT AUTO_INCREMENT PRIMARY KEY,
    cod_grupo VARCHAR(20) UNIQUE NOT NULL,
    titulo VARCHAR(100) NOT NULL,
    descripcion TEXT,
    moneda VARCHAR(10) DEFAULT 'EUR',
    fecha_creacion DATE NOT NULL,
    fecha_eliminacion DATE DEFAULT NULL
);

CREATE TABLE miembros_grupo (
    id_miembros INT AUTO_INCREMENT PRIMARY KEY,
    cod_miembros_grupo VARCHAR(20) UNIQUE NOT NULL,
    id_usuario INT NOT NULL,
    id_grupo INT NOT NULL,
    id_rol INT NOT NULL, -- Relación con el "Enum" de roles
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_grupo) REFERENCES grupos(id_grupo) ON DELETE CASCADE,
    FOREIGN KEY (id_rol) REFERENCES roles_miembro(id_rol)
);

CREATE TABLE gastos (
    id_gasto INT AUTO_INCREMENT PRIMARY KEY,
    cod_gasto VARCHAR(20) UNIQUE NOT NULL,
    concepto VARCHAR(150) NOT NULL,
    monto_total DECIMAL(15, 2) NOT NULL,
    fecha DATE NOT NULL,
    id_grupo INT NOT NULL,
    id_categoria INT,
    id_usuario_pagador INT NOT NULL,
    FOREIGN KEY (id_grupo) REFERENCES grupos(id_grupo) ON DELETE CASCADE,
    FOREIGN KEY (id_categoria) REFERENCES categorias(id_categoria) ON DELETE SET NULL,
    FOREIGN KEY (id_usuario_pagador) REFERENCES usuarios(id_usuario)
);

CREATE TABLE reparto_gasto (
    id_reparto_gasto INT AUTO_INCREMENT PRIMARY KEY,
    cod_reparto_gasto VARCHAR(20) UNIQUE NOT NULL,
    id_gasto INT NOT NULL,
    id_usuario INT NOT NULL,
    cuota_deuda DECIMAL(15, 2) NOT NULL,
    FOREIGN KEY (id_gasto) REFERENCES gastos(id_gasto) ON DELETE CASCADE,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE
);

CREATE TABLE liquidaciones (
    id_liquidacion INT AUTO_INCREMENT PRIMARY KEY,
    cod_liquidacion VARCHAR(20) UNIQUE NOT NULL,
    monto DECIMAL(15, 2) NOT NULL,
    fecha_movimiento DATE NOT NULL,
    concepto VARCHAR(150),
    id_estado INT NOT NULL,
    id_emisor INT NOT NULL,
    id_receptor INT NOT NULL,
    id_grupo INT NOT NULL,
    FOREIGN KEY (id_estado) REFERENCES estados_liquidacion(id_estado),
    FOREIGN KEY (id_emisor) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_receptor) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_grupo) REFERENCES grupos(id_grupo) ON DELETE CASCADE
);


INSERT INTO idiomas (nombre, codigo) VALUES 
('Español', 'es'), ('Inglés', 'en'), ('Francés', 'fr'), ('Alemán', 'de'), ('Italiano', 'it'), 
('Portugués', 'pt'), ('Chino', 'zh'), ('Japonés', 'ja'), ('Ruso', 'ru'), ('Árabe', 'ar');

INSERT INTO roles_miembro (nombre_rol) VALUES ('admin'), ('miembro');

INSERT INTO estados_liquidacion (nombre_estado) VALUES ('pendiente'), ('completado');

INSERT INTO categorias (cod_categoria, nombre, icono) VALUES 
('CAT001', 'Comida', 'ic_food'), ('CAT002', 'Transporte', 'ic_transport'), 
('CAT003', 'Alojamiento', 'ic_hotel'), ('CAT004', 'Ocio', 'ic_leisure'), ('CAT005', 'Otros', 'ic_other');

INSERT INTO usuarios (cod_usuario, nombre, email, password, id_idioma, alias, iban, fecha_nacimiento) VALUES
('USR001', 'Alejandro García', 'aaa@email.com', '1234', 1, 'Ale', 'ES1234567890123456789012', '1990-05-15'),
('USR002', 'Beatriz López', 'beatriz@email.com', 'hash_pw_2', 1, 'Bea', 'ES9876543210987654321098', '1992-08-22'),
('USR003', 'Carlos Martínez', 'carlos@email.com', 'hash_pw_3', 2, 'Carlitos', NULL, '1988-11-02'),
('USR004', 'Diana Pérez', 'diana@email.com', 'hash_pw_4', 1, 'Di', 'ES1122334455667788990011', '1995-01-30'),
('USR005', 'Eduardo Rodríguez', 'edu@email.com', 'hash_pw_5', 1, 'Edu', NULL, '1993-04-12'),
('USR006', 'Fernanda Ruiz', 'fer@email.com', 'hash_pw_6', 1, 'Fer', 'ES2233445566778899001122', '1991-07-19'),
('USR007', 'Gabriel Sánchez', 'gabriel@email.com', 'hash_pw_7', 2, 'Gabi', NULL, '1989-12-05'),
('USR008', 'Helena Castro', 'helena@email.com', 'hash_pw_8', 1, 'Hel', 'ES3344556677889900112233', '1994-03-25'),
('USR009', 'Iván Torres', 'ivan@email.com', 'hash_pw_9', 1, 'Iván', NULL, '1996-09-08'),
('USR010', 'Julia Morales', 'julia@email.com', 'hash_pw_10', 1, 'Juli', 'ES4455667788990011223344', '1992-02-14'),
('USR011', 'Kevin White', 'kevin@email.com', 'hash_pw_11', 2, 'Kev', NULL, '1990-10-10'),
('USR012', 'Laura Vidal', 'laura@email.com', 'hash_pw_12', 1, 'Lau', 'ES5566778899001122334455', '1991-06-01'),
('USR013', 'Mario Gomez', 'mario@email.com', 'hash_pw_13', 1, 'Mario', NULL, '1987-04-20'),
('USR014', 'Natalia Ortiz', 'natalia@email.com', 'hash_pw_14', 1, 'Nati', 'ES6677889900112233445566', '1998-12-31'),
('USR015', 'Oscar León', 'oscar@email.com', 'hash_pw_15', 1, 'Oscar', NULL, '1993-05-15'),
('USR016', 'Patricia Ramos', 'patricia@email.com', 'hash_pw_16', 1, 'Patri', 'ES7788990011223344556677', '1994-08-11'),
('USR017', 'Quique Domínguez', 'quique@email.com', 'hash_pw_17', 1, 'Quique', NULL, '1990-01-05'),
('USR018', 'Rosa Méndez', 'rosa@email.com', 'hash_pw_18', 1, 'Rosa', 'ES8899001122334455667788', '1992-11-20'),
('USR019', 'Sergio Blanes', 'sergio@email.com', 'hash_pw_19', 1, 'Sergi', NULL, '1989-03-30'),
('USR020', 'Teresa Soler', 'teresa@email.com', 'hash_pw_20', 1, 'Tere', 'ES9900112233445566778899', '1995-07-07'),
('USR021', 'Urbano Lima', 'urbano@email.com', 'hash_pw_21', 6, 'Urbi', NULL, '1988-09-12'),
('USR022', 'Valeria Vega', 'valeria@email.com', 'hash_pw_22', 1, 'Val', 'ES0011223344556677889900', '1996-04-22'),
('USR023', 'Walter Smith', 'walter@email.com', 'hash_pw_23', 2, 'Walt', NULL, '1991-12-15'),
('USR024', 'Ximena Gil', 'ximena@email.com', 'hash_pw_24', 1, 'Xime', 'ES1100110011001100110011', '1994-02-28'),
('USR025', 'Yago Costa', 'yago@email.com', 'hash_pw_25', 1, 'Yago', NULL, '1993-10-05'),
('USR026', 'Zoe Herrero', 'zoe@email.com', 'hash_pw_26', 1, 'Zoe', 'ES2200220022002200220022', '1997-06-18'),
('USR027', 'Adrian Fluxá', 'adrian@email.com', 'hash_pw_27', 1, 'Adri', NULL, '1992-01-12'),
('USR028', 'Bárbara Cano', 'barbara@email.com', 'hash_pw_28', 1, 'Barbi', 'ES3300330033003300330033', '1995-03-03'),
('USR029', 'Cristian Sol', 'cristian@email.com', 'hash_pw_29', 1, 'Cris', NULL, '1990-11-11'),
('USR030', 'Daniela Paz', 'daniela@email.com', 'hash_pw_30', 1, 'Dani', 'ES4400440044004400440044', '1994-09-25');

INSERT INTO grupos (cod_grupo, titulo, descripcion, moneda, fecha_creacion) VALUES
('GRP001', 'Viaje Cantabria', 'Viaje de fin de curso del grupo A', 'EUR', '2025-01-10'),
('GRP002', 'Piso compartido', 'Gastos del piso de estudiantes', 'EUR', '2025-01-15'),
('GRP003', 'Erasmus Berlín', 'Intercambio universitario Berlín', 'EUR', '2025-02-01'),
('GRP004', 'Feria de Sevilla', 'Viaje semana feria', 'EUR', '2025-03-20'),
('GRP005', 'Cumple Ana', 'Organización cumpleaños sorpresa', 'EUR', '2025-04-05'),
('GRP006', 'Road trip Portugal', 'Viaje en coche por Portugal', 'EUR', '2025-05-10'),
('GRP007', 'Cena navidad', 'Cena de empresa navideña', 'EUR', '2025-11-20'),
('GRP008', 'Esquí Andorra', 'Fin de semana en la nieve', 'EUR', '2025-12-01');

INSERT INTO miembros_grupo (cod_miembros_grupo, id_usuario, id_grupo, id_rol) VALUES
('MBR001', 1, 1, 1), ('MBR002', 2, 1, 2), ('MBR003', 3, 1, 2), ('MBR004', 4, 1, 2),
('MBR005', 5, 2, 1), ('MBR006', 6, 2, 2), ('MBR007', 7, 2, 2),
('MBR008', 8, 3, 1), ('MBR009', 9, 3, 2), ('MBR010', 10, 3, 2), ('MBR011', 11, 3, 2),
('MBR012', 12, 4, 1), ('MBR013', 13, 4, 2), ('MBR014', 14, 4, 2),
('MBR015', 15, 5, 1), ('MBR016', 16, 5, 2);


INSERT INTO gastos (cod_gasto, concepto, monto_total, fecha, id_grupo, id_categoria, id_usuario_pagador) VALUES
('GST001', 'Cena restaurante', 60.00, '2025-06-01', 1, 1, 1),
('GST002', 'Gasolina ida', 45.00, '2025-06-02', 1, 2, 2),
('GST003', 'Alquiler apartamento', 320.00, '2025-06-01', 1, 3, 3),
('GST004', 'Supermercado marzo', 87.50, '2025-03-05', 2, 1, 5),
('GST005', 'Factura luz febrero', 65.20, '2025-02-28', 2, 5, 6),
('GST006', 'Internet mensual', 30.00, '2025-03-01', 2, 5, 7),
('GST007', 'Vuelo Berlín', 210.00, '2025-09-10', 3, 2, 8),
('GST008', 'Hotel 3 noches', 270.00, '2025-09-11', 3, 3, 9),
('GST009', 'Entradas feria', 48.00, '2025-04-15', 4, 4, 12),
('GST010', 'Alquiler coche', 150.00, '2025-04-14', 4, 2, 13);

INSERT INTO reparto_gasto (cod_reparto_gasto, id_gasto, id_usuario, cuota_deuda) VALUES
('RPT001', 1, 1, 15.00), ('RPT002', 1, 2, 15.00), ('RPT003', 1, 3, 15.00), ('RPT004', 1, 4, 15.00),
('RPT005', 2, 1, 22.50), ('RPT006', 2, 2, 22.50),
('RPT007', 4, 5, 29.17), ('RPT008', 4, 6, 29.17), ('RPT009', 4, 7, 29.16),
('RPT010', 5, 5, 21.73), ('RPT011', 5, 6, 21.73), ('RPT012', 5, 7, 21.74),
('RPT013', 7, 8, 70.00), ('RPT014', 7, 9, 70.00);

INSERT INTO liquidaciones (cod_liquidacion, monto, fecha_movimiento, concepto, id_estado, id_emisor, id_receptor, id_grupo) VALUES
('LIQ001', 15.00, '2025-06-05', 'Pago cena Cantabria', 2, 2, 1, 1),
('LIQ002', 15.00, '2025-06-06', 'Pago cena Cantabria', 2, 3, 1, 1),
('LIQ003', 15.00, '2025-06-07', 'Pago cena Cantabria', 1, 4, 1, 1),
('LIQ004', 29.17, '2025-03-10', 'Pago supermercado marzo', 1, 6, 5, 2),
('LIQ005', 29.16, '2025-03-11', 'Pago supermercado marzo', 1, 7, 5, 2),
('LIQ006', 70.00, '2025-09-15', 'Pago vuelo Berlín', 1, 9, 8, 3);

-- ============================================================
-- CONSULTAS
-- ============================================================

-- 1. Todos los gastos de un grupo con el nombre del pagador y la categoría
--    Útil para mostrar el listado de gastos en la pantalla de detalle de grupo
SELECT 
    g.concepto, 
    g.monto_total, 
    g.fecha, 
    u.nombre AS pagador, 
    c.nombre AS categoria
FROM gastos g
JOIN usuarios u ON g.id_usuario_pagador = u.id_usuario
JOIN categorias c ON g.id_categoria = c.id_categoria
WHERE g.id_grupo = 1
ORDER BY g.fecha DESC;

-- 2. Deuda total pendiente de cada usuario en un grupo
--    Útil para el panel de balances: cuánto debe cada miembro en total
SELECT 
    u.nombre, 
    SUM(rg.cuota_deuda) AS total_deuda
FROM reparto_gasto rg
JOIN usuarios u ON rg.id_usuario = u.id_usuario
JOIN gastos g ON rg.id_gasto = g.id_gasto
WHERE g.id_grupo = 1
GROUP BY u.id_usuario, u.nombre
ORDER BY total_deuda DESC;

-- 3. Liquidaciones pendientes de un grupo con nombre de emisor y receptor
--    Útil para mostrar quién debe pagar a quién y cuánto
SELECT 
    e.nombre AS emisor, 
    r.nombre AS receptor, 
    l.monto, 
    l.concepto, 
    l.estado 
FROM liquidaciones l
JOIN usuarios e ON l.id_emisor = e.id_usuario
JOIN usuarios r ON l.id_receptor = r.id_usuario
WHERE l.id_grupo = 1 
  AND l.estado = 0;

-- 4. Gasto total por categoría en todos los grupos
--    Útil para la pantalla de estadísticas con el gráfico circular
SELECT 
    c.nombre AS categoria, 
    COUNT(g.id_gasto) AS num_gastos, 
    SUM(g.monto_total) AS total_gastado
FROM gastos g
JOIN categorias c ON g.id_categoria = c.id_categoria
GROUP BY c.id_categoria, c.nombre
ORDER BY total_gastado DESC;

-- 5. Grupos a los que pertenece un usuario con su rol
--    Útil para mostrar la lista de grupos del usuario en la pantalla principal
SELECT 
    gr.titulo, 
    gr.descripcion, 
    gr.moneda, 
    mg.rol
FROM miembros_grupo mg
JOIN grupos gr ON mg.id_grupo = gr.id_grupo
WHERE mg.id_usuario = 1 
  AND gr.fecha_eliminacion IS NULL
ORDER BY gr.fecha_creacion DESC;

-- 6. Usuarios que aún no han pagado su parte en un gasto concreto
--    Útil para recordatorios o marcar deudas pendientes
SELECT 
    u.nombre, 
    rg.cuota_deuda
FROM reparto_gasto rg
JOIN usuarios u ON rg.id_usuario = u.id_usuario
WHERE rg.id_gasto = 1
  AND rg.id_usuario NOT IN (
      SELECT id_emisor 
      FROM liquidaciones 
      WHERE id_grupo = 1 
        AND estado = 1 -- 1 representa 'completado'
  );

-- 7. Gasto total por mes en un grupo (para el gráfico de barras mensual)
SELECT 
    DATE_FORMAT(g.fecha, '%Y-%m') AS mes, 
    SUM(g.monto_total) AS total_mes
FROM gastos g
WHERE g.id_grupo = 1
GROUP BY mes
ORDER BY mes ASC;

-- 8. Miembros de un grupo con el total que ha pagado cada uno
--    Útil para ver quién ha adelantado más dinero
SELECT 
    u.nombre, 
    COALESCE(SUM(g.monto_total), 0) AS total_pagado
FROM miembros_grupo mg
JOIN usuarios u ON mg.id_usuario = u.id_usuario
LEFT JOIN gastos g ON g.id_usuario_pagador = u.id_usuario 
                  AND g.id_grupo = mg.id_grupo
WHERE mg.id_grupo = 1
GROUP BY u.id_usuario, u.nombre
ORDER BY total_pagado DESC;

-- 9. Balance neto de cada usuario en un grupo (pagado − debe)
--    Positivo = le deben dinero | Negativo = debe dinero
SELECT 
    u.nombre, 
    COALESCE(pagos.total_pagado, 0) AS total_pagado,
    COALESCE(deudas.total_debe, 0) AS total_debe,
    (COALESCE(pagos.total_pagado, 0) - COALESCE(deudas.total_debe, 0)) AS balance_neto
FROM miembros_grupo mg
JOIN usuarios u ON mg.id_usuario = u.id_usuario
LEFT JOIN (
    SELECT id_usuario_pagador, id_grupo, SUM(monto_total) AS total_pagado
    FROM gastos 
    GROUP BY id_usuario_pagador, id_grupo
) pagos ON pagos.id_usuario_pagador = u.id_usuario AND pagos.id_grupo = mg.id_grupo
LEFT JOIN (
    SELECT rg.id_usuario, g.id_grupo, SUM(rg.cuota_deuda) AS total_debe
    FROM reparto_gasto rg
    JOIN gastos g ON rg.id_gasto = g.id_gasto
    GROUP BY rg.id_usuario, g.id_grupo
) deudas ON deudas.id_usuario = u.id_usuario AND deudas.id_grupo = mg.id_grupo
WHERE mg.id_grupo = 1
ORDER BY balance_neto DESC;


SELECT 
    'Gasto' AS tipo, 
    g.concepto, 
    g.monto_total AS importe, 
    g.fecha AS fecha, 
    gr.titulo AS grupo
FROM gastos g
JOIN grupos gr ON g.id_grupo = gr.id_grupo
WHERE g.id_usuario_pagador = 1

UNION ALL

SELECT 
    'Liquidacion' AS tipo, 
    l.concepto, 
    l.monto AS importe, 
    l.fecha_movimiento AS fecha, 
    gr.titulo AS grupo
FROM liquidaciones l
JOIN grupos gr ON l.id_grupo = gr.id_grupo
WHERE l.id_emisor = 1

ORDER BY fecha DESC;