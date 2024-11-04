package com.example.pokemonapp.components

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.pokemonapp.models.PokemonDetailsModel
import com.example.pokemonapp.models.PokemonDetailsViewModel

@Composable
fun PokeAPIApp(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "list") {
        composable("list") { PokemonListScreen(navController) }
        composable(
            "details/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType }), enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(700)
                )
            }
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("id")
                ?.let { PokemonDetailsScreen(navController = navController, name = it) }
        }
    }
}

@Composable
fun PokemonDetailsScreen(
    navController: NavController,
    name: String
) {
    val viewModel = PokemonDetailsViewModel()
    // MARK: - State
    val pokemonDetails = viewModel.pokemonDetails.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val gotError = viewModel.gotError.collectAsState()

    LaunchedEffect(pokemonDetails) {
        viewModel.fetchDetails(name)
    }
    Content(
        isLoading = isLoading.value,
        gotError = gotError.value,
        pokemonDetails = pokemonDetails.value
    )
}

@Composable
private fun Content(
    isLoading: Boolean,
    gotError: Boolean,
    pokemonDetails: PokemonDetailsModel?
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if(isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(200.dp),
                color = Color.Blue,
                trackColor = Color.Red
            )
        } else if(gotError) {
            ErrorState()
        } else {
            pokemonDetails.let {details ->
                if (details != null) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(url = details.sprite.imageURL)
                        Text(
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .fillMaxWidth(),
                            text = details.name,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun AsyncImage(url: String) {
    val painter: Painter = rememberImagePainter(data = url,  builder = {
        // Optionally, you can apply transformations
        transformations(CircleCropTransformation())
    })
    Image(
        modifier = Modifier.size(200.dp),
        painter = painter,
        contentDescription = null
    )
}
