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

El código final deberá de ser similar al presentado aquí.


```
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mexiti.cronoapp.R
import com.mexiti.cronoapp.model.Cronos
import com.mexiti.cronoapp.ui.components.CircleButton
import com.mexiti.cronoapp.ui.components.MainIconButton
import com.mexiti.cronoapp.ui.components.MainTextField
import com.mexiti.cronoapp.ui.components.MainTitle
import com.mexiti.cronoapp.ui.components.formatTiempo
import com.mexiti.cronoapp.viewmodel.CronometroViewModel
import com.mexiti.cronoapp.viewmodel.DataViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditView(navController: NavController,
             cronometroVM:CronometroViewModel,
             dataVM:DataViewModel,
             id:Long
             ){

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { MainTitle(title = stringResource(R.string.edit_cronometro)) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    MainIconButton(
                        icon = Icons.Default.ArrowBack
                    ) {
                        navController.popBackStack()
                    }
                }
            )
        }
    ) {


            ContentEditView(it = it, navController, cronometroVM,dataVM,id)
    }

}

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

    Column(
        modifier = Modifier
            .padding(it)
            .padding(top = 30.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = formatTiempo(time = cronometroVM.time),
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold
        )
        Row( horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(vertical = 16.dp)
            )
        {
            CircleButton(icon = painterResource(id = R.drawable.play_arrow_24),
                enabled = !state.cronometroActivo
            ) {
                cronometroVM.iniciar()
            }
            CircleButton(icon = painterResource(id = R.drawable.pause_24),
                enabled = state.cronometroActivo
                ) {
                cronometroVM.pausar()
            }
        }
        MainTextField(value = state.title,
            onValueChange = {cronometroVM.onValue(it)},
            label = "Title")
        Button(onClick = {
            dataVM.updateCrono(
                Cronos(id = id,
                    title = state.title,
                    crono = cronometroVM.time
                    )
            )
            navController.popBackStack()
        }) {
            Text(text = "Guardar Cambios")
        }

        DisposableEffect(Unit){
            onDispose {
                cronometroVM.detener()
            }
        }

    }

}
```

## Edita archivo HomeView para mostrar los tiempos registrados

## Edita archivo NavManager
