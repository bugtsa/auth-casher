package com.bugtsa.auth.casherauthserver.service

import com.bugtsa.auth.casherauthserver.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.security.authentication.AccountStatusUserDetailsChecker
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.regex.Matcher
import java.util.regex.Pattern

@Service(value = "userDetailsService")
class CustomUserDetailsService : UserDetailsService {

    @Autowired
    private lateinit var userRepository: UserRepository

    override fun loadUserByUsername(input: String): UserDetails {
        return try {
            val user = if (isValidEmail(input)) {
                userRepository.findByEmail(input)
            } else {
                userRepository.findByUsername(input)
            }
            AccountStatusUserDetailsChecker().check(user)
            user
        } catch (e: EmptyResultDataAccessException) {
            throw UsernameNotFoundException(input)
        }
    }

    private fun isValidEmail(email: String?): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(email ?: "")
        return matcher.matches()
    }
}