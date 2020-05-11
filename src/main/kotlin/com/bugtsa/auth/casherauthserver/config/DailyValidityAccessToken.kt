package com.bugtsa.auth.casherauthserver.config

data class DailyValidityAccessToken(val validity: Int = SECONDS_IN_MINUTES * MINUTES_IN_HOUR * HOURS_IN_DAY) {
    companion object {
        private const val SECONDS_IN_MINUTES = 60
        private const val MINUTES_IN_HOUR = 60
        private const val HOURS_IN_DAY = 24
    }
}