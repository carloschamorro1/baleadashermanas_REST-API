package hn.edu.ujcv.baleadashermanas.dao

import hn.edu.ujcv.baleadashermanas.model.inventario
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface InventarioRepository: JpaRepository<inventario, Long>{

    fun findByNombreproducto(nombre: String): Optional<inventario>
}