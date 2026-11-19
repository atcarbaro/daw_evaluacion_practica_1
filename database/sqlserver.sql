USE master;
GO

IF DB_ID(N'evaluacion_practica_1') IS NULL
    CREATE DATABASE evaluacion_practica_1;
GO

USE evaluacion_practica_1;
GO

IF OBJECT_ID(N'dbo.empleados', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.empleados (
        id INT IDENTITY(1,1) NOT NULL,
        nombre VARCHAR(100) NOT NULL,
        apellido VARCHAR(100) NOT NULL,
        email VARCHAR(150) NOT NULL,
        especialidad VARCHAR(100) NULL,
        CONSTRAINT PK_empleados PRIMARY KEY (id)
    );
END;
GO
