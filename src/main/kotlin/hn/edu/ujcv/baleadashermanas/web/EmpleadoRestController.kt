package hn.edu.ujcv.baleadashermanas.web

import hn.edu.ujcv.baleadashermanas.business.IEmpleadoBusiness
import hn.edu.ujcv.baleadashermanas.exceptions.BusinessException
import hn.edu.ujcv.baleadashermanas.exceptions.NotFoundException
import hn.edu.ujcv.baleadashermanas.model.empleado
import hn.edu.ujcv.baleadashermanas.utils.Constants
import hn.edu.ujcv.baleadashermanas.utils.RestApiError
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(Constants.URL_BASE_EMPLEADO)
class EmpleadoRestController {
    @Autowired
    val empleadoBusiness: IEmpleadoBusiness? = null

    @GetMapping("")
    fun list(): ResponseEntity<List<empleado>> {
        return try{
            ResponseEntity(empleadoBusiness!!.getEmpleado(), HttpStatus.OK)
        }catch (e:Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
    @GetMapping("/id/{id}")
    fun loadById(@PathVariable("id") idEmpleado:Long):ResponseEntity<empleado>{
        return try{
            ResponseEntity(empleadoBusiness!!.getEmpleadoById(idEmpleado),HttpStatus.OK)
        }catch (e: BusinessException){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
        catch (e:BusinessException){
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("/dni/{dni}")
    fun loadByDni_empleado(@PathVariable("dni") dni:String):ResponseEntity<empleado>{
        return try{
            ResponseEntity(empleadoBusiness!!.getEmpleadoByDniempleado(dni),HttpStatus.OK)
        }catch (e: BusinessException){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
        catch (e:BusinessException){
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("/addempleado")
    fun insert(@RequestBody empleado: empleado):ResponseEntity<Any>{
        return try{
            empleadoBusiness!!.saveEmpleado(empleado)
            val responseHeader = HttpHeaders ()
            responseHeader.set("location",Constants.URL_BASE_EMPLEADO+"/"+empleado.idempleado)
            ResponseEntity(empleado,responseHeader,HttpStatus.CREATED)
        }catch (e:BusinessException){
            val apiError = RestApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Informacion enviada no es valida",e.message.toString())
            ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
    @PutMapping("")
    fun update(@RequestBody empleado: empleado):ResponseEntity<Any>{
        return try{
            empleadoBusiness!!.updateEmpleado(empleado)
            ResponseEntity(HttpStatus.OK)
        }catch (e:BusinessException){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
        catch (e:BusinessException){
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable("id")idEmpleado:Long):ResponseEntity<Any>{
        return try{
            empleadoBusiness!!.removeEmpleado(idEmpleado)
            ResponseEntity(HttpStatus.OK)
        }catch (e:BusinessException){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
        catch (e:BusinessException){
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("/login/{usuario}/{pass}")
    fun login(@PathVariable("usuario")usuario: String, @PathVariable("pass")contraseña: String): ResponseEntity<empleado>{
        return try{
            ResponseEntity(empleadoBusiness!!.getBycontraseña(usuario,contraseña),HttpStatus.OK)
        }catch (e:BusinessException){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
        catch (e:NotFoundException){
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

}