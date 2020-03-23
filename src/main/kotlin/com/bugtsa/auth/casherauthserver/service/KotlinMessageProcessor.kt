package com.bugtsa.auth.casherauthserver.service

import org.springframework.http.converter.ByteArrayHttpMessageConverter
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.ResourceHttpMessageConverter
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.http.converter.json.GsonHttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter
import org.springframework.http.converter.xml.SourceHttpMessageConverter
import org.springframework.util.ClassUtils
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.method.support.ModelAndViewContainer
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.xml.transform.Source


class KotlinMessageProcessor {
    // Any name you like
    // List of HttpMessageConverter
    private val messageConverters: MutableList<HttpMessageConverter<*>>

    // under org.springframework.web.servlet.mvc.method.annotation
    private val processor: RequestResponseBodyMethodProcessor

    /**
     * This method will convert the response body to the desire format.
     */
    @Throws(Exception::class)
    fun handle(returnValue: Any?, request: HttpServletRequest?,
               response: HttpServletResponse?) {
        val nativeRequest = ServletWebRequest(request, response)
        processor.handleReturnValue(returnValue, null, ModelAndViewContainer(), nativeRequest)
    }

    /**
     * @return list of message converters
     */
    fun getMessageConverters(): List<HttpMessageConverter<*>> {
        return messageConverters
    }

    companion object {
        /**
         * Below class name are copied from the framework.
         * (And yes, they are hard-coded, too)
         */
        private val jaxb2Present = ClassUtils.isPresent("javax.xml.bind.Binder", KotlinMessageProcessor::class.java.classLoader)
        private val jackson2Present = ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper", KotlinMessageProcessor::class.java.classLoader) &&
                ClassUtils.isPresent("com.fasterxml.jackson.core.JsonGenerator", KotlinMessageProcessor::class.java.classLoader)
        private val gsonPresent = ClassUtils.isPresent("com.google.gson.Gson", KotlinMessageProcessor::class.java.classLoader)
    }

    init {
        messageConverters = ArrayList()
        messageConverters.add(ByteArrayHttpMessageConverter())
        messageConverters.add(StringHttpMessageConverter())
        messageConverters.add(ResourceHttpMessageConverter())
        messageConverters.add(SourceHttpMessageConverter<Source>())
        messageConverters.add(AllEncompassingFormHttpMessageConverter())
        if (jaxb2Present) {
            messageConverters.add(Jaxb2RootElementHttpMessageConverter())
        }
        if (jackson2Present) {
            messageConverters.add(MappingJackson2HttpMessageConverter())
        } else if (gsonPresent) {
            messageConverters.add(GsonHttpMessageConverter())
        }
        processor = RequestResponseBodyMethodProcessor(messageConverters)
    }
}