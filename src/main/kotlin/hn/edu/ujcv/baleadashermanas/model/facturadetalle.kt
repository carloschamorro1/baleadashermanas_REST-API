package hn.edu.ujcv.baleadashermanas.model

import javax.persistence.*

@Entity
@Table(name = "factura_detalle")
data class facturadetalle(
    var idfactura: Long,
    var idproducto: Long,
    var cantidadfactura: String,
    var precio: String) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var iddetalle:Long = 0

}