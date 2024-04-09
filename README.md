# Instrucciones para implementar las operaciones de básicas de Room para esta aplicación

## Crea un Archivo llamado EditView

El archivo **EditView** debe contener una función Componible del mismo nombre que reciba como parámetros
_navController_, _cronometroVM_, _dataVM_ de tipo DataViewModel y el _id_ de tipo Long.
La estructura de EditView debe ser muy semejante a la que se tiene con [AddView] (https://github.com/faraday58/CronoApp/blob/database_editview/app/src/main/java/com/mexiti/cronoapp/ui/views/AddView.kt)
con el único cambio que invoca a la función componible ContentEditView que recibe como argumentos los parámetros
de EditView.

### Genera la función componible ContentEditView
