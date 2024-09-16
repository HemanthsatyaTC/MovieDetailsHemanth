package com.example.moviedetails

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.moviedetails.ui.screen.loginpages.LoginPage
import com.example.moviedetails.ui.screen.loginpages.SignUpPage
import com.example.moviedetails.ui.screen.moviescreens.MovieDetailScreen
import com.example.moviedetails.ui.screen.moviescreens.MovieDetails
import com.example.moviedetails.ui.screen.moviescreens.RecommendedShowDetails
import com.example.moviedetails.ui.screen.moviescreens.ShowDetails
import com.example.moviedetails.ui.theme.MovieDetailsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieDetailsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    MyApp(navController)

                }
            }
        }
    }
}

@Composable
fun MyApp(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginPage(navController) }
        composable("signup") {

            SignUpPage(navController) }
        composable("welcome/{name}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name")
            MovieDetails(name = name ?: "", navController = navController)
            WelcomeWithBottomNav( name ?: "", navController)
        }
        composable("movie_detail/{movieId}") { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")
            MovieDetailScreen(navController, movieId ?: "")
        }
    }
}

@Composable
fun WelcomeWithBottomNav( name: String, navController: NavHostController) {
    val bottomNavController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(bottomNavController)
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = "welcome_page",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("welcome_page") { MovieDetails(name, navController = navController) }
            composable("show_page") { ShowDetails() }
            composable("recommended_page") { RecommendedShowDetails() }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem("Movies", "welcome_page", Icons.Default.Home),
        BottomNavItem("Shows", "show_page", Icons.Default.List),
        BottomNavItem("Popular", "recommended_page", Icons.Default.Star)
    )

    BottomNavigation(
        backgroundColor = Color.Cyan,
        contentColor = Color.Black
    ) {
        val currentDestination: NavDestination? = navController.currentBackStackEntryAsState().value?.destination
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

data class BottomNavItem(val title: String, val route: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)
