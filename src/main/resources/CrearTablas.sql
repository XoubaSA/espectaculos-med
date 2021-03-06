CREATE TABLE IF NOT EXISTS EVENTO(
	ID_EVENTO INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	NOMBRE_EVENTO VARCHAR(40) NOT NULL UNIQUE,
	FECHA_INICIO_EVENTO DATE NOT NULL,
	LOCALIDAD VARCHAR(40) NOT NULL
);

CREATE TABLE IF NOT EXISTS GRUPO(
	ID_GRUPO INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	NOMBRE_ORQUESTA VARCHAR(40) NOT NULL UNIQUE,
	SALARIO_ACTUACION REAL NOT NULL
);

CREATE TABLE IF NOT EXISTS GRUPO_EVENTO(
	ID_GRUPO_EVENTO INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	FECHA_ACTUACION VARCHAR(15) NOT NULL,
	ID_EVENTO BIGINT NOT NULL,
	ID_GRUPO BIGINT NOT NULL,
	FOREIGN KEY (ID_GRUPO) REFERENCES GRUPO(ID_GRUPO),
	FOREIGN KEY (ID_EVENTO) REFERENCES EVENTO(ID_EVENTO)
);

CREATE TABLE IF NOT EXISTS MUSICO(
	ID_MUSICO INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	NOMBRE_MUSICO VARCHAR(40) NOT NULL UNIQUE,
	DIRECCION VARCHAR(120) NOT NULL,
	INSTRUMENTO VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS MUSICO_GRUPO(
	ID_MUSICO_GRUPO INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	ID_MUSICO BIGINT NOT NULL,
	ID_GRUPO BIGINT NOT NULL,	
	FOREIGN KEY (ID_MUSICO) REFERENCES MUSICO(ID_MUSICO),
	FOREIGN KEY (ID_GRUPO) REFERENCES GRUPO(ID_GRUPO)	
);
