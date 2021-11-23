package hn.edu.ujcv.baleadashermanas.model

import javax.persistence.*

@Entity
@Table(name="empleado")
data class empleado (val primer_nombre_empleado:String,
                     val segundo_nombre_empleado:String,
                     val primer_apellido_empleado:String,
                     val segundo_apellido_empleado: String,
                     val telefono_empleado:String,
                     val email_empleado:String,
                     val dniempleado:String,
                     val usuario:String,
                     val contraseña:String)
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idempleado:Long = 0
}


