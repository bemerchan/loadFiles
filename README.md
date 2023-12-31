## Tabla de contenidos
1. [Información general](#informacin-general)
2. [Tecnologías](#tecnologas)
3. [Instalación](#instalacin)
4. [Servicios expuestos](#servicios-expuestos)
5. [Lectura del archivo](#lectura-del-archivo)
6. [Test](#test)
7. [Configuración](#configuracin)
8. [BenaIO](#beanio)
9. [Querydsl](#querydsl)
### Información general
***
Servicio encargado de leer archivos en un formato especifico (configurable), consultar una serie de APIs
públicas de MercadoLibre y almacenar una base de datos con los datos del archivo y las consultas de las APIs.

[![CI](https://github.com/flori/json/actions/workflows/ci.yml/badge.svg)](https://github.com/flori/json/actions/workflows/ci.yml)

## Tecnologías
***
* [JAVA](https://www.java.com/es/): Versión 11
* [Maven](https://maven.apache.org/): Versión 2.7.7
* [Sprint-boot](https://spring.io/projects/spring-boot): Version 2.7.13
* [Querydsl](http://querydsl.com/): Version 5.0.0
* [Swagger2](https://swagger.io/): Versión 3.0.0
* [BeanIO](https://swagger.io/): Versión 2.0.2
* [PostgreSQL](https://www.postgresql.org/): Versión 42.3.8


## Instalación
***
Pasos para la instalación y puesta en marcha del proyecto.

Tener en cuenta que debe tener instaladas las siguientes tecnolgías en su ordenador:

* [JAVA](https://www.java.com/es/): Version 11
* [Maven](https://maven.apache.org/): Version 2.7.7

```shell
$ git clone https://github.com/bemerchan/loadFiles.git
$ cd loadFiles/
$ mvn install -Dmaven.test.skip=true
```

Luego de estos pasos se habrán descargado las librerías y recursos necesarios para la ejecución del servicio. 

Es indispensable que para el siguiente paso configure la base de datos ya sea local o en su servidor de preferencia, los scripts de la estructura de base de datos se encuentran en archivo **database.sql** en la siguiente ruta del proyecto:

```
src/main/resources/database.sql
```

Creada la base de datos puede cambiar la configuración del servicio para que se conecte a la base datos, por defecto la configuración apunta al servidor de base de datos local. Dicha configuración se encuentra en el archivo **application.properties** de la carpeta **resorces**

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/upload
spring.datasource.username=upload
spring.datasource.password=upload123
```

Luego de configurada la base de datos puede continuar con el proceso de ejecución:

```shell
$ mvn spring-boot:run
```

Luego de ejecutar estos comando, el servicio debe estar disponible para su utilización, para comprobar este paso puede ingresar a la siguiente URL: http://localhost:8080/swagger-ui/#/, allí encontrará la documentación de las apis expuestas.


## Servicios expuestos
***
El servicio consta de 3 controladores
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
Para leer un archivo se debe consumir el servicio **POST** **upload-api/upload**, este servicio recibe un parámetro **file** de tipo File.

Al consumir el servicio si el llamado es exitoso se tendrá una respuesta de este tipo:

```json
{
    "id": 1,
    "startDate": "2023-08-18 17:11:04",
    "status": "EN PROCESO",
    "fileName": "technical_challenge_data.csv"
}
```

El servidor se queda procesando el archivo asincronamente, para consultar el estado del proceso se puede utilizar el api: [http://localhost:8080/load/1]( http://localhost:8080/load/1).

**Respuesta Exitosa:** Indica que la lectura del archivo finalizo con éxito y que se procesó la información correctamente:

```json
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

```json
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
Para correr los test integrales se debe ejecutar el siguiente comando:
```shell
mvn test
```
La cobertura del proyecto se puede verificar en la siguiente ruta:
```
target/site/jacoco/index.html
```

> [!NOTA]
> Para ejecutar los test es necesario haber configurado la base de datos ya que son test de inegración


## Configuración
***
El servicio está configurado para recibir archivos archivos **csv** separado por **comas**, esta configuración se puede cambiar de acuerdo a la necesidad de la implementación, dicha configuración se encuentra en el archivo **application.properties** de la carpeta **resources**

```properties
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

config.multipart.extensions=csv
config.process.search.data.size=20
config.process.save.data.size=100

client.mercadolibre.api.url=https://api.mercadolibre.com/
```

* **spring.servlet.multipart.max-file-size:** Tamaño máximo de los archivos que acepta el servicio.

* **config.multipart.extensions=:** Extensión o extensiones permitidas para carga de archivos.

* **config.process.search.data:** Tamaño máximo de registros que se pueden consultar por petición en las apis de mercado libre.

* **config.process.save.data.size:** Número de registros por transacción que se persisten en base de datos al momento de leer y guardar los datos.

* **client.mercadolibre.api.url=https:** Url base de los servicios de mercado libre.

> [!NOTA] 
> Si se desea cambiar el formato de lectura de archivos y el delimitador es necesario modificar la configuración anterior **config.multipart.extension** y el archivo **load-mapping.xml**

Las perticiones se procesan asincronamente en el servidor, si desea cambiar la configuración lo puede realizar en el archivo **UploadApplication** que se encuentra en **src/main/java**

```java
  @Bean
  public Executor taskExecutor() {
      ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
      executor.setCorePoolSize(2);
      executor.setMaxPoolSize(10);
      executor.setQueueCapacity(20);
      executor.setKeepAliveSeconds(120);
      executor.initialize();
      return executor;
  }
```

El ejemplo configura un pool de hilos dinámico con un tamaño comprendido entre 2 y 10, y una cola de peticiones limitada a 20. Un hilo sin uso permanecerá en el pool un máximo de 120 segundos


### BeanIO
Se utlizó la librería **BeanIO** que permite clasificar y desclasificar beans Java desde un archivo plano, una secuencia o un objeto String simple. La configuración de la libreria se encuentra en el archivo **load-mapping.xml** del la carpeta **resources**, allí se configura la estructura, el formato y el delimitador para la lectura de los archivos:

```xml
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

### Querydsl
Librería que permite crear y ejecutar consultar de manera segura y ági.

**Ejemplo:** Si queremos consultar todos los registros de una carga es especifico basta con consumir la el servicio de load-data y enviarle el id de la carga como parámetro **http://localhost:8080/load-data?loadId=1**
 
Obtendremos los regitros asociados a la carga con su respectiva paginación y cantidad de registros: 

```json
{
  "_embedded" : {
    "load-data" : [ {
      "id" : "2473",
      "loadId" : 1,
      "recordId" : "MLA750925229",
      "price" : 719.44,
      "startTime" : "2018-10-01T22:31:56",
      "name" : null,
      "description" : "Peso argentino",
      "nickname" : "VIP.SALE",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/load-data/2473"
        },
        "loadData" : {
          "href" : "http://localhost:8080/load-data/2473"
        }
      }
    }, {
      "id" : "2474",
      "loadId" : 1,
      "recordId" : "MLA845041373",
      "price" : 2900.0,
      "startTime" : "2020-03-22T21:27:57",
      "name" : null,
      "description" : "Peso argentino",
      "nickname" : "CHSKATE",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/load-data/2474"
        },
        "loadData" : {
          "href" : "http://localhost:8080/load-data/2474"
        }
      }
    } ]
  },
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/load-data?loadId=60"
    },
    "profile" : {
      "href" : "http://localhost:8080/profile/load-data"
    },
    "search" : {
      "href" : "http://localhost:8080/load-data/search"
    }
  },
  "page" : {
    "size" : 20,
    "totalElements" : 2,
    "totalPages" : 1,
    "number" : 0
  }
}
```

En el caso que se quiera obetner los registros donde el recoirdId contenga 'MLA', cambiamos el parámetro de busqueda

```json
{
  "_embedded" : {
    "load-data" : [ {
      "id" : "2473",
      "loadId" : 1,
      "recordId" : "MLA750925229",
      "price" : 719.44,
      "startTime" : "2018-10-01T22:31:56",
      "name" : null,
      "description" : "Peso argentino",
      "nickname" : "VIP.SALE",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/load-data/2473"
        },
        "loadData" : {
          "href" : "http://localhost:8080/load-data/2473"
        }
      }
    }, {
      "id" : "2474",
      "loadId" : 1,
      "recordId" : "MLA845041373",
      "price" : 2900.0,
      "startTime" : "2020-03-22T21:27:57",
      "name" : null,
      "description" : "Peso argentino",
      "nickname" : "CHSKATE",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/load-data/2474"
        },
        "loadData" : {
          "href" : "http://localhost:8080/load-data/2474"
        }
      }
    }, {
      "id" : "2475",
      "loadId" : 2,
      "recordId" : "MLA845041374",
      "price" : 3000.0,
      "startTime" : "2020-03-22T21:27:57",
      "name" : null,
      "description" : "Peso argentino",
      "nickname" : "CHSKATE",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/load-data/2474"
        },
        "loadData" : {
          "href" : "http://localhost:8080/load-data/2474"
        }
      }
    } ]
  },
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/load-data?loadId=60"
    },
    "profile" : {
      "href" : "http://localhost:8080/profile/load-data"
    },
    "search" : {
      "href" : "http://localhost:8080/load-data/search"
    }
  },
  "page" : {
    "size" : 20,
    "totalElements" : 2,
    "totalPages" : 1,
    "number" : 0
  }
}
```

> [!NOTA]
> Quedrydsl permite realizar consultas y combinaciones por todos y cada uno de los campos expuestos en los servicios

