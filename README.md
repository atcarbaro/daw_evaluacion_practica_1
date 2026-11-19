# Evaluación práctica 1

API REST para registrar, consultar, actualizar y eliminar empleados con Java 25, Spring Boot, Spring Data JPA y SQL Server.

La conexión a SQL Server usa **autenticación integrada de Windows** (`integratedSecurity=true`). La configuración está en [application.properties](src/main/resources/application.properties).

## Endpoints

URL base `http://localhost:8080`.

| Método | Ruta | Resultado |
| --- | --- | --- |
| GET | `/api/empleados` | `200 OK` con el listado de empleados. |
| GET | `/api/empleados?filtro=Torres` | `200 OK` con las coincidencias por nombre, apellido o especialidad. |
| GET | `/api/empleados/{id}` | `200 OK` con el empleado o `404 Not Found` si el ID no existe. |
| POST | `/api/empleados` | `201 Created` con el empleado registrado, su ID y el encabezado `Location`. Devuelve `400 Bad Request` si los datos son inválidos. |
| PUT | `/api/empleados/{id}` | `200 OK` con el empleado actualizado, `400 Bad Request` si los datos son inválidos o `404 Not Found` si el ID no existe. |
| DELETE | `/api/empleados/{id}` | `204 No Content` al eliminar o `404 Not Found` si el ID no existe. |

### Cuerpo de la solicitud para POST y PUT

Envía el encabezado `Content-Type: application/json`. El nombre, el apellido y el correo electrónico son obligatorios. La especialidad es opcional.

```json
{
  "nombre": "Ana",
  "apellido": "Torres",
  "email": "ana.torres@example.com",
  "especialidad": "Análisis de datos"
}
```

Usa el `id` de la respuesta del POST en las rutas con `{id}`. En una actualización, omitir `especialidad` o enviarla como `null` elimina ese valor.
