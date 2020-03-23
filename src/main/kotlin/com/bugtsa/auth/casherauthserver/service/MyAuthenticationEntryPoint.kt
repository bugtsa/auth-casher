package com.bugtsa.auth.casherauthserver.service

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@ControllerAdvice
class MyAuthenticationEntryPoint : AuthenticationEntryPoint {
    @Throws(IOException::class, ServletException::class, CustomUserDetailsService.WrongCredentialCustomException::class)
    override fun commence(request: HttpServletRequest?, response: HttpServletResponse, authException: AuthenticationException?) {
        // 401
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed")
    }

    @ExceptionHandler(value = [AccessDeniedException::class])
    @Throws(IOException::class)
    fun commence(request: HttpServletRequest?, response: HttpServletResponse,
                 accessDeniedException: AccessDeniedException) {
        // 403
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Authorization Failed : " + accessDeniedException.message)
    }

    @ExceptionHandler(value = [Exception::class])
    @Throws(IOException::class)
    fun commence(request: HttpServletRequest?, response: HttpServletResponse,
                 exception: Exception) {
        // 500
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error : " + exception.message)
    }
}