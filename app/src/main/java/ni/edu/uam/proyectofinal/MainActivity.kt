package ni.edu.uam.proyectofinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import androidx.navigation.compose.NavHost
import ni.edu.uam.proyectofinal.ui.theme.ProyectoFinalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ProyectoFinalTheme {
                AppNavegacion()
            }
        }
    }
}

//////////////////////////////////////////////////////
// 🧠 POO: Modelo de datos
//////////////////////////////////////////////////////

data class Gato(
    val nombre: String,
    val imagen: Int,
    val descripcion: String
)

//////////////////////////////////////////////////////
// 🚀 Navegación
//////////////////////////////////////////////////////

@Composable
fun AppNavegacion() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "inicio"
    ) {

        composable("inicio") {
            PantallaInicio(navController)
        }

        composable("detalle") {
            PantallaDetalle(navController)
        }

        composable("perfil") {
            PantallaPerfil(navController)
        }

        composable("post/{imagen}/{nombre}") { backStackEntry ->
            val imagen = backStackEntry.arguments?.getString("imagen")?.toInt() ?: 0
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            PantallaPost(navController, imagen, nombre)
        }
    }
}

//////////////////////////////////////////////////////
// 🏠 INICIO
//////////////////////////////////////////////////////

@Composable
fun PantallaInicio(navController: NavController) {

    var nombreUsuario by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFFFFF8E1),
                        Color(0xFFFFE0B2),
                        Color.White
                    )
                )
            )
            .padding(20.dp),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.michiapplogo),
            contentDescription = "Logo",
            modifier = Modifier.height(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("¡La Red Social Gatuna! 🐱")

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = nombreUsuario,
            onValueChange = { nombreUsuario = it },
            label = { Text("Tu nombre") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("detalle") }
        ) {
            Text("Entrar al Feed")
        }
    }
}

//////////////////////////////////////////////////////
// 📄 FEED
//////////////////////////////////////////////////////

@Composable
fun PantallaDetalle(navController: NavController) {

    val gatos = listOf(
        Gato("Leo", R.drawable.gato1, "Le gusta dormir"),
        Gato("Milo", R.drawable.gato2, "Come todo el día"),
        Gato("Luna", R.drawable.gato3, "Le encanta saltar"),
        Gato("Nala", R.drawable.gato4, "Muy elegante"),
        Gato("Tom", R.drawable.gato5, "Travieso"),
        Gato("Michi", R.drawable.gato6, "Amigable"),
        Gato("Simba", R.drawable.gato7, "Rey del sofá"),
        Gato("Oreo", R.drawable.gato8, "Blanco y negro"),
        Gato("Kitty", R.drawable.gato9, "Muy dulce"),
        Gato("Garfield", R.drawable.gato10, "Ama lasagna"),
        Gato("Loki", R.drawable.gato11, "Caótico"),
        Gato("Bella", R.drawable.gato12, "Hermosa"),
        Gato("Max", R.drawable.gato13, "Energético"),
        Gato("Chloe", R.drawable.gato14, "Curiosa"),
        Gato("Oliver", R.drawable.gato15, "Dormilón")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFFE3F2FD),
                        Color(0xFFBBDEFB),
                        Color.White
                    )
                )
            )
            .padding(16.dp)
    ) {

        item {
            Text(
                "Feed Gatuno 🐱",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate("perfil") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ir a tu Perfil")
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        items(gatos) { gato ->
            Publicacion(gato, navController)
        }
    }
}

//////////////////////////////////////////////////////
// 🐱 ITEM PUBLICACIÓN
//////////////////////////////////////////////////////

@Composable
fun Publicacion(gato: Gato, navController: NavController) {

    var rating by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clickable {
                navController.navigate("post/${gato.imagen}/${gato.nombre}")
            }
    ) {

        Image(
            painter = painterResource(id = gato.imagen),
            contentDescription = gato.nombre,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(gato.nombre, style = MaterialTheme.typography.titleMedium)
        Text(gato.descripcion)

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            for (i in 1..5) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = if (i <= rating)
                        MaterialTheme.colorScheme.primary
                    else
                        Color.Gray,
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { rating = i }
                )
            }
        }
    }
}

//////////////////////////////////////////////////////
// 📌 DETALLE DE POST
//////////////////////////////////////////////////////

@Composable
fun PantallaPost(navController: NavController, imagen: Int, nombre: String) {

    var rating by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFFE3F2FD), Color.White)
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(nombre, style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = imagen),
            contentDescription = nombre,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Califica este michi ⭐")

        Row {
            for (i in 1..5) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = if (i <= rating)
                        MaterialTheme.colorScheme.primary
                    else Color.Gray,
                    modifier = Modifier
                        .size(36.dp)
                        .clickable { rating = i }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { navController.navigate("perfil") }) {
            Text("Ver perfil")
        }

        Button(onClick = { navController.popBackStack() }) {
            Text("Volver")
        }
    }
}

//////////////////////////////////////////////////////
// 👤 PERFIL
//////////////////////////////////////////////////////

@Composable
fun PantallaPerfil(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFFF3E5F5),
                        Color(0xFFE1BEE7),
                        Color.White
                    )
                )
            )
            .padding(16.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Perfil de Leo 🐱", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(id = R.drawable.catface),
            contentDescription = "Perfil",
            modifier = Modifier
                .size(140.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Leo", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(8.dp))

        Text("Analista de Software")
        Text("Le gusta pelear en techos a las 3AM")

        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(id = R.drawable.diagrama),
            contentDescription = "Post",
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { }) {
            Text("🔄 Compartir Perfil")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                navController.navigate("inicio") {
                    popUpTo("inicio") { inclusive = true }
                }
            }
        ) {
            Text("Volver al Inicio")
        }
    }
}