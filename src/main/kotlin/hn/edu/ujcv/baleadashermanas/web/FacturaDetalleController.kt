package hn.edu.ujcv.baleadashermanas.web

import hn.edu.ujcv.baleadashermanas.business.IFacturaDetalleBusiness
import hn.edu.ujcv.baleadashermanas.exceptions.BusinessException
import hn.edu.ujcv.baleadashermanas.model.facturadetalle
import hn.edu.ujcv.baleadashermanas.utils.Constants
import hn.edu.ujcv.baleadashermanas.utils.RestApiError
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(Constants.URL_BASE_FACTURA_DETALLE)
class FacturaDetalleController {

    @Autowired
    val facturaDetalleBusiness: IFacturaDetalleBusiness? = null

    @GetMapping("")
    fun list(): ResponseEntity<List<facturadetalle>> {
        return try{
            ResponseEntity(facturaDetalleBusiness!!.getDetalles(), HttpStatus.OK)
        }catch (e:Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @Transactional
    @GetMapping("/allDetalles/{id}")
    fun listAllFactura(@PathVariable("id") idfactura: Long): ResponseEntity<List<facturadetalle>> {
        return try{
            ResponseEntity(facturaDetalleBusiness!!.getAllDetallesInFactura(idfactura), HttpStatus.OK)
        }catch (e:Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/id/{id}")
    fun loadById(@PathVariable("id") idDetalle:Long): ResponseEntity<facturadetalle> {
        return try{
            ResponseEntity(facturaDetalleBusiness!!.getDetalleById(idDetalle), HttpStatus.OK)
        }catch (e: BusinessException){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
        catch (e: BusinessException){
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("/addfacturadetalle")
    fun insert(@RequestBody facturadetalle: facturadetalle):ResponseEntity<Any>{
        return try{
            facturaDetalleBusiness!!.saveDetalle(facturadetalle)
            val responseHeader = HttpHeaders ()
            responseHeader.set("location",Constants.URL_BASE_FACTURA_DETALLE+"/"+facturadetalle.iddetalle)
            ResponseEntity(facturadetalle,responseHeader,HttpStatus.CREATED)
        }catch (e:BusinessException){
            val apiError = RestApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Informacion enviada no es valida",e.message.toString())
            ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping("")
    fun update(@RequestBody facturadetalle: facturadetalle):ResponseEntity<Any>{
        return try{
            facturaDetalleBusiness!!.updateDetalle(facturadetalle)
            ResponseEntity(HttpStatus.OK)
        }catch (e:BusinessException){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
        catch (e:BusinessException){
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable("id")idDetalle:Long):ResponseEntity<Any>{
        return try{
            facturaDetalleBusiness!!.removeDetalle(idDetalle)
            ResponseEntity(HttpStatus.OK)
        }catch (e:BusinessException){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
        catch (e:BusinessException){
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @Transactional
    @DeleteMapping("/deleteAll/{id}")
    fun deleteAll(@PathVariable("id")idfactura:Long):ResponseEntity<Any>{
        return try{
            facturaDetalleBusiness!!.removeAllDetalles(idfactura)
            ResponseEntity(HttpStatus.OK)
        }catch (e:BusinessException){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
        catch (e:BusinessException){
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

}