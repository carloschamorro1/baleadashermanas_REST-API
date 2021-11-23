package hn.edu.ujcv.baleadashermanas.utils

class Constants {
    companion object{
        private const val URL_API_BASE    ="/api"
        private const val URL_API_VERSION ="/v1"
        private const val URL_BASE        = URL_API_BASE + URL_API_VERSION
        const val URL_BASE_EMPLEADO             ="$URL_BASE/empleado"
        const val URL_BASE_CLIENTE              ="$URL_BASE/cliente"
        const val URL_BASE_INVENTARIO           ="$URL_BASE/inventario"
        const val URL_BASE_FACTURA_ENCABEZADO   ="$URL_BASE/facturaencabezado"
        const val URL_BASE_FACTURA_DETALLE      ="$URL_BASE/facturadetalle"
    }
}