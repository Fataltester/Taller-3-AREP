# Arquitecturas de Servidores de Aplicaciones, Meta protocolos de objetos, Patrón IoC, Reflexión
El objetivo del taller es construir un servidor web (tipo Apache) con Java, proveyendo un framework con IoC para construir una aplicación web a partir de un POJO, mostrando la reflexión en java.
## Objetivos 
* Para la primera versión, el POJO debe ser cargado desde la consola (realizado en clase).
* Atender notaciones @GetMapping con retorno limitado a String.
* Explorar el classpath para cargar los controladores evitando cargar los POJO desde consola.
* Soportar @GetMapping y @RequestParam.
## PreRequisitos
Para el proyecto debemos configurar un entorno maven para armar el proyecto, para esto hay diferentes opciones como visual studio, intellij, etc. Si se quiere trabajar sobre estos se debe configurar e instalar maven para su funcionamiento, pero, para este laboratorio y facilitarme este proceso utilicé Netbeans el cual me ahorra ese proceso, puede instalar el ambiente de desarrollo en su página oficial [aquí](https://netbeans.apache.org/front/main/index.html). También debemos tener java instalado y git para poder clonar el repositorio actual.
## Instalación
1. Vamos a clonar el repositorio con el siguiente comando
```bash
git clone https://github.com/Fataltester/Taller-2-AREP.git
```

2. Como estamos utilizando Netbeans simplemente abrimos el proyecto en el ambiente y ejecutamos el archivo "MicroSpringboot", una vez iniciado, en la consola aparecerá "Listo para recibir ..."
   
3. Procedemos a ir al navegador e insertar la siguiente dirección
```bash
http://localhost:35000/....
```
Ya dependiendo la solicitud si es de archivo estático o cargar los métodos del controlador definimos la URI

Para consultar tenemos:
Controlador:
```bash
http://localhost:35000/app/greeting?name=..
```
Para archivos estáticos:
```bash
http://localhost:35000/cat.jpg
http://localhost:35000/index.html
http://localhost:35000/response.js
http://localhost:35000/style.css
```

## Arquitectura
Tenemos una arquitectura en capas:

Capa de Presentación -> el main que esta encargado de levantar el servidor.

Capa de Infraestructura -> la clase "HttpServer" donde se levantan los socket, reciben solicitudes, generan respuestas e invoca los métodos.

Capa de Controladores -> los controladores definidos @RestController-

Capa de Pruebas -> pruebas JUnit.

## Reporte de pruebas
Al ejecutar el archivo de pruebas obtenemos lo siguiente:

<img width="950" height="334" alt="image" src="https://github.com/user-attachments/assets/efa72db4-cfb1-44e7-86cb-4960d345a337" />

Tenemos las siguientes pruebas:
* testGuessMimeTypeHtml -> se reconocen las extensiones html.
* testGuessMimeTypePng -> se reconocen las extensiones png.
* testGuessMimeTypeUnknown -> al pedir una extension que no exista, retorne text/plain con una extension desconocida.
* testLoadRestControllers -> prueba que los controladores y sus métodos son cargados correctamente

## De aceptación

index.html

<img width="793" height="507" alt="image" src="https://github.com/user-attachments/assets/88f52cda-e67d-4174-8a13-6f573a26cba7" />

cat.jpg

<img width="670" height="562" alt="image" src="https://github.com/user-attachments/assets/e241c76d-1592-4b24-89ed-f50da3110898" />

/app/greeting?name=juan

<img width="483" height="121" alt="image" src="https://github.com/user-attachments/assets/025a0e4e-cf19-44f5-b05e-39be81d431ec" />

### Contruido con 
[Maven](https://maven.apache.org) Maven

[Netbeans](https://netbeans.apache.org/front/main/index.html) Netbeans 

[Git](https://git-scm.com) Git

### Autor
Juan David Martínez Mendez - [Fataltester](https://github.com/Fataltester)


