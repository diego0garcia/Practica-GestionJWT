package ies.sequeros.dam.pmdm.gestionperifl.ui.home

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import ies.sequeros.dam.pmdm.gestionperifl.dominio.dto.User
import ies.sequeros.dam.pmdm.gestionperifl.ui.appsettings.AppSettings
import ies.sequeros.dam.pmdm.gestionperifl.ui.sesion.SesionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

//Lo del lao de las opciones
data class ItemOption(val icon: ImageVector, val action:()->Unit, val name:String)

class HomeViewModel(
    private val _sesionManager: SesionManager
) : ViewModel() {

    val sesionManager: SesionManager = _sesionManager

    private val _options = MutableStateFlow<List<ItemOption>>(emptyList())
    val options = _options

    fun setOptions(options:List<ItemOption>){
        _options.value = options.toList()
    }
}