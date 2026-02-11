package ies.sequeros.dam.pmdm.gestionperifl.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import ies.sequeros.dam.pmdm.gestionperifl.modelo.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

//Lo del lao de las opciones
data class ItemOption(val icon: ImageVector, val action:()->Unit, val name:String)
class HomeViewModel() : ViewModel() {
    private val _user = MutableStateFlow(User())
    val user = _user.asStateFlow()

    private val _options = MutableStateFlow<List<ItemOption>>(emptyList())
    val options = _options

    fun setOptions(options:List<ItemOption>){
        _options.value = options.toList()
    }
}