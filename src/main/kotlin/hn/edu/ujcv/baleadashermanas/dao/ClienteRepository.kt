package hn.edu.ujcv.baleadashermanas.dao

import hn.edu.ujcv.baleadashermanas.model.cliente
import hn.edu.ujcv.baleadashermanas.model.empleado
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ClienteRepository : JpaRepository<cliente, Long>{
    fun findByDnicliente(dnicliente: String): Optional<cliente>
}