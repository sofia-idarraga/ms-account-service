# MS Account Service

Este servicio se encarga del manejo de cuentas y movimientos bancarios 


## Aplicación

### 2.1. Pre-Requisitos
* Tener instalado [**Java 17**](https://www.oracle.com/java/technologies/downloads/).

Esta aplicación se conecta con **MySQL**  por lo que deberá tener una imagen o servicio en su local.

Para ejecutar el microservicio y los tests correctamente, asegúrate de tener correctamente configuradas las siguientes variables de entorno:

- `server.port`: ${SERVER_PORT} Define el puerto de la aplicación, por defecto es 8081

- `spring.account.service`: ${ACCOUNT_SERVICE} Esta variable especifica la URL del servicio de cuentas bancarias al que el microservicio se conecta. Por defecto, apunta a `http://localhost:8082/api/cuentas`.

- `spring.datasource.url`: ${DATABASE_URL} Esta variable define la URL de conexión a la base de datos.

- `spring.datasource.username`: ${DATABASE_USERNAME} Nombre de usuario de la base de datos.

- `spring.datasource.password`: ${DATABASE_PASSWORD} Contraseña de la base de datos.

Las variables de entorno se utilizan para configurar aspectos específicos de la aplicación, como la conexión a la base de datos y la comunicación con otros servicios.

## Endpoints


### Cuentas

- `POST /api/cuentas/nuevo`: Crea una nueva cuenta. Se espera un objeto JSON con la información de la cuenta en el cuerpo de la solicitud.

- `PUT /api/cuentas/{number}`: Actualiza la información de la cuenta correspondiente al número especificado. Se espera un objeto JSON con la información actualizada de la cuenta en el cuerpo de la solicitud.

- `PUT /api/cuentas/desactivar/{nit}`: Desactiva la cuenta correspondiente al NIT especificado.

### Movimientos

- `POST /api/movimientos`: Registra un nuevo movimiento. Se espera un objeto JSON con la información del movimiento en el cuerpo de la solicitud.

- `GET /api/movimientos/reportes`: Genera un reporte de movimientos para un cliente específico en un rango de fechas dado. Se esperan los parámetros de consulta `nit`, `initDate` y `endDate`.

### Ambos

Los siguientes endpoints aplican para ambas entidades


- `GET /api/${entidad}/{nit}`: Retorna la información de la entidad correspondiente al número de identificación especificado.

- `GET /api/${entidad}/todos`: Retorna todos. Se puede especificar el orden ascendente ("ASC") o descendente ("DESC") utilizando el parámetro de consulta `direction`.

- `GET /api/${entidad}/todos/page`: Retorna una lista paginada de clientes. Se puede especificar el número de página, el tamaño de página y el orden ascendente o descendente utilizando los parámetros de consulta correspondientes `direction`, `pageNumber` y `pageSize`.

- `DELETE /api/${entidad}/{nit}`: Elimina el item correspondiente al id especificado.

### ⚠️ Alerta ⚠️

Siempre prefiera usar la desactivación que la eliminación. Esto evitará que tenga data inconsistente.

## Crear imagen

Para crear la imagen ejecute
 ```shell script
  podman build -t ms-account-service:latest -f Dockerfile .
  ```

## Archivos extras:

En la raiz del proyecto encontrará: 

-  Script de base datos, entidades y esquema datos, con el nombre
   BaseDatos.sql.
-  Archivo Json, de Aplicación Postman, para validación de los endpoints.