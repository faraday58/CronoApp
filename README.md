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
lanza la corrutina _cronos()_ de acuerdo al estado del cronómetro. Agrega un segundo  _LaunchedEffect_ que obtenga el título y el tiempo 
cronometrado.

```
@Composable
fun ContentEditView(it: PaddingValues,
                    navController: NavController,
                    cronometroVM: CronometroViewModel,
                    dataVM: DataViewModel,
                    id: Long) {

    val state = cronometroVM.state
    LaunchedEffect(key1 = state.cronometroActivo ){
            cronometroVM.cronos()
    }

    LaunchedEffect(key1 = Unit){
        cronometroVM.getCronoById(id)
    }
```
Los elementos componibles en _ContentEditView_ deben de ser solamente el _Tiempo_, el _botón play_, el _botón de pause_, el _título_ y por último
el botón de _guardar cambios_ tal y como se muestra en el ejemplo de la vista. <br>

<p align="center">
<img width="332" alt="Captura de pantalla 2024-04-08 a la(s) 10 09 08 p m" src="https://github.com/faraday58/CronoApp/assets/18446145/58701635-8b78-41f8-8eb7-6606c48422b5">      
</p>

Para mostrar el botón de guardar cambios, agregalo dentro de MainTextField. En el argumento onClick invoca a través del viewModel correspondiente la actualización
de los datos. En el código se muestra un ejemplo del contenido de onClick en la ejecuión del botón _Guardar_ . 


```
onClick = {
            dataVM.updateCrono(
                Cronos(id = id,
                    title = state.title,
                    crono = cronometroVM.time
                    )
            )
        }
```

Finalmente para esta función _EditView_ agrega el código Dispose para detener el cronómetro después de guardar los cambios y regresar a la pantalla **HomeView**.


```
DisposableEffect(Unit ){

            onDispose {
                cronometroVM.detener()

            }
        }

```


