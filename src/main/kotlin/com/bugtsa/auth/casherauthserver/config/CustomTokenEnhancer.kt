package com.bugtsa.auth.casherauthserver.config;

import com.bugtsa.auth.casherauthserver.entity.User
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import java.util.*

class CustomTokenEnhancer : JwtAccessTokenConverter() {
    override fun enhance(accessToken: OAuth2AccessToken, authentication: OAuth2Authentication): OAuth2AccessToken {
        val user = authentication.principal as User

        val info = LinkedHashMap<String, Any>(accessToken.additionalInformation)

        info["email"] = user.email ?: ""

        val customAccessToken = DefaultOAuth2AccessToken(accessToken)
        customAccessToken.additionalInformation = info

        return super.enhance(customAccessToken, authentication)
    }
}