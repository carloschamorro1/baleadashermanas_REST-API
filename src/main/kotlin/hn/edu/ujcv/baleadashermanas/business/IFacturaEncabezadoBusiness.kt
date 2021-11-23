package hn.edu.ujcv.baleadashermanas.business

import hn.edu.ujcv.baleadashermanas.model.facturaencabezado

interface IFacturaEncabezadoBusiness {

    fun getFacturas():List<facturaencabezado>
    fun getFacturaById(idFactura:Long): facturaencabezado
    fun saveFactura(facturaencabezado: facturaencabezado): facturaencabezado
    fun removeFactura(idFactura:Long)
    fun updateFactura(facturaencabezado: facturaencabezado): facturaencabezado

}