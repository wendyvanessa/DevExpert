package com.example.devexpert.data

/**
 * @ClaseSellada
 * Se definen en un mismo fichero
 * Se utilizan para representar una jerarquia de clases que heredan de una clase padre especifica.
 * con un conjunto finito de subtipos, como estados o resultados en aplicaciones
 *
 * https://www.adictosaltrabajo.com/2019/06/27/clases-selladas-y-enumerados-en-kotlin/#03
 *
 * En este caso se crean los dos tipos de clase None y ByType
 */
sealed class Filter {
    object None : Filter()
    class ByType(val type: MediaItem.Type) : Filter()
}