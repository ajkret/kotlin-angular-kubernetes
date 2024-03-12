package com.dersommer.todolist.exceptions

/**
 * Custom exception. I chose the @ControllerAdvice approach, which means, throwing exceptions
 * without handling the catches in each layer, excepts when necessary for the business POV.
 */
class ApplicationException : RuntimeException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(message: String, t: Throwable) : super(message, t)
}