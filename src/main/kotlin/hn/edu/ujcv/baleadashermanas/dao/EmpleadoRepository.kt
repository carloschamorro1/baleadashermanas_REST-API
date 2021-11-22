package hn.edu.ujcv.baleadashermanas.dao

import hn.edu.ujcv.baleadashermanas.model.empleado
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface EmpleadoRepository:JpaRepository<empleado, Long>{
    fun findByDniempleado(dniempleado: String): Optional<empleado>
    fun findByusuario(usuario: String): Optional<empleado>

    @Query("SELECT t FROM empleado t WHERE t.usuario = ?1 AND t.contraseña = ?2")
    fun findByusuarioAndcontraseña(usuario: String?, contrasena: String?): Optional<empleado>

}