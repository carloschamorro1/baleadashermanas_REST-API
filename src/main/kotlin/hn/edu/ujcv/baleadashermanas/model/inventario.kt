package hn.edu.ujcv.baleadashermanas.model

import javax.persistence.*

@Entity
@Table(name = "inventario")
data class inventario(val nombreproducto: String,
                      val idempleado: Long,
                      val cantidadstock: String,
                      val fechaintroduccion :String,
                      val tipomovimiento : String,
                      val precio :String){

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idproducto:Long = 0
}

