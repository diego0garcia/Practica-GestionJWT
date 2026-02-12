package ies.sequeros.dam.pmdm.gestionperifl.ui.components.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ies.sequeros.dam.pmdm.gestionperifl.ui.home.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfileComponent(){
    val vm: HomeViewModel = koinViewModel()
    Column (modifier = Modifier.fillMaxSize()){
        Text("Usuario: " + vm.sesionManager.currentUser.value?.username)
    }
}