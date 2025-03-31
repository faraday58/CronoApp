 # Rama con proyecto terminado
En esta rama se encuentra el proyecto terminado, con fines de probar y verificar el funcionamiento de las bases de datos utilizando ROOM.

## Clona el repositorio
Clona el repositorio y realiza un  **checkout** a la rama  _database_editview

### Verifica la versión de Java instalada

En tu computadora local verifica cuál es la versión de Java que estás utilizando, para ello abre una terminal
en Android Studio.
<img width="937" alt="abrir_terminal_android" src="https://github.com/user-attachments/assets/6452ab14-1ae9-4760-93cd-b493b256aa7d" />

Teclea el comando 

```
C:\Users\[nombre usuario]\AndroidStudioProjects\CronoApp>java --version

```

Verifica que la salida sea la  versión 17, en caso de que no sea así, instalala desde la página oficial de Oracle, el enlace
por el momento es el siguiente:
[Descarga de Java versión 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

Después de la instalación, en Android Studio navega a **File** -> **Settings** -> **Build,Execution, Deployment** -> **Build Tools** -> **Gradle**
Selecciona en Gradle JDK la versión 17.


<img width="600" alt="gradle_jdk_17" src="https://github.com/user-attachments/assets/d2440fe9-d27c-46c5-8140-a3dcd2d88b1b" />


Ahora podrás sincronizar Gradle y compilar la aplicación.

**Nota:** En caso de marcar errores en la Aplicación y no poder sincronizar o compilar, reinicia Android Studio y verifica nuevamente los pasos anteriores.
