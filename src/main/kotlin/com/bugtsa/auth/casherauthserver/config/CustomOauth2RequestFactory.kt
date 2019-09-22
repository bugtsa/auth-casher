package com.bugtsa.auth.casherauthserver.config;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.oauth2.provider.ClientDetails
import org.springframework.security.oauth2.provider.ClientDetailsService
import org.springframework.security.oauth2.provider.TokenRequest
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory
import org.springframework.security.oauth2.provider.token.TokenStore

class CustomOauth2RequestFactory(clientDetailsService: ClientDetailsService) : DefaultOAuth2RequestFactory(clientDetailsService) {

    @Autowired
    private lateinit var tokenStore: TokenStore

    @Autowired
    private lateinit var userDetailsService: UserDetailsService


    override fun createTokenRequest(requestParameters: kotlin.collections.Map<String, String>,
                                    authenticatedClient: ClientDetails): TokenRequest {
        if (requestParameters["grant_type"] == "refresh_token") {
            val authentication = tokenStore!!.readAuthenticationForRefreshToken(
                    tokenStore!!.readRefreshToken(requestParameters["refresh_token"]))
            SecurityContextHolder.getContext()
                    .setAuthentication(UsernamePasswordAuthenticationToken(authentication.getName(), null,
                            userDetailsService!!.loadUserByUsername(authentication.getName()).getAuthorities()))
        }
        return super.createTokenRequest(requestParameters, authenticatedClient)
    }
}
