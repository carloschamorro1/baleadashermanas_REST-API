package hn.edu.ujcv.baleadashermanas.utils

import org.springframework.http.HttpStatus

class RestApiError(val httpStatus: HttpStatus, errorMessage: String, detallesError:String) {

}