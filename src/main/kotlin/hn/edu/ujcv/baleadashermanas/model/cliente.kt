package hn.edu.ujcv.baleadashermanas.model

import javax.persistence.*

@Entity
@Table(name="cliente")
class cliente (val primer_nombre_cliente:String = "", val segundo_nombre_cliente:String = "", val primer_apellido_cliente:String = "",
               val segundo_apellido_cliente: String, val telefono_cliente:String = "", val email_cliente:String = "", val dnicliente:String = "",
               val rtncliente:String = "")
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idcliente:Long = 0
}