package com.bugtsa.auth.casherauthserver.service

import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.AuthenticationEntryPoint
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class KotlinModernEntryPoint : AuthenticationEntryPoint {
    // The class from Step 1
    private val processor: KotlinMessageProcessor = KotlinMessageProcessor()

    @Throws(IOException::class, ServletException::class, Exception::class, UsernameNotFoundException::class)
    override fun commence(request: HttpServletRequest,
                          response: HttpServletResponse, authException: AuthenticationException) {

        // This object is just like the model class,
        // the processor will convert it to appropriate format in response body
        val returnValue = CustomExceptionObject()
        try {
            processor.handle(returnValue, request, response)
        } catch (e: Exception) {
            throw ServletException()
        }
    }

    internal inner class CustomExceptionObject
}
