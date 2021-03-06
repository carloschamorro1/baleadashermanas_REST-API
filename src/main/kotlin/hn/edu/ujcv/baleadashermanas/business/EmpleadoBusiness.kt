package hn.edu.ujcv.baleadashermanas.business

import hn.edu.ujcv.baleadashermanas.dao.EmpleadoRepository
import hn.edu.ujcv.baleadashermanas.exceptions.BusinessException
import hn.edu.ujcv.baleadashermanas.exceptions.NotFoundException
import hn.edu.ujcv.baleadashermanas.model.empleado
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


@Service
class EmpleadoBusiness: IEmpleadoBusiness{
    @Autowired
    val empleadoRepository: EmpleadoRepository? = null

    @Throws(BusinessException::class)
    override fun getEmpleado(): List<empleado> {
        try{
            return empleadoRepository!!.findAll()
        }catch (e: Exception){
            throw BusinessException(e.message)
        }
    }
    @Throws(BusinessException::class, NotFoundException::class)
    override fun getEmpleadoById(idEmpleado: Long): empleado {
        val opt: Optional<empleado>
        try{
            opt = empleadoRepository!!.findById(idEmpleado)
        }catch (e:Exception){
            throw BusinessException(e.message)
        }
        if(!opt.isPresent){
            throw BusinessException("Debe ingresar un id")
        }
        if(!opt.isPresent){
            throw NotFoundException("No se encontro el empleado $idEmpleado")
        }
        return opt.get()
    }

    @Throws(BusinessException::class)
    override fun saveEmpleado(empleado: empleado): empleado {
        try{
            validarEspacios(empleado)
            validarDni(empleado.dniempleado)
            existeUsuario(empleado.usuario)
            existeEmpleado(empleado.dniempleado)
            validarLongitudTelefono(empleado.telefono_empleado)
            return empleadoRepository!!.save(empleado)
        }catch(e:Exception){
            throw BusinessException(e.message)
        }
    }
    @Throws(BusinessException::class, NotFoundException::class)
    override fun removeEmpleado(idEmpleado: Long) {
        val opt: Optional<empleado>
        try{
            opt = empleadoRepository!!.findById(idEmpleado)
        }catch(e:Exception){
            throw BusinessException(e.message)
        }
        if(!opt.isPresent){
            throw NotFoundException("No se encontr?? el empleado $idEmpleado")
        }
        else{
            try{
                empleadoRepository!!.deleteById(idEmpleado)
            }catch (e: java.lang.Exception){
                throw BusinessException(e.message)
            }
        }
    }
    @Throws(BusinessException::class, NotFoundException::class)
    override fun getEmpleadoByDniempleado(dni: String): empleado {
        val opt: Optional<empleado>
        try{
            opt = empleadoRepository!!.findByDniempleado(dni)
        }catch (e:Exception){
            throw BusinessException(e.message)
        }
        if(!opt.isPresent){
            throw NotFoundException("No se encontro el dni $dni")
        }
        return opt.get()
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun getBycontrase??a(usuario: String, contrase??a: String): empleado {
        val opt: Optional<empleado>
        try{
            opt = empleadoRepository!!.findByusuarioAndcontrase??a(usuario, contrase??a)
        }catch (e:Exception){
            throw BusinessException(e.message)
        }
        if(!opt.isPresent){
            throw NotFoundException("No se encontro el usuario o contrase??a")
        }
        return opt.get()
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun updateEmpleado(empleado: empleado): empleado {
        val opt: Optional<empleado>
        try{
            opt = empleadoRepository!!.findById(empleado.idempleado)
        }catch (e: Exception){
            throw BusinessException(e.message)
        }
        if(!opt.isPresent){
            throw NotFoundException("No se encontro el empleado ${empleado.idempleado}")
        }
        else{
            try{
                validarEspaciosActualizar(empleado)
                validarDni(empleado.dniempleado)
                existeUsuario(empleado.usuario)
                existeEmpleado(empleado.dniempleado)
                validarLongitudTelefono(empleado.telefono_empleado)
                if(empleado.contrase??a.isEmpty()){
                     empleadoRepository!!.updateEmpleado(
                        empleado.primer_nombre_empleado,empleado.segundo_nombre_empleado,empleado.primer_apellido_empleado,
                        empleado.segundo_apellido_empleado,empleado.telefono_empleado,empleado.email_empleado,empleado.dniempleado,
                        empleado.usuario,empleado.dniempleado
                    )
                    return empleadoRepository!!.getById(empleado.idempleado)
                }
                return empleadoRepository!!.save(empleado)
            }catch(e: java.lang.Exception){
                throw BusinessException(e.message)
            }
        }
        return opt.get()
    }



    // Validaciones

    @Throws(BusinessException::class)
    fun validarEspacios(empleado: empleado){
        if(empleado.primer_nombre_empleado.toString().isEmpty()){
            throw BusinessException("El primer nombre del empleado no debe estar vac??o")
        }
        if(empleado.segundo_nombre_empleado.toString().isEmpty()){
            throw BusinessException("El segundo nombre del empleado no debe estar vac??o")
        }
        if(empleado.primer_apellido_empleado.toString().isEmpty()){
            throw BusinessException("El primer apellido del empleado no debe estar vac??o")
        }
        if(empleado.segundo_apellido_empleado.toString().isEmpty()){
            throw BusinessException("El segundo apellido del empleado no debe estar vac??o")
        }
        if(empleado.dniempleado.toString().isEmpty()){
            throw BusinessException("El dni del empleado no debe estar vac??o")
        }
        if(empleado.telefono_empleado.toString().isEmpty()){
            throw BusinessException("El tel??fono del empleado no debe estar vac??o")
        }
        if(empleado.email_empleado.toString().isEmpty()){
            throw BusinessException("El email del empleado no debe estar vac??o")
        }
        if(empleado.usuario.toString().isEmpty()){
            throw BusinessException("El usuario del empleado no debe estar vac??o")
        }
        if(empleado.contrase??a.toString().isEmpty()){
            throw BusinessException("La contrase??a del empleado no debe estar vac??a")
        }
    }

    @Throws(BusinessException::class)
    fun validarEspaciosActualizar(empleado: empleado){
        if(empleado.primer_nombre_empleado.toString().isEmpty()){
            throw BusinessException("El primer nombre del empleado no debe estar vac??o")
        }
        if(empleado.segundo_nombre_empleado.toString().isEmpty()){
            throw BusinessException("El segundo nombre del empleado no debe estar vac??o")
        }
        if(empleado.primer_apellido_empleado.toString().isEmpty()){
            throw BusinessException("El primer apellido del empleado no debe estar vac??o")
        }
        if(empleado.segundo_apellido_empleado.toString().isEmpty()){
            throw BusinessException("El segundo apellido del empleado no debe estar vac??o")
        }
        if(empleado.dniempleado.toString().isEmpty()){
            throw BusinessException("El dni del empleado no debe estar vac??o")
        }
        if(empleado.telefono_empleado.toString().isEmpty()){
            throw BusinessException("El tel??fono del empleado no debe estar vac??o")
        }
        if(empleado.email_empleado.toString().isEmpty()){
            throw BusinessException("El email del empleado no debe estar vac??o")
        }
        if(empleado.usuario.toString().isEmpty()){
            throw BusinessException("El usuario del empleado no debe estar vac??o")
        }
    }

    @Throws(BusinessException::class)
    fun validarDni(dni: String): Boolean {
        if(dni.length != 13){
            throw BusinessException("El dni debe tener 13 caracteres")
        }
        if(!dni.matches("[0-9]+".toRegex())){
            throw BusinessException("El dni debe contener solo n??meros")
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
    fun existeUsuario(usuario: String){
        try{
            empleadoRepository!!.findByusuario(usuario)
        }catch (e: Exception){
            throw BusinessException("El usuario ya existe")
        }
    }

    @Throws(BusinessException::class)
    fun existeEmpleado(dni: String){
        try{
            empleadoRepository!!.findByDniempleado(dni)
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
                throw BusinessException("El n??mero de tel??fono debe comenzar con: 2,3,7,8 o 9")
            }
        }
        else {
            throw BusinessException("El n??mero de tel??fono debe tener 8 d??gitos")
            return false
        }
    }


}