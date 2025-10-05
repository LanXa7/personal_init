package org.example.personal_init.kcasbin

import org.casbin.jcasbin.main.Enforcer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KCasbinConfig {

    @Bean
    fun enforcer(jimmerRbacAdapter: JimmerRbacAdapter) = Enforcer(MODEL, jimmerRbacAdapter)

    companion object {
        private const val MODEL = "classpath:config/rbac_with_domains_model.conf"
    }
}