package hn.edu.ujcv.baleadashermanas.business

import hn.edu.ujcv.baleadashermanas.dao.ClienteRepository
import hn.edu.ujcv.baleadashermanas.exceptions.BusinessException
import hn.edu.ujcv.baleadashermanas.exceptions.NotFoundException
import hn.edu.ujcv.baleadashermanas.model.cliente
import hn.edu.ujcv.baleadashermanas.model.empleado
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

@Service
class ClienteBusiness :IClienteBusiness {
    @Autowired
    var clienteRepository: ClienteRepository? = null

    @Throws(BusinessException::class)
    override fun getCliente(): List<cliente> {
        try{
            return clienteRepository!!.findAll()
        }catch (e: Exception){
            throw BusinessException(e.message)
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun getClienteById(idCliente: Long): cliente {
        val opt: Optional<cliente>
        try{
            opt = clienteRepository!!.findById(idCliente)
        }catch (e:Exception){
            throw BusinessException(e.message)
        }
        if(!opt.isPresent){
            throw BusinessException("Debe ingresar un id")
        }
        if(!opt.isPresent){
            throw NotFoundException("No se encontro el cliente $idCliente")
        }
        return opt.get()
    }

    @Throws(BusinessException::class)
    override fun saveCliente(cliente: cliente): cliente {
        try{
            validarEspacios(cliente)
            validarDni(cliente.dnicliente)
            validarLongitudTelefono(cliente.telefono_cliente)
            return clienteRepository!!.save(cliente)
        }catch(e:Exception){
            throw BusinessException(e.message)
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun removeCliente(idCliente: Long) {
        val opt: Optional<cliente>
        try{
            opt = clienteRepository!!.findById(idCliente)
        }catch(e:Exception){
            throw BusinessException(e.message)
        }
        if(!opt.isPresent){
            throw NotFoundException("No se encontró el cliente $idCliente")
        }
        else{
            try{
                clienteRepository!!.deleteById(idCliente)
            }catch (e: java.lang.Exception){
                throw BusinessException(e.message)
            }
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun getClienteByDniempleado(dni: String): cliente {
        val opt: Optional<cliente>
        try{
            opt = clienteRepository!!.findByDnicliente(dni)
        }catch (e:Exception){
            throw BusinessException(e.message)
        }
        if(!opt.isPresent){
            throw NotFoundException("No se encontro el dni $dni")
        }
        return opt.get()
    }

    @Throws(BusinessException::class)
    override fun updateCliente(cliente: cliente): cliente {
        val opt: Optional<cliente>
        try{
            opt = clienteRepository!!.findById(cliente.idcliente)
        }catch (e: Exception){
            throw BusinessException(e.message)
        }
        if(!opt.isPresent){
            throw NotFoundException("No se encontro el cliente ${cliente.idcliente}")
        }
        else{
            try{
                validarEspacios(cliente)
                validarDni(cliente.dnicliente)
                validarLongitudTelefono(cliente.telefono_cliente)
                return clienteRepository!!.save(cliente)
            }catch(e: java.lang.Exception){
                throw BusinessException(e.message)
            }
        }
        return opt.get()
    }


// Validaciones

    @Throws(BusinessException::class)
    fun validarEspacios(cliente: cliente){
        if(cliente.primer_nombre_cliente.toString().isEmpty()){
            throw BusinessException("El primer nombre del cliente no debe estar vacío")
        }
        if(cliente.segundo_nombre_cliente.toString().isEmpty()){
            throw BusinessException("El segundo nombre del cliente no debe estar vacío")
        }
        if(cliente.primer_apellido_cliente.toString().isEmpty()){
            throw BusinessException("El primer apellido del cliente no debe estar vacío")
        }
        if(cliente.segundo_apellido_cliente.toString().isEmpty()){
            throw BusinessException("El segundo apellido del cliente no debe estar vacío")
        }
        if(cliente.dnicliente.toString().isEmpty()){
            throw BusinessException("El dni del cliente no debe estar vacío")
        }
        if(cliente.telefono_cliente.toString().isEmpty()){
            throw BusinessException("El teléfono del cliente no debe estar vacío")
        }
        if(cliente.email_cliente.toString().isEmpty()){
            throw BusinessException("El email del cliente no debe estar vacío")
        }
        if(cliente.rtncliente.toString().isEmpty()){
            throw BusinessException("El rtn del cliente no debe estar vacío")
        }
    }


    @Throws(BusinessException::class)
    fun validarDni(dni: String): Boolean {
        if(dni.length != 13){
            throw BusinessException("El dni debe tener 13 caracteres")
        }
        if(!dni.matches("[0-9]+".toRegex())){
            throw BusinessException("El dni debe contener solo números")
        }

        if(dni.length == 13){
            if(dni.substring(0,1) == "0" || dni.substring(0,1) == "1"){
                return true
            }
            else{
                throw BusinessException("El dni debe empezar por 0 o 1")

            }
        }
        return false
    }


    @Throws(BusinessException::class)
    fun existeCliente(dni: String){
        try{
            clienteRepository!!.findByDnicliente(dni)
        }catch (e: Exception){
            throw BusinessException("El dni ya existe")
        }
    }

    @Throws(BusinessException::class)
    fun validarLongitudTelefono(texto: String): Boolean {
        if (texto.length == 8) {
            val pattern: Pattern = Pattern.compile("[23789]")
            val matcher: Matcher = pattern.matcher(texto.substring(0, 1))
            return if (matcher.matches()) {
                true
            }
            else {
                throw BusinessException("El número de teléfono debe comenzar con: 2,3,7,8 o 9")
            }
        }
        else {
            throw BusinessException("El número de teléfono debe tener 8 dígitos")
            return false
        }
    }

}