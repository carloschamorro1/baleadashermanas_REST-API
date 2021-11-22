package hn.edu.ujcv.baleadashermanas.business

import hn.edu.ujcv.baleadashermanas.model.empleado
import java.util.*

interface IEmpleadoBusiness {
    fun getEmpleado():List<empleado>
    fun getEmpleadoById(idEmpleado:Long): empleado
    fun saveEmpleado(empleado: empleado): empleado
    fun removeEmpleado(idEmpleado:Long)
    fun getEmpleadoByDniempleado(dni: String): empleado
    fun updateEmpleado(empleado: empleado): empleado
    fun getBycontraseña(usuario:String, contraseña: String): empleado
}