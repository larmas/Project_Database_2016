SET search_path to ciudaddelosniños;

-- Carga de datos a la tabla persona
INSERT INTO persona 
(dni, nombre, apellido, fecha_nac, direccion, cod_postal, e_mail, facebook, edad, tel_fijo, tel_cel)
VALUES (37108050, 'Matias', 'Pollo', '1995/04/27', 'Santa Rosa 442', 5427, 'matias_lvcb@hotmail.com', 'matias pollo', 21, 4914144, 4119989);

INSERT INTO persona 
(dni, nombre, apellido, fecha_nac, direccion, cod_postal, e_mail, facebook, edad, tel_fijo, tel_cel)
VALUES (37108054, 'Matias', 'Fernandez', '1994/12/30', 'Pueyrredon 442', 5800, 'matias_fernandez@hotmail.com', 'matias fernandez', 18, 4914130, 4240323);

INSERT INTO persona 
(dni, nombre, apellido, fecha_nac, direccion, cod_postal, e_mail, facebook, edad, tel_fijo, tel_cel)
VALUES (37108055, 'Maria', 'Dolores', '2000/04/20', 'Santa Rosa 1000', 5800, 'mariadolores@hotmail.com', 'maria dolores', 34, 4914399, 2233444);

INSERT INTO persona 
(dni, nombre, apellido, fecha_nac, direccion, cod_postal, e_mail, facebook, edad, tel_fijo, tel_cel)
VALUES (37108056, 'Esperanza', 'Carlini', '1995/06/11', 'Alvear 300', 1000, null, null, 20, 4136689, null);

INSERT INTO persona 
(dni, nombre, apellido, fecha_nac, direccion, cod_postal, e_mail, facebook, edad, tel_fijo, tel_cel)
VALUES (37108057, 'Lucas', 'Pomilio', '1985/02/05', 'Lugones 322', 5427, 'lucas_pomilio@hotmail.com', 'lucas pomilio', 45, 4914123, 4449979);

INSERT INTO persona 
(dni, nombre, apellido, fecha_nac, direccion, cod_postal, e_mail, facebook, edad, tel_fijo, tel_cel)
VALUES (37108052, 'Gabriela', 'Gomez', '1978/03/19', 'Alvear 1234', 1000, 'gabi_gomez@hotmail.com', 'gabriela gomez', 15, 4914700, 4563412);

-- Carga de datos a la tabla padrino
INSERT INTO padrino 
(dni)
VALUES (37108050);

INSERT INTO padrino 
(dni)
VALUES (37108054);

INSERT INTO padrino 
(dni)
VALUES (37108055);

INSERT INTO padrino 
(dni)
VALUES (37108056);

--Carga de datos a la tabla referente

INSERT INTO referente 
(dni)
VALUES (37108057);

INSERT INTO referente 
(dni)
VALUES (37108052);

-- Carga de datos a la tabla contacto

INSERT INTO contacto 
(dni, estado, fecha_primer_contacto, fecha_alta, fecha_baja, fecha_rechazo_adhesion, dni_referente, relacion, comentario)
VALUES (37108050, 'Adherido', '2000/03/02', '2001/01/12', null, null, 37108057, 'Primos', 'Exelente persona');

INSERT INTO contacto 
(dni, estado, fecha_primer_contacto, fecha_alta, fecha_baja, fecha_rechazo_adhesion, dni_referente, relacion, comentario)
VALUES (37108054, 'Adherido', '2012/03/02', '2012/04/12', null, null, 37108052, 'Conocidos', 'No paga a tiempo');

INSERT INTO contacto 
(dni, estado, fecha_primer_contacto, fecha_alta, fecha_baja, fecha_rechazo_adhesion, dni_referente, relacion, comentario)
VALUES (37108055, 'Adherido', '1999/12/12', '2000/01/01', '2003/05/05', '2003/06/05', 37108057, 'Compañero de trabajo', 'Colaborador');

INSERT INTO contacto 
(dni, estado, fecha_primer_contacto, fecha_alta, fecha_baja, fecha_rechazo_adhesion, dni_referente, relacion, comentario)
VALUES (37108056, 'Adherido', '2002/11/02', '20013/01/01', null, null, 37108052, 'Amigos', 'Cumplidor');

-- Carga de datos a la tabla donantes

INSERT INTO donante 
(dni, ocupacion, cuil_cuit)
VALUES (37108050, 'Empleado Rural', 123456789); 

INSERT INTO donante 
(dni, ocupacion, cuil_cuit)
VALUES (37108054, 'Empresario', 987654321);

INSERT INTO donante 
(dni, ocupacion, cuil_cuit)
VALUES (37108056, 'Ama de casa', 564738291);

-- Carga de datos a la tabla programa

INSERT INTO programa 
(id_programa,nombre,descripcion)
VALUES (1, 'Huerta', 'materiales para huerta');

INSERT INTO programa 
(id_programa,nombre,descripcion)
VALUES (2, 'Carpinteria', 'herramientas');

INSERT INTO programa 
(id_programa,nombre,descripcion)
VALUES (3, 'Apoyo', 'salario docente');

INSERT INTO programa 
(id_programa,nombre,descripcion)
VALUES (4, 'Educacion fisica', 'materiales para educacion fisica');

INSERT INTO programa 
(id_programa,nombre,descripcion)
VALUES (5, 'Comida', 'mercaderia');

INSERT INTO programa 
(id_programa,nombre,descripcion)
VALUES (6, 'Taller', 'materiales para taller');

-- Carga de datos a la tabla medio de pago

INSERT INTO medio_de_pago 
(cod_pago, nombre_titular)
VALUES (001, 'Matias Pollo');

INSERT INTO medio_de_pago 
(cod_pago, nombre_titular)
VALUES (123, 'Matias Fernandez');

INSERT INTO medio_de_pago 
(cod_pago, nombre_titular)
VALUES (321, 'Maria Dolores');

INSERT INTO medio_de_pago 
(cod_pago, nombre_titular)
VALUES (111, 'Esperanza Carlini');

-- Carga de datos a la tabla aporta

/**INSERT INTO aporta 
(dni_donante, id_programa, cod_pago, monto, frecuencia)
VALUES (37108050, 1, 001, 500, 'mensual');*/

INSERT INTO aporta 
(dni_donante, id_programa, cod_pago, monto, frecuencia)
VALUES (37108054, 2, 123, 1000, 'mensual');

INSERT INTO aporta 
(dni_donante, id_programa, cod_pago, monto, frecuencia)
VALUES (37108054, 4, 123, 300, 'mensual');

INSERT INTO aporta 
(dni_donante, id_programa, cod_pago, monto, frecuencia)
VALUES (37108050, 3, 111, 1500, 'trimestral');

INSERT INTO aporta 
(dni_donante, id_programa, cod_pago, monto, frecuencia)
VALUES (37108056, 5, 001, 4000, 'anual');

INSERT INTO aporta
(dni_donante, id_programa, cod_pago, monto, frecuencia)
VALUES (37108056, 3, 001, 1500, 'trimestral');
-- Carga de datos a la tabla nombre empresa

INSERT INTO nombre_empresa 
(nombre_tarjeta)
VALUES ('Naranja');

INSERT INTO nombre_empresa 
(nombre_tarjeta)
VALUES ('BBVA Frances');

INSERT INTO nombre_empresa 
(nombre_tarjeta)
VALUES ('Cordoba');

INSERT INTO nombre_empresa 
(nombre_tarjeta)
VALUES ('Galicia');

-- Carga de datos a la tabla tarjeta

INSERT INTO tarjeta 
(cod_pago, cod_verificacion, numero_tarjeta, vencimiento, nombre_tarjeta)
VALUES (001, 987, 123456, '2020/10/14', 'Naranja');

INSERT INTO tarjeta 
(cod_pago, cod_verificacion, numero_tarjeta, vencimiento, nombre_tarjeta)
VALUES (123, 789, 678901, '2022/12/01', 'BBVA Frances');

-- Carga de datos a la tabla debito transferencia

INSERT INTO debito_transferencia 
( cod_pago, cbu, nro_cuenta, tipo, sucursal_banco, nombre_banco)
VALUES (321, 5555, 0022, 'Tansferencia', 'Rio Cuarto', 'Galicia');

INSERT INTO debito_transferencia 
( cod_pago, cbu, nro_cuenta, tipo, sucursal_banco, nombre_banco)
VALUES (111, 2222, 3344, 'Debito', 'Cordoba', 'Cordoba');


