package hn.edu.ujcv.baleadashermanas.model

import javax.persistence.*

@Entity
@Table(name = "factura_encabezado")
data class facturaencabezado(
    val cai: String,
    val idempleado: String,
    val totalfactura: String,
    val idcliente: Long,
    val fecha_factura: String,
    val cambio_factura: String,
    val pago_factura: String){

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idfactura:Long = 0

}