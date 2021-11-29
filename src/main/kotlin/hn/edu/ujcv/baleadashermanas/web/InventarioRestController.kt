package hn.edu.ujcv.baleadashermanas.web

import hn.edu.ujcv.baleadashermanas.business.IInventarioBusiness
import hn.edu.ujcv.baleadashermanas.exceptions.BusinessException
import hn.edu.ujcv.baleadashermanas.model.inventario
import hn.edu.ujcv.baleadashermanas.utils.Constants
import hn.edu.ujcv.baleadashermanas.utils.RestApiError
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(Constants.URL_BASE_INVENTARIO)
class InventarioRestController {
    @Autowired
    val inventarioBusiness: IInventarioBusiness? = null

    @GetMapping("")
    fun list(): ResponseEntity<List<inventario>> {
        return try{
            ResponseEntity(inventarioBusiness!!.getInventario(), HttpStatus.OK)
        }catch (e:Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/id/{id}")
    fun loadById(@PathVariable("id") idProducto:Long): ResponseEntity<inventario> {
        return try{
            ResponseEntity(inventarioBusiness!!.getProductoById(idProducto), HttpStatus.OK)
        }catch (e: BusinessException){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
        catch (e: BusinessException){
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("/nombreproducto/{nombreproducto}")
    fun loadBynombreproducto(@PathVariable("nombreproducto") nombreproducto:String):ResponseEntity<inventario>{
        return try{
            ResponseEntity(inventarioBusiness!!.getProductobyNombreproducto(nombreproducto),HttpStatus.OK)
        }catch (e: BusinessException){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
        catch (e:BusinessException){
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("/addproducto")
    fun insert(@RequestBody inventario: inventario):ResponseEntity<Any>{
        return try{
            inventarioBusiness!!.saveProducto(inventario)
            val responseHeader = HttpHeaders ()
            responseHeader.set("location",Constants.URL_BASE_INVENTARIO+"/"+inventario.idproducto)
            ResponseEntity(inventario,responseHeader,HttpStatus.CREATED)
        }catch (e:BusinessException){
            val apiError = RestApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Informacion enviada no es valida",e.message.toString())
            ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping("")
    fun update(@RequestBody inventario: inventario):ResponseEntity<Any>{
        return try{
            ResponseEntity(inventarioBusiness!!.updateProducto(inventario),HttpStatus.OK)
        }catch (e:BusinessException){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
        catch (e:BusinessException){
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable("id")idProducto:Long):ResponseEntity<Any>{
        return try{
            inventarioBusiness!!.removeProducto(idProducto)
            ResponseEntity(HttpStatus.OK)
        }catch (e:BusinessException){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
        catch (e:BusinessException){
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

}