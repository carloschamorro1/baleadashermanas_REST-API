package hn.edu.ujcv.baleadashermanas.business

import hn.edu.ujcv.baleadashermanas.dao.FacturaEncabezadoRepository
import hn.edu.ujcv.baleadashermanas.exceptions.BusinessException
import hn.edu.ujcv.baleadashermanas.exceptions.NotFoundException
import hn.edu.ujcv.baleadashermanas.model.facturaencabezado
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class FacturaEncabezadoBusiness :IFacturaEncabezadoBusiness {

    @Autowired
    var facturaEncabezadoRepository: FacturaEncabezadoRepository? = null

    @Throws(BusinessException::class)
    override fun getFacturas(): List<facturaencabezado> {
        try{
            return facturaEncabezadoRepository!!.findAll()
        }catch (e: Exception){
            throw BusinessException(e.message)
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun getFacturaById(idFactura: Long): facturaencabezado {
        val opt: Optional<facturaencabezado>
        try{
            opt = facturaEncabezadoRepository!!.findById(idFactura)
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

    @Throws(BusinessException::class)
    override fun saveFactura(facturaencabezado: facturaencabezado): facturaencabezado {
        try{
            validarEspacios(facturaencabezado)
            return facturaEncabezadoRepository!!.save(facturaencabezado)
        }catch(e:Exception){
            throw BusinessException(e.message)
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun removeFactura(idFactura: Long) {
        val opt: Optional<facturaencabezado>
        try{
            opt = facturaEncabezadoRepository!!.findById(idFactura)
        }catch(e:Exception){
            throw BusinessException(e.message)
        }
        if(!opt.isPresent){
            throw NotFoundException("No se encontró la factura $idFactura")
        }
        else{
            try{
                facturaEncabezadoRepository!!.deleteById(idFactura)
            }catch (e: java.lang.Exception){
                throw BusinessException(e.message)
            }
        }
    }

    @Throws(BusinessException::class)
    override fun updateFactura(facturaencabezado: facturaencabezado): facturaencabezado {
        val opt: Optional<facturaencabezado>
        try{
            opt = facturaEncabezadoRepository!!.findById(facturaencabezado.idfactura)
        }catch (e: Exception){
            throw BusinessException(e.message)
        }
        if(!opt.isPresent){
            throw NotFoundException("No se encontro la factura ${facturaencabezado.idfactura}")
        }
        else{
            try{
                validarEspacios(facturaencabezado)
                return facturaEncabezadoRepository!!.save(facturaencabezado)
            }catch(e: java.lang.Exception){
                throw BusinessException(e.message)
            }
        }
        return opt.get()
    }

    //Validaciones

    fun validarEspacios(facturaencabezado: facturaencabezado){
        if(facturaencabezado.cai.isEmpty()){
            throw Exception("El campo cai no puede estar vacio")
        }
        if(facturaencabezado.idempleado.isEmpty()){
            throw Exception("El ID del empleado no puede estar vacio")
        }

        if(facturaencabezado.totalfactura.isEmpty()){
            throw Exception("El campo total factura no puede estar vacio")
        }
        if(facturaencabezado.idcliente.toString().isEmpty()){
            throw Exception("El ID del cliente no puede estar vacio")
        }

        if(facturaencabezado.fecha_factura.isEmpty()){
            throw Exception("El campo fecha factura no puede estar vacio")
        }
        if(facturaencabezado.cambio_factura.isEmpty()){
            throw Exception("El campo cambio factura no puede estar vacio")
        }

        if(facturaencabezado.pago_factura.isEmpty()){
            throw Exception("El campo pago factura no puede estar vacio")
        }

    }


}