API de Prevención de Fraudes
Autor: Juan Ignacio Felice

Requisitos para Desarrollo:
- Java JDK 17 o superior
- Maven 3.6.x o superior
- IDE: recomendado Spring Boot
Instalación:
1. Clona el repositorio:

git clone https://github.com/JuanigFelice/FraudPrevent
2. Levanta el proyecto con el IDE elegido, preferiblemente SpringToolSuite:

Abrir el IDE, seleccionar “Importar Proyecto”, Elegir el directorio raiz donde esta el
archivo pom.xml
3. Compilar el proyecto usando Maven:
Parado en el Raiz del proyecto, boton derecho, RunAs->Maven Clean
Parado en el Raiz del proyecto, boton derecho, RunAs->Maven Install
Esto trae todas las dependecias necesarias de la aplicación
4. Ejecutar la aplicacion:

Buscando el paquete com.fraud.FraudPrevent → posicionarse sobre la clase
FraudPreventApplication
Posicionado sobre dicha clase → boton derecho→ Run As → Spring Boot App
La app se iniciaConfiguración Adicional
Configuración de la Base de Datos
Asegurarse de que la base de datos MySQL esté corriendo y accesible.
Configurar la conexión en el archivo “src/main/resources/application.properties” de la
siguiente manera:
Aplication.properties:
spring.datasource.url=jdbc:mysql://localhost:3306/nombre_base_datos
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
Reemplazar nombre_base_datos con el nombre de tu base de datos.
Reemplaza tu_usuario y tu_contraseña con tus credenciales de MySQL.
-------------------------------------------------------------------------------------
Uso de la API
La API está disponible en: http://localhost:8080/api/fraud/checkFraudRisk?.
La Repuesta de la Api es:
{
"is_new_user": true,
"qty_rejected_1d": 5,
"total_amt_7d": 1234.56
}

Probar con Postman

Puedes probar este endpoint usando Postman en la siguiente URL:
(Esta URL es solo cuando se ejecuta desde la SpringBoot)
GET http://localhost:8080/api/fraud/checkFraudRisk?userId={3}
por ejemplo:
http://localhost:8080/api/fraud/checkFraudRisk?userId=1se deben cargar usuario y Clave, las mismas que estan en el archivo
application.properties
como se ve en la imagen en la opcion “Authorization” de Postman
Luego en la seccion Params, las query params, en este caso:
key: “userId” y Value: “{aca se ingresa el nro de Id de Usuario}”
imagen de ejemplo:Instrucciones para Ejecutar la Aplicación Spring Boot (.war)

Requisitos Previos:

1. Java Runtime Environment (JRE):
- El cliente necesita tener instalado Java 17 (o superior) en su máquina. Se peude verificar si
Java está instalado ejecutando el siguiente comando en la terminal o símbolo del sistema:
con el comando:
java -version
- Si Java no está instalado, se puede descargar e instalar desde la [página oficial de Oracle]
(https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)

2. Servidor de Aplicaciones:
- Aunque Spring Boot se puede ejecutar como un archivo `.jar`, al empaquetarlo como un
`.war`, se necesitará un servidor de aplicaciones compatible como Apache Tomcat.
Asegurarse de tener el Tomcat instalado y configurado correctamente.
- Si no tiene Tomcat, puede descargarlo desde la [página oficial de Apache Tomcat]
(https://tomcat.apache.org/).
Pasos para Ejecutar el Archivo “.war”


Copiar el archivo “.war” (por ejemplo, FraudPrevent-0.0.1-SNAPSHOT .war) que se
encuentra autogenerado dentro de carpeta “target”:
*\target\FraudPrevent-0.0.1-SNAPSHOT.war
y luego ponerlo en el directorio webapps de la instalación de Tomcat.
La ruta podría ser algo como: “C:\apache-tomcat-10.1.31\webapps\ FraudPrevent-
0.0.1-SNAPSHOT .war”.
  
Iniciar el Tomcat:

◦ Ir al directorio C:\apache-tomcatXX\bin de la instalación de Tomcat desde la
terminal o símbolo del sistema
◦ Iniciar el servidor Tomcat ejecutando el siguiente comando:
▪ En Windows: startup.bat


Acceder a la Aplicación:
◦ Una vez inicado el Tomcat, se puede verificar ingresando a la direccion: (ejemplo):
http://localhost:8080/FraudPrevent-0.0.1-SNAPSHOT/api/fraud/checkFraudRisk?userId=1
Por las medidas de seguridad no se va a poder chequear desde un navegador web, sino,
desde la applicacion Postman: https://www.postman.com/
Aca tambien deben cargar se los parametros de usuario Id y su valor, como tambien, el
usuario y clave de Autorizacion de ingreso a la API.
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Documentación de la API con Swagger
Se puede acceder con la app deployada en el servidor tomcat, en la siguiente URL:
http://localhost:8080/FraudPrevent-0.0.1-SNAPSHOT/swagger-ui/index.html
Diagrama:
