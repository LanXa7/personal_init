package org.example.personal_init.kcasbin

import org.casbin.jcasbin.config.Config
import org.casbin.jcasbin.main.Enforcer
import org.casbin.jcasbin.model.Model
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.swing.text.html.parser.DTDConstants.MODEL

@Configuration
class KCasbinConfig {

    @Bean
    fun enforcer(jimmerRbacAdapter: JimmerRbacAdapter): Enforcer {
        val text = javaClass.classLoader.getResourceAsStream(MODEL)!!.use { it.bufferedReader().readText() }
        val newModelFromString = Model.newModelFromString(text)
        return Enforcer(newModelFromString, jimmerRbacAdapter)
    }

    companion object {
        private const val MODEL = "config/rbac_with_domains_model.conf"
    }
}