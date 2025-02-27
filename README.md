# Line Counting Program

Este proyecto consiste en un programa que cuenta las líneas lógicas y las líneas físicas en archivos de código fuente, omitiendo comentarios y líneas en blanco.

## Requisitos

- Java 11 o superior
- Maven para la construcción y empaquetado del proyecto (si decides generar el JAR desde el código fuente).
- Sistema operativo Windows para ejecutar el archivo EXE.

## Instalación y Ejecución

1. Clona el repositorio:
   ```sh
   git clone <URL_DEL_REPOSITORIO>
   cd demo
   ```

2. Compila el proyecto con Maven:
   ```sh
   mvn clean install
   ```

3. Ejecuta la aplicación:
   ```sh
   java -jar target/demo-1.0-SNAPSHOT.jar
   ```

## Ejecución del EXE (solo para Windows)
### Instalación
1. Descarga el archivo EXE:

El archivo mi-app.exe se encuentra en el directorio target/ de tu proyecto. Si tienes el repositorio clonado, asegúrate de que el archivo .exe está disponible en target/.

2. Transferencia del archivo EXE:

Si prefieres, puedes copiar el archivo mi-app.exe a cualquier directorio en tu máquina y ejecutarlo directamente.

## Ejecución del EXE
1. Ejecuta el archivo EXE:

Navega al directorio donde se encuentra el archivo mi-app.exe y haz doble clic sobre él. Esto ejecutará la aplicación sin necesidad de instalar Java en tu máquina, ya que está empaquetado como un ejecutable nativo de Windows.

```sh
cd target/
./mi-app.exe
```

Si no tienes un entorno gráfico, puedes ejecutar el EXE desde la línea de comandos también.

## Pruebas

Para ejecutar las pruebas, usa:
```sh
mvn test
```

## Autores
- Aaron Isaac Graniel Arzat
- Fernando Joachín Prieto
- David Peña Muñoz
- Jose Luis Pooc Moo
- Andrea Isabel Torres Perez


