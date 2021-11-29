package hn.edu.ujcv.baleadashermanas.dao

import hn.edu.ujcv.baleadashermanas.model.empleado
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.*
import javax.transaction.Transactional

interface EmpleadoRepository:JpaRepository<empleado, Long>{
    fun findByDniempleado(dniempleado: String): Optional<empleado>
    fun findByusuario(usuario: String): Optional<empleado>

    @Query("SELECT t FROM empleado t WHERE t.usuario = ?1 AND t.contraseña = ?2")
    fun findByusuarioAndcontraseña(usuario: String?, contrasena: String?): Optional<empleado>


    // update empleado
    @Transactional
    @Modifying
    @Query("UPDATE empleado t SET t.primer_nombre_empleado = ?1, t.segundo_nombre_empleado = ?2, t.primer_apellido_empleado = ?3, t.segundo_apellido_empleado = ?4, t.telefono_empleado = ?5, t.email_empleado = ?6, t.dniempleado = ?7, t.usuario = ?8 WHERE t.dniempleado = ?9")
    fun updateEmpleado(primer_nombre_empleado: String?, segundo_nombre_empleado: String?, primer_apellido_empleado: String?, segundo_apellido_empleado: String?, telefono_empleado: String?, email_empleado: String?, dniempleado: String?, usuario: String?, dniempleado2: String?) : Int



}