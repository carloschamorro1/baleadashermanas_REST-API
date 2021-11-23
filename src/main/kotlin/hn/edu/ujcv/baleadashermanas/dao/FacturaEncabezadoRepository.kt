package hn.edu.ujcv.baleadashermanas.dao

import hn.edu.ujcv.baleadashermanas.model.facturaencabezado
import org.springframework.data.jpa.repository.JpaRepository

interface FacturaEncabezadoRepository : JpaRepository<facturaencabezado, Long>{

}