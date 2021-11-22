package hn.edu.ujcv.baleadashermanas.business

import hn.edu.ujcv.baleadashermanas.model.inventario

interface IInventarioBusiness {

    fun getInventario():List<inventario>
    fun getProductoById(idProducto:Long): inventario
    fun saveProducto(inventario: inventario): inventario
    fun removeProducto(idProducto:Long)
    fun getProductobyNombreproducto(nombreProducto: String): inventario
    fun updateProducto(inventario: inventario): inventario
}