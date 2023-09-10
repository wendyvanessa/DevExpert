package com.example.devexpert

/**
 * data class:
 * Diseñado para optimizar clases que solo almacenan datos y no tienen comportamientos especificos
 * más allá de la administración y acceso a esos datos.
 *
 * Automáticamente generan propiedades (campos), getters y setters.
 * Permite que el metodo toString muestre en un formato legible el nombre de la misma y
 * sus propiedades.
 */
data class MediaItem(val title:String, val url:String, val type: Type ){
    enum class Type{ PHOTO, VIDEO}
}