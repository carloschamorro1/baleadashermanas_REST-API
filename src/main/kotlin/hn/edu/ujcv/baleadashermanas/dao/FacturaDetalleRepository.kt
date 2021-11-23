package hn.edu.ujcv.baleadashermanas.dao

import hn.edu.ujcv.baleadashermanas.model.facturadetalle
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface FacturaDetalleRepository : JpaRepository<facturadetalle, Long>{

    fun findByidfactura(idfactura: Long): Optional<List<facturadetalle>>

    fun deleteByidfactura(idfactura:Long)
}