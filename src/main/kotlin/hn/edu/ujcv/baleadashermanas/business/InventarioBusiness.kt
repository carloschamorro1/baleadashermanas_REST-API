package hn.edu.ujcv.baleadashermanas.business

import hn.edu.ujcv.baleadashermanas.dao.InventarioRepository
import hn.edu.ujcv.baleadashermanas.exceptions.BusinessException
import hn.edu.ujcv.baleadashermanas.exceptions.NotFoundException
import hn.edu.ujcv.baleadashermanas.model.cliente
import hn.edu.ujcv.baleadashermanas.model.inventario
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class InventarioBusiness : IInventarioBusiness {

    @Autowired
    var inventarioRepository: InventarioRepository? = null

    @Throws(BusinessException::class)
    override fun getInventario(): List<inventario> {
        try{
            return inventarioRepository!!.findAll()
        }catch (e: Exception){
            throw BusinessException(e.message)
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun getProductoById(idProducto: Long): inventario {
        val opt: Optional<inventario>
        try{
            opt = inventarioRepository!!.findById(idProducto)
        }catch (e:Exception){
            throw BusinessException(e.message)
        }
        if(!opt.isPresent){
            throw BusinessException("Debe ingresar un id")
        }
        if(!opt.isPresent){
            throw NotFoundException("No se encontro el producto $idProducto")
        }
        return opt.get()
    }
    @Throws(BusinessException::class)
    override fun saveProducto(inventario: inventario): inventario {
        try{
            validarEspacios(inventario)
            existeProducto(inventario.nombreproducto)
            return inventarioRepository!!.save(inventario)
        }catch(e:Exception){
            throw BusinessException(e.message)
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun removeProducto(idProducto: Long) {
        val opt: Optional<inventario>
        try{
            opt = inventarioRepository!!.findById(idProducto)
        }catch(e:Exception){
            throw BusinessException(e.message)
        }
        if(!opt.isPresent){
            throw NotFoundException("No se encontró el producto $idProducto")
        }
        else{
            try{
                inventarioRepository!!.deleteById(idProducto)
            }catch (e: java.lang.Exception){
                throw BusinessException(e.message)
            }
        }
    }

    @Throws(BusinessException::class)
    override fun getProductobyNombreproducto(nombreProducto: String): inventario {
        val opt: Optional<inventario>
        try{
            opt = inventarioRepository!!.findByNombreproducto(nombreProducto)
        }catch(e:Exception){
            throw BusinessException(e.message)
        }
        if(!opt.isPresent){
            throw BusinessException("No se encontró el producto $nombreProducto")
        }
        return opt.get()
    }

    @Throws(BusinessException::class)
    override fun updateProducto(inventario: inventario): inventario {
        val opt: Optional<inventario>
        try{
            opt = inventarioRepository!!.findById(inventario.idproducto)
        }catch (e: Exception){
            throw BusinessException(e.message)
        }
        if(!opt.isPresent){
            throw NotFoundException("No se encontro el producto ${inventario.idproducto}")
        }
        else{
            try{
                validarEspacios(inventario)
                existeProducto(inventario.nombreproducto)
                return inventarioRepository!!.save(inventario)
            }catch(e: java.lang.Exception){
                throw BusinessException(e.message)
            }
        }
        return opt.get()
    }

    //validaciones

    @Throws(BusinessException::class)
    fun validarEspacios(inventario: inventario){
        if(inventario.nombreproducto.isEmpty()){
            throw BusinessException("El nombre del producto no puede estar vacio")
        }
        if(inventario.idempleado.toString().isEmpty()){
            throw BusinessException("El id del empleado no puede estar vacio")
        }
        if(inventario.cantidadstock.isEmpty()){
            throw BusinessException("La cantidad del producto no puede estar vacio")
        }

        if(inventario.fechaintroduccion.isEmpty()){
            throw BusinessException("La fecha de introduccion no puede estar vacio")
        }

        if(inventario.tipomovimiento.isEmpty()){
            throw BusinessException("El tipo de movimiento no puede estar vacio")
        }

        if(inventario.precio.isEmpty()){
            throw BusinessException("El precio del producto no puede estar vacio")
        }
    }


    @Throws(BusinessException::class)
    fun existeProducto(producto: String){
        try{
            inventarioRepository!!.findByNombreproducto(producto)
        }catch (e: Exception){
            throw BusinessException("El dni ya existe")
        }
    }

}