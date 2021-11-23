package hn.edu.ujcv.baleadashermanas.business

import hn.edu.ujcv.baleadashermanas.model.facturadetalle

interface IFacturaDetalleBusiness {

    fun getDetalles():List<facturadetalle>
    fun getAllDetallesInFactura(idFactura:Long):List<facturadetalle>
    fun getDetalleById(idDetalle:Long): facturadetalle
    fun saveDetalle(facturadetalle: facturadetalle): facturadetalle
    fun removeDetalle(idDetalle:Long)
    fun updateDetalle(facturadetalle: facturadetalle): facturadetalle
    fun removeAllDetalles(idFactura : Long)

}