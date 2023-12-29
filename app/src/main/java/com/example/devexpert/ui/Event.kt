package com.example.devexpert.ui

/**
 * MutableLiveData es un poco peligroso por que almacena el ultimo valor que se le ha asignado
 * al momento de rotar la activity podemos tener resultados inhesperados.
 *
 * Por google la opción mas recomendada para dar mas seguridad es:
 *
 * crear un objeto Event para identificar si ya se ha tratado un evento o no
 * si ya se trato se devuelve null, de lo contrario es correcto devolver el contenido del evento.
 */

data class Event<out T>(private val content: T) {

    private var hasBeenHandled = false

    fun getContentIfNotHandled():T?{
        return if (hasBeenHandled){
            null
        }else{
            hasBeenHandled = true
            content
        }
    }
}