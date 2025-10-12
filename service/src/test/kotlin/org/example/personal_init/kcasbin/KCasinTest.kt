package org.example.personal_init.kcasbin

import org.assertj.core.api.Assertions.assertThat
import org.casbin.jcasbin.main.Enforcer
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.jdbc.Sql
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Disabled("TODO")
@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
class KCasbinTest {

    companion object {
        @Container
        val postgreSQLContainer =
            PostgreSQLContainer("postgres:15-alpine").apply {
                withDatabaseName("testdb")
                withUsername("test")
                withPassword("test")
            }

        @JvmStatic
        @DynamicPropertySource
        fun registerPgProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url") { postgreSQLContainer.jdbcUrl }
            registry.add("spring.datasource.username") { postgreSQLContainer.username }
            registry.add("spring.datasource.password") { postgreSQLContainer.password }
        }
    }

    @Autowired
    private lateinit var enforcer: Enforcer

    @BeforeEach
    fun setUp() {
        // 清空并重新加载策略
        enforcer.clearPolicy()
        enforcer.loadPolicy()
    }

    @Test
    @Sql("classpath:db/casbin_test.sql")
    fun `test jimmer rbac adapter`() {
        // 先添加策略
        enforcer.addPolicy("alice", "data1", "read")
        enforcer.addPolicy("bob", "data2", "write")

        // 重新加载策略确保从数据库读取
        enforcer.loadPolicy()

        // 测试权限
        assertThat(enforcer.enforce("alice", "data1", "read")).isTrue()
        assertThat(enforcer.enforce("alice", "data1", "write")).isFalse()
        assertThat(enforcer.enforce("bob", "data2", "write")).isTrue()
    }

    @Test
    fun `test role based access control`() {
        // 设置角色关系
        enforcer.addGroupingPolicy("alice", "admin")
        enforcer.addPolicy("admin", "data1", "read")
        enforcer.addPolicy("admin", "data1", "write")

        enforcer.loadPolicy()

        assertThat(enforcer.enforce("alice", "data1", "read")).isTrue()
        assertThat(enforcer.enforce("alice", "data1", "write")).isTrue()
    }

    @Test
    fun `test domain based access control`() {
        // 测试域权限
        enforcer.addPolicy("admin", "domain1", "data1", "read")
        enforcer.addPolicy("user", "domain2", "data1", "read")

        enforcer.loadPolicy()

        assertThat(enforcer.enforce("admin", "domain1", "data1", "read")).isTrue()
        assertThat(enforcer.enforce("user", "domain1", "data1", "read")).isFalse()
    }
}