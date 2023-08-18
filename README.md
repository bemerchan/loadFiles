## Tabla de contenidos
1. [Información general](#general-info)
2. [Tecnologias](#tecnologias)
3. [Instalación](#installation)
4. [Ejecución](#ejecución)
5. [FAQs](#faqs)
### Información general
***
Servicio encargado de eleer archivos en un formarto especifico (configurable), consultar una serie de APIs
públicas de MercadoLibre y almacenar una base de datos con los datos del archivo y las consultas de las APIs.

## Tecnologias
***
* [JAVA](https://www.java.com/es/): Version 11
* [Maven](https://maven.apache.org/): Version 2.7.7
* [Sprint-boot](https://spring.io/projects/spring-boot): Version 2.7.13
* [Querydsl](http://querydsl.com/): Version 5.0.0
* [Swagger2](https://swagger.io/): Version 3.0.0
* [BeanIO](https://swagger.io/): Version 2.0.2

## Instalación
***
Pasos para la instalación y puesta en marcha del proyecto.

Tener en cuenta que debe tener instaladas las siguientes tecnolgias en su ordenador:

* [JAVA](https://www.java.com/es/): Version 11
* [Maven](https://maven.apache.org/): Version 2.7.7

```
$ git clone https://github.com/bemerchan/loadFiles.git
$ cd loadFiles/
$ mvn install -Dmaven.test.skip=true
```

Luego de estos pasos se habran descargado las librerias y recursos necesarios para la ejecución del servicio. 

Es indispensable que para el siguiente paso configure la base de datos ya sea local o en su servidor de preferencia, los script de la estructura de base de datos se encuentran en archivo **database.sql** en la siguiente rura del proyecto:

```
src/main/resources/database.sql
```

Creada la base de datos puede cambiear la configuración del servicio para que se conecte a la base datos, por defecto la configuración apunta al servidor de base de datos local. Dicha configuración se encuentra en el archivo **application.properties** de la carpeta **resorces**

```
spring.datasource.url=jdbc:postgresql://localhost:5432/upload
spring.datasource.username=upload
spring.datasource.password=upload123
```

Luego de configurada la base de datos puede continuar con el proceso de ejecución:

```
$ mvn spring-boot:run
```

Luego de ejecutar estos comando, el servicio debe estar disponible para su utilización, para comprobar este paso puede ingresar a la siguiente URL: http://localhost:8080/swagger-ui/#/, allí encontrará la documentación de las apis expuestas.


## Servicios expuestos
***
El servicio consta de 3 contrladores
* **Load entity**: Expone las acciones sobre las cargas:
  * GET
  * POST
  * PUT
  * PATCH
  * DELETE
  
* **LoadDATA entity**: Expone las acciones sobre el detalle de las cargas:
  * GET
  * POST
  * PUT
  * PATCH
  * DELETE
* **upload-api**: Expone el api para la carga de archivos
  * POST **upload**
  
## Lectura del archivo
***
Para leer un archivo se debe consumir el servicio **POST** **upload-api/upload**, este servicio recibe un parametro **file** de tipo File.

Al consumir el servicio si el llamado es exitoso se tendrá una respues de este tipo:

```
{
    "id": 1,
    "startDate": "2023-08-18 17:11:04",
    "status": "EN PROCESO",
    "fileName": "technical_challenge_data.csv"
}
```

El servidor se queda procesando el archivo asincronamente, para consultar el estado del proceso se puede utilizar el api: [http://localhost:8080/load/1]( http://localhost:8080/load/1).

**Respuesta Exitosa:** Indica que la lextura del archivo finalizo con exito y que se proceso la información correctamente:

```
{
  "id" : 1,
  "startDate" : "2023-08-18 17:11:04",
  "endDate" : "2023-08-18 17:11:53",
  "status" : "EXITOSO",
  "fileName" : "technical_challenge_data.csv",
  "error" : null
}
```

**Respuesta Fallida:** Indica que el archivo tuvo errores e indica el error especifico:

```
{
  "id" : 1,
  "startDate" : "2023-08-18 17:11:04",
  "endDate" : "2023-08-18 17:11:53",
  "status" : "ERROR",
  "fileName" : "technical_challenge_data.csv",
  "error" : "Error converting file"
}
```

## Test
***
Para correr los test unitarios e integrales se debe ejecutar el siguiente comando:
```
mvn test
```
La cobertura del proyecto se puede verificar en la siguiente ruta:
```
target/site/jacoco/index.html
```

## Configuración
***
El servicio esta configurado para recibir archivos archivos **csv** separado por **comas**, esá configuración se puede cambiar de acuerdo a la necesidad de la implementación, dicha configuración se encuentra en el archivo **application.properties** de la carpeta **resources**

```
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

config.multipart.extensions=csv
config.process.search.data.size=20
config.process.save.data.size=100

client.mercadolibre.api.url=https://api.mercadolibre.com/
```

**spring.servlet.multipart.max-file-size:** Tamaño máximo de los archivos que acepta el servicio.

**config.multipart.extensions=:** Extensión o extensiones permitidas para carga de archivos.

**config.process.search.data:** Tamaño máximo de registros que se pueden consultar por petición en las apis de mercado libre.

**config.process.save.data.size:** Número de registros por transacción que se persisten en base de datos al momento de leer y guardar los datos.

**client.mercadolibre.api.url=https:** Url base de los servicios de mercado libre.

#### NOTA: Si se desea cambiar el formato de lectura de archivos y el delimitador es necesario modificar la configuración anterior **config.multipart.extension** y el archivo **load-mapping.xml**

### BeanIO
Se utlizo la libreria **BeanIO** que permite clasificar y desclasificar beans Java desde un archivo plano, una secuencia o un objeto String simple. la configuración de la libreria se encuentra en el archivo **load-mapping.xml** del la carpeta **resources**, allí se configura la estructura, el formato y el delimitador para la lectura de los archivos:

```
<beanio xmlns="http://www.beanio.org/2012/03"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.beanio.org/2012/03 http://www.beanio.org/2012/03/beanio.xsd">

    <stream name="formatLoadFile" format="csv">
        <parser>
            <property name="delimiter" value=","/>
        </parser>
        <record name="row" class="com.upload.application.api.model.FileRow">
            <field name="site" position="0"/>
            <field name="id" position="1"/>
        </record>
    </stream>
</beanio>
```


