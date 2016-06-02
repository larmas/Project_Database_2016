DROP SCHEMA IF EXISTS ciudaddelosniños CASCADE;
CREATE SCHEMA ciudaddelosniños;
SET search_path to ciudaddelosniños;

CREATE TABLE persona (
dni INTEGER NOT NULL,
nombre VARCHAR(40) NOT NULL,
apellido VARCHAR(40) NOT NULL,
direccion VARCHAR(60) NOT NULL,
cod_postal INTEGER NOT NULL,
e_mail VARCHAR(40),
facebook VARCHAR(40),
fecha_nac DATE NOT NULL,
edad INTEGER NOT NULL,
tel_fijo INTEGER NOT NULL,
tel_cel INTEGER,
CONSTRAINT Persona_pkey PRIMARY KEY (dni),
CONSTRAINT dni_valido CHECK (dni > 0),
CONSTRAINT edad_valida CHECK (edad > 0)
);

CREATE TABLE padrino (
dni INTEGER NOT NULL,
CONSTRAINT pk_padrino PRIMARY KEY (dni),
CONSTRAINT fk_padrino FOREIGN KEY (dni) REFERENCES persona ON DELETE CASCADE
);

CREATE TABLE donante (
dni INTEGER NOT NULL,
ocupacion VARCHAR NOT NULL,
cuil_cuit INTEGER NOT NULL,
CONSTRAINT pk_donante PRIMARY KEY (dni),
CONSTRAINT fk_donante FOREIGN KEY (dni) REFERENCES padrino ON DELETE CASCADE
);

CREATE TABLE referente (
dni INTEGER NOT NULL,
CONSTRAINT pk_referente PRIMARY KEY (dni),
CONSTRAINT fk_referente FOREIGN KEY (dni) REFERENCES persona ON DELETE CASCADE
);

CREATE DOMAIN ESTADOVALIDO AS VARCHAR(20)
CHECK( VALUE IN ('Sin llamar','Error','En Gestion','Adherido','Amigo','No acepta','Voluntario'))
NOT NULL;


CREATE TABLE contacto (
dni INTEGER NOT NULL,
estado ESTADOVALIDO,
fecha_alta DATE NOT NULL,
fecha_baja DATE,
fecha_rechazo_adhesion DATE,
fecha_primer_contacto DATE NOT NULL,
dni_referente INTEGER,
comentario VARCHAR,
relacion VARCHAR,
CONSTRAINT fecha_alta_valida CHECK (fecha_primer_contacto <= fecha_alta),
CONSTRAINT fecha_baja_valida CHECK (fecha_alta < fecha_baja),
CONSTRAINT fecha_rechazo_valida CHECK (fecha_baja < fecha_rechazo_adhesion),
CONSTRAINT pk_contacto PRIMARY KEY (dni),
CONSTRAINT fk_contacto FOREIGN KEY (dni) REFERENCES padrino ON DELETE CASCADE,
CONSTRAINT fk_referente FOREIGN KEY (dni_referente) REFERENCES referente ON DELETE CASCADE
);

CREATE TABLE programa (
 id_programa INTEGER NOT NULL,
 nombre VARCHAR NOT NULL,
 descripcion VARCHAR,
 CONSTRAINT programa_pkey PRIMARY KEY (id_programa),
 CONSTRAINT programa_valido CHECK (id_programa > 0)
 );
 
CREATE TABLE medio_de_pago (
cod_pago INTEGER NOT NULL,
nombre_titular VARCHAR NOT NULL,
CONSTRAINT medio_pago_pkey PRIMARY KEY (cod_pago),
CONSTRAINT medio_pago_valido CHECK (cod_pago > 0)
);

 CREATE TABLE aporta (
 dni_donante INTEGER NOT NULL,
 id_programa INTEGER NOT NULL,
 cod_pago INTEGER NOT NULL ,
 monto INTEGER NOT NULL DEFAULT 0,
 frecuencia VARCHAR NOT NULL,
 CONSTRAINT pk_aporta PRIMARY KEY (dni_donante, id_programa, cod_pago),
 CONSTRAINT fk_donante_aporta FOREIGN KEY (dni_donante) REFERENCES donante ON DELETE CASCADE,
 CONSTRAINT fk_programa_aporta FOREIGN KEY (id_programa) REFERENCES programa ON DELETE CASCADE,
 CONSTRAINT fk_pago_aporta FOREIGN KEY (cod_pago) REFERENCES medio_de_pago ON DELETE CASCADE,
 CONSTRAINT monto_positivo CHECK (monto > 0),
 CONSTRAINT pago_Aporta_valido CHECK (cod_pago > 0)
 );

 CREATE TABLE nombre_empresa (
 nombre_tarjeta VARCHAR (40) NOT NULL PRIMARY KEY
 );

 CREATE TABLE tarjeta (
 cod_pago INTEGER NOT NULL,
 cod_verificacion INTEGER NOT NULL,
 vencimiento DATE NOT NULL,
 numero_tarjeta INTEGER NOT NULL,
 nombre_tarjeta VARCHAR (40) NOT NULL,
 CONSTRAINT pk_cod_pago_tarjeta PRIMARY KEY (cod_pago),
 CONSTRAINT fk_medio_pago_tarjeta FOREIGN KEY (cod_pago) REFERENCES medio_de_pago ON DELETE CASCADE,
 CONSTRAINT fk_empresa_nom_tarjeta FOREIGN KEY (nombre_tarjeta) REFERENCES nombre_empresa ON DELETE CASCADE,
 CONSTRAINT cod_pago_tarjeta_valido CHECK (cod_pago > 0)
 );
 
 CREATE TABLE debito_transferencia (
 cod_pago INTEGER NOT NULL,
 cbu INTEGER NOT NULL,
 nro_cuenta INTEGER NOT NULL,
 tipo VARCHAR NOT NULL,
 sucursal_banco VARCHAR NOT NULL,
 nombre_banco VARCHAR NOT NULL,
 CONSTRAINT debito_cod_pago PRIMARY KEY (cod_pago),
 CONSTRAINT debito_trans_cod_pago FOREIGN KEY (cod_pago) REFERENCES medio_de_pago ON DELETE CASCADE
 );

 CREATE TABLE auditoria (
 	dni INTEGER NOT NULL,
 	--nombre VARCHAR(40) NOT NULL,
 	--apellido VARCHAR(40) NOT NULL,
 	cuil_cuit INTEGER NOT NULL,
 	--direccion VARCHAR(60) NOT NULL,
 	--tel_fijo INTEGER NOT NULL,
 	usuario VARCHAR(60) NOT NULL,
 	fecha_eliminacion DATE NOT NULL,
 	CONSTRAINT pk_donante_auditoria PRIMARY KEY (dni),
	CONSTRAINT fk_donante_auditoria FOREIGN KEY (dni) REFERENCES padrino ON DELETE CASCADE
 	);

 CREATE OR REPLACE FUNCTION insertar_trigger() RETURNS TRIGGER AS $insertar$
 DECLARE BEGIN
 		INSERT INTO auditoria 
 		(dni, cuil_cuit,  usuario, fecha_eliminacion)
 		VALUES (OLD.dni, OLD.cuil_cuit, session_user, now());
 		RETURN NULL;
 END;
 $insertar$ LANGUAGE plpgsql;

 CREATE TRIGGER insertar_auditoria AFTER DELETE
 ON Donante FOR EACH ROW
 EXECUTE PROCEDURE insertar_trigger();
 