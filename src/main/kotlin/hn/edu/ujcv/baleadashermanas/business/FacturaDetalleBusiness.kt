package hn.edu.ujcv.baleadashermanas.business

import hn.edu.ujcv.baleadashermanas.dao.FacturaDetalleRepository
import hn.edu.ujcv.baleadashermanas.exceptions.BusinessException
import hn.edu.ujcv.baleadashermanas.exceptions.NotFoundException
import hn.edu.ujcv.baleadashermanas.model.facturadetalle
import hn.edu.ujcv.baleadashermanas.model.facturaencabezado
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.Throws

@Service
class FacturaDetalleBusiness : IFacturaDetalleBusiness{

    @Autowired
    var facturaDetalleRepository: FacturaDetalleRepository? = null

    @Throws(BusinessException::class)
    override fun getDetalles(): List<facturadetalle> {
        try{
            return facturaDetalleRepository!!.findAll()
        }catch (e: Exception){
            throw BusinessException(e.message)
        }
    }

    @Throws(BusinessException::class)
    override fun getAllDetallesInFactura(idFactura: Long): List<facturadetalle> {
        val opt: Optional<List<facturadetalle>>
        try{
            opt = facturaDetalleRepository!!.findByidfactura(idFactura)
        }catch (e:Exception){
            throw BusinessException(e.message)
        }
        if(!opt.isPresent){
            throw BusinessException("Debe ingresar un id")
        }
        if(!opt.isPresent){
            throw NotFoundException("No se encontro la factura $idFactura")
        }
        return opt.get()
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun getDetalleById(idDetalle: Long): facturadetalle {
        val opt: Optional<facturadetalle>
        try{
            opt = facturaDetalleRepository!!.findById(idDetalle)
        }catch (e:Exception){
            throw BusinessException(e.message)
        }
        if(!opt.isPresent){
            throw BusinessException("Debe ingresar un id")
        }
        if(!opt.isPresent){
            throw NotFoundException("No se encontro el detalle $idDetalle")
        }
        return opt.get()
    }

    @Throws(BusinessException::class)
    override fun saveDetalle(facturadetalle: facturadetalle): facturadetalle {
        try{
            validarEspacios(facturadetalle)
            return facturaDetalleRepository!!.save(facturadetalle)
        }catch(e:Exception){
            throw BusinessException(e.message)
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun removeDetalle(idDetalle: Long) {
        val opt: Optional<facturadetalle>
        try{
            opt = facturaDetalleRepository!!.findById(idDetalle)
        }catch(e:Exception){
            throw BusinessException(e.message)
        }
        if(!opt.isPresent){
            throw NotFoundException("No se encontró el detalle $idDetalle")
        }
        else{
            try{
                facturaDetalleRepository!!.deleteById(idDetalle)
            }catch (e: java.lang.Exception){
                throw BusinessException(e.message)
            }
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun removeAllDetalles(idFactura: Long) {
        val opt: Optional<List<facturadetalle>>
        try{
            opt = facturaDetalleRepository!!.findByidfactura(idFactura)
        }catch(e:Exception){
            throw BusinessException(e.message)
        }
        if(!opt.isPresent){
            throw NotFoundException("No se encontró la factura $idFactura")
        }
        else{
            try{
                facturaDetalleRepository!!.deleteByidfactura(idFactura)
            }catch (e: java.lang.Exception){
                throw BusinessException(e.message)
            }
        }
    }

    @Throws(BusinessException::class)
    override fun updateDetalle(facturadetalle: facturadetalle): facturadetalle {
        val opt: Optional<facturadetalle>
        try{
            opt = facturaDetalleRepository!!.findById(facturadetalle.iddetalle)
        }catch (e: Exception){
            throw BusinessException(e.message)
        }
        if(!opt.isPresent){
            throw NotFoundException("No se encontro la factura ${facturadetalle.iddetalle}")
        }
        else{
            try{
                validarEspacios(facturadetalle)
                return facturaDetalleRepository!!.save(facturadetalle)
            }catch(e: java.lang.Exception){
                throw BusinessException(e.message)
            }
        }
        return opt.get()
    }



    // validaciones

    fun validarEspacios(facturadetalle: facturadetalle){

        if(facturadetalle.idfactura.toString().isEmpty()){
            throw Exception("El id de la factura no puede estar vacio")
        }
        if(facturadetalle.idproducto.toString().isEmpty()){
            throw Exception("El id del producto no puede estar vacio")
        }
        if(facturadetalle.cantidadfactura.isEmpty()){
            throw Exception("La cantidad no puede estar vacia")
        }
        if(facturadetalle.precio.isEmpty()){
            throw Exception("El precio no puede estar vacio")
        }
    }

}