package hn.edu.ujcv.baleadashermanas.web

import hn.edu.ujcv.baleadashermanas.business.IFacturaEncabezadoBusiness
import hn.edu.ujcv.baleadashermanas.exceptions.BusinessException
import hn.edu.ujcv.baleadashermanas.model.facturaencabezado
import hn.edu.ujcv.baleadashermanas.utils.Constants
import hn.edu.ujcv.baleadashermanas.utils.RestApiError
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(Constants.URL_BASE_FACTURA_ENCABEZADO)
class FacturaEncabezadoController {
    @Autowired
    val facturaEncabezadoBusiness: IFacturaEncabezadoBusiness? = null

    @GetMapping("")
    fun list(): ResponseEntity<List<facturaencabezado>> {
        return try{
            ResponseEntity(facturaEncabezadoBusiness!!.getFacturas(), HttpStatus.OK)
        }catch (e:Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/id/{id}")
    fun loadById(@PathVariable("id") idFactura:Long): ResponseEntity<facturaencabezado> {
        return try{
            ResponseEntity(facturaEncabezadoBusiness!!.getFacturaById(idFactura), HttpStatus.OK)
        }catch (e: BusinessException){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
        catch (e: BusinessException){
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("/addfacturaencabezado")
    fun insert(@RequestBody facturaencabezado: facturaencabezado):ResponseEntity<Any>{
        return try{
            facturaEncabezadoBusiness!!.saveFactura(facturaencabezado)
            val responseHeader = HttpHeaders ()
            responseHeader.set("location",Constants.URL_BASE_FACTURA_ENCABEZADO+"/"+facturaencabezado.idfactura)
            ResponseEntity(facturaencabezado,responseHeader,HttpStatus.CREATED)
        }catch (e:BusinessException){
            val apiError = RestApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Informacion enviada no es valida",e.message.toString())
            ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping("")
    fun update(@RequestBody facturaencabezado: facturaencabezado):ResponseEntity<Any>{
        return try{
            facturaEncabezadoBusiness!!.updateFactura(facturaencabezado)
            ResponseEntity(HttpStatus.OK)
        }catch (e:BusinessException){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
        catch (e:BusinessException){
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable("id")idFactura:Long):ResponseEntity<Any>{
        return try{
            facturaEncabezadoBusiness!!.removeFactura(idFactura)
            ResponseEntity(HttpStatus.OK)
        }catch (e:BusinessException){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
        catch (e:BusinessException){
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

}