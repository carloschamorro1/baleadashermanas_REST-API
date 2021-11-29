package hn.edu.ujcv.baleadashermanas.web

import hn.edu.ujcv.baleadashermanas.business.IClienteBusiness
import hn.edu.ujcv.baleadashermanas.exceptions.BusinessException
import hn.edu.ujcv.baleadashermanas.model.cliente
import hn.edu.ujcv.baleadashermanas.utils.Constants
import hn.edu.ujcv.baleadashermanas.utils.RestApiError
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(Constants.URL_BASE_CLIENTE)
class ClienteRestController {
    @Autowired
    val clienteBusiness: IClienteBusiness? = null

    @GetMapping("")
    fun list(): ResponseEntity<List<cliente>> {
        return try{
            ResponseEntity(clienteBusiness!!.getCliente(), HttpStatus.OK)
        }catch (e:Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/id/{id}")
    fun loadById(@PathVariable("id") idCliente:Long): ResponseEntity<cliente> {
        return try{
            ResponseEntity(clienteBusiness!!.getClienteById(idCliente), HttpStatus.OK)
        }catch (e: BusinessException){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
        catch (e: BusinessException){
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("/dni/{dni}")
    fun loadByDnicliente(@PathVariable("dni") dni:String):ResponseEntity<cliente>{
        return try{
            ResponseEntity(clienteBusiness!!.getClienteByDniempleado(dni),HttpStatus.OK)
        }catch (e: BusinessException){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
        catch (e:BusinessException){
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("/addcliente")
    fun insert(@RequestBody cliente: cliente):ResponseEntity<Any>{
        return try{
            clienteBusiness!!.saveCliente(cliente)
            val responseHeader = HttpHeaders ()
            responseHeader.set("location",Constants.URL_BASE_CLIENTE+"/"+cliente.idcliente)
            ResponseEntity(cliente,responseHeader,HttpStatus.CREATED)
        }catch (e:BusinessException){
            val apiError = RestApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Informacion enviada no es valida",e.message.toString())
            ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping("")
    fun update(@RequestBody cliente: cliente):ResponseEntity<Any>{
        return try{
            ResponseEntity(clienteBusiness!!.updateCliente(cliente),HttpStatus.OK)
        }catch (e:BusinessException){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
        catch (e:BusinessException){
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable("id")idCliente:Long):ResponseEntity<Any>{
        return try{
            clienteBusiness!!.removeCliente(idCliente)
            ResponseEntity(HttpStatus.OK)
        }catch (e:BusinessException){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
        catch (e:BusinessException){
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}