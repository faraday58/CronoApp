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
En esta sección se deben desplegar todos los registros almacenados en las base de datos, además es donde se deben poder eliminar al realizar
un gesto de desplazamiento horizontal. Por lo cual, agrega una librería que permita implementar dicho movimiento. <br />


Agrega la librería Swipe dentro del **build.gradle.kts**  

```kotlin
    // Swipe
    implementation( "me.saket.swipe:swipe:1.1.1")
```

Ahora, dentro del **DataViewModel**  agrega una variable llamada _cronoList_ y asignale  _ _cronolist.asStateFlow()_  entre el constructor de DataViewModel y 
la función _init_. A continuación se muestra cómo debería de verse el código.

`val cronolist = _cronolist.asStateFlow()`

### Modifica el ContentHomeView

Comienza por declarar una variable _dataList_ que asigne la colección correspondiente al DataViewModel previo a la función
_LazyColumn_. El código deberá tener la siguiente estructura

```kotlin
Column(
        modifier = Modifier.padding(it)
    ) {
        val dataList by dataVM.cronoList.collectAsState()
        LazyColumn{
            /*
       Show a collection about timing
        */
       }
```
Agrega la función **Items** que reciba como argumento a _dataList_ . Ahora fija a _item_ como condición para conservar siempre la funcionalidad
de eliminar asignando a una variable llamada _delete_  y asignale la función _SwipeAction_ que se encargará de realizar la acción de eliminar
a través del atributo _onSwipe_. El código deberá de tener la siguiente distribución:

```kotlin
  LazyColumn{
            /*
       Show a collection about timing
        */
            items(dataList){
               item ->
                val delete = SwipeAction(
                    icon = rememberVectorPainter(image = Icons.Default.Delete),
                    background = Color.Red,
                    onSwipe = {
                        dataVM.deleteCrono(item)
                    }
                )
           }

```

Como segunda función agrega _SwipeableActionBox_ que refresque los elementos a mostrar después de ser eliminados. También se debe de encargar
de realizar el despliegue una CronCard y si se presiona debe de navegar hacia **EditView** pasando como parámetro el elemento al cual se hace referencia.
El código deberá tener la siguiente estructura:


```kotlin
 items(dataList){
               item ->
              //Código de SwipeAction
                
                SwipeableActionsBox(
                    startActions = listOf(delete),
                    swipeThreshold = 150.dp
                ) {
                    CronCard(title = item.title,
                        crono = formatTiempo(time = item.crono)
                    ) {
                        navController.navigate("EditView/${item.id}")
                    }

                }
            }
```

Finalmente  agrega a **HomeView**  el parámetro _dataVM:DataViewModel_ y asígnalo a _ContentHomeView_. El código deberá tener la estructura 
similar a la mostrada.


```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(navController: NavController, dataVM:DataViewModel){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { MainTitle(title = stringResource(id = R.string.app_name)) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )

        },
        floatingActionButton = {
            FloatButton {
                navController.navigate("AddView")

            }
        }
    ) {
            ContentHomeView( it = it, navController, dataVM )
    }
}

```


  
## Edita archivo NavManager

Para finalizar el ejercicio agrega una nueva ruta _composable_ que contenga como argumento _"EditView{id}"_ con la finalidad de pasar el parámetro el _id_ 
del elemento a editar. Definir también el número de parámetros que se envían a **EditView** así como el tipo de dato.


```kotlin
  composable("EditView/{id}",
            arguments = listOf(
                navArgument("id"){type = NavType.LongType}
            )){
         //Código de validación
        }
```

Finalmente valida la navegación cuando se presione el botón de back sin realizar un cambio en el registro y asignale el valor de cero. Invoca a la 
función **EditView**  con los parámetros _navController_, _cronometroVM_ ,_dataVM_ y el _id_. El código deberá de tener la siguiente estructura:

```kotlin
@Composable
fun NavManager(cronometroVM:CronometroViewModel,dataVM:DataViewModel ) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Home") {
        composable("Home") {
            HomeView(navController,dataVM)
        }
        composable("AddView") {
            AddView(navController,cronometroVM, dataVM)
        }
        composable("EditView/{id}",
            arguments = listOf(
                navArgument("id"){type = NavType.LongType}
            )){
            val id = it.arguments?.getLong("id") ?:0
            EditView(navController = navController,
                cronometroVM =cronometroVM , dataVM = dataVM, id =id )

        }

    }
}

```
