package com.bugtsa.auth.casherauthserver.service

import com.bugtsa.auth.casherauthserver.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AccountStatusUserDetailsChecker
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service(value = "userDetailsService")
class CustomUserDetailsService : UserDetailsService {

    @Autowired
    private lateinit var userRepository: UserRepository

    override fun loadUserByUsername(input: String): UserDetails {
        val user = userRepository.findByUsername(input)

        AccountStatusUserDetailsChecker().check(user)

        return user
    }
}