package ies.sequeros.dam.pmdm.gestionperifl.ui.components.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import ies.sequeros.dam.pmdm.gestionperifl.ui.home.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeComponent(){
    val vm: HomeViewModel = koinViewModel()

    Column(
        Modifier.fillMaxSize()
    ){
        Text("Home")
        Text("Bienvenido")
    }
}