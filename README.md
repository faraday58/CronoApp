# Instrucciones para implementar las operaciones de básicas de Room para esta aplicación
Seguir las instrucciones para lograr modificar los registros de tiempos almacenados así como
eliminarlos según sea el caso.


## Crea un Archivo llamado EditView

El archivo **EditView** debe contener una función Componible del mismo nombre que reciba como parámetros
_navController_, _cronometroVM_, _dataVM_ de tipo DataViewModel y el _id_ de tipo Long.
La estructura de EditView debe ser muy semejante a la que se tiene en [AddView](https://github.com/faraday58/CronoApp/blob/database_editview/app/src/main/java/com/mexiti/cronoapp/ui/views/AddView.kt)
con el único cambio, que ahora se invoca a la función componible **ContentEditView** que recibe como argumentos los parámetros
de EditView.

### Genera la función componible ContentEditView

La función ContenEditView también debe recibir como parámetro _it_ para separar los PaddingValues. En el cuerpo de la función
también agrega un variable llamada _state_ que almacene el estado del cronómetro si se encuentra activo. Mediante la función _LaunchedEffect_
lanza la corrutina _cronos()_ de acuerdo al estado del cronómetro. Agrega un segundo  _LaunchedEffect_ que obtenga el 
