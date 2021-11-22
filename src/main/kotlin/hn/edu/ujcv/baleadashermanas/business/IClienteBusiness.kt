package hn.edu.ujcv.baleadashermanas.business

import hn.edu.ujcv.baleadashermanas.model.cliente

interface IClienteBusiness {

    fun getCliente():List<cliente>
    fun getClienteById(idCliente:Long): cliente
    fun saveCliente(cliente: cliente): cliente
    fun removeCliente(idCliente:Long)
    fun getClienteByDniempleado(dni: String): cliente
    fun updateCliente(cliente: cliente): cliente
}