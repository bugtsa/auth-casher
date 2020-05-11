package com.bugtsa.auth.casherauthserver.config

import org.springframework.security.oauth2.provider.ClientDetails
import org.springframework.security.oauth2.provider.ClientDetailsService
import org.springframework.security.oauth2.provider.ClientRegistrationException
import org.springframework.security.oauth2.provider.client.BaseClientDetails

class MyClientDetailsService : ClientDetailsService {
    @Throws(ClientRegistrationException::class)
    override fun loadClientByClientId(clientId: String): ClientDetails {
        val client = BaseClientDetails()
        client.accessTokenValiditySeconds = DailyValidityAccessToken().validity
        client.refreshTokenValiditySeconds = Int.MAX_VALUE
        return client
    }
}

data class DailyValidityAccessToken(val validity: Int = SECONDS_IN_MINUTES * MINUTES_IN_HOUR * HOURS_IN_DAY) {
    companion object {
        private const val SECONDS_IN_MINUTES = 60
        private const val MINUTES_IN_HOUR = 60
        private const val HOURS_IN_DAY = 24
    }
}