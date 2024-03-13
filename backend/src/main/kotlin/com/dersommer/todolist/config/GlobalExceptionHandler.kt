package com.dersommer.todolist.config

import com.dersommer.todolist.exceptions.ApplicationException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class GlobalExceptionHandler {
    private val log: Logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(ApplicationException::class)
    fun handleApplicationException(ex: ApplicationException?): ResponseEntity<String> {
        log.warn("ApplicationException: {}", ex?.message)

        return ResponseEntity(ex?.message ?: "An unexpected error occurred.", HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(ex: RuntimeException?): ResponseEntity<String> {
        log.error("Unhandled Runtime Exception:{}", ex?.message, ex);

        return ResponseEntity("An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR)
    }

    // Validations
    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun processValidationError(exception: MethodArgumentNotValidException): Map<String, String?> {
        val errors = HashMap<String, String?>()
        for (error in exception.bindingResult.fieldErrors) {
            errors[error.field] = error.defaultMessage
        }
        return errors
    }
}