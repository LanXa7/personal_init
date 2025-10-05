package org.example.personal_init.kcasbin

import org.babyfish.jimmer.sql.ast.mutation.SaveMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.`eq?`
import org.casbin.jcasbin.model.Model
import org.casbin.jcasbin.persist.Adapter
import org.casbin.jcasbin.persist.Helper
import org.example.personal_init.entity.*
import org.springframework.stereotype.Component

@Component
class JimmerRbacAdapter(
    private val sql: KSqlClient
) : Adapter {

    override fun loadPolicy(model: Model) {
        val rbacPolicies = sql.executeQuery(RbacPolicy::class) {
            select(table)
        }
        for (policy in rbacPolicies) {
            loadPolicyLine(policy, model)
        }
    }

    private fun loadPolicyLine(line: RbacPolicy, model: Model) {
        val lineText = listOfNotNull(
            line.type, line.v0, line.v1, line.v2, line.v3, line.v4, line.v5
        ).joinToString(",")
        Helper.loadPolicyLine(lineText, model)
    }

    override fun savePolicy(model: Model) {
        val entities: MutableList<RbacPolicy> = mutableListOf()
        for (entry in model.model["p"]!!.entries) {
            val pType = entry.key
            val ast = entry.value
            for (policy in ast.policy) {
                savePolicyLine(pType, policy).also(entities::add)
            }
        }
        for (entry in model.model["g"]!!.entries) {
            val gType = entry.key
            val ast = entry.value
            for (policy in ast.policy) {
                savePolicyLine(gType, policy).also(entities::add)
            }
        }
        sql.saveEntities(entities, SaveMode.INSERT_ONLY)
    }

    private fun savePolicyLine(pType: String, rule: List<String>): RbacPolicy {
        return RbacPolicy {
            this.type = pType
            rule.forEachIndexed { index, value ->
                when (index) {
                    0 -> this.v0 = value
                    1 -> this.v1 = value
                    2 -> this.v2 = value
                    3 -> this.v3 = value
                    4 -> this.v4 = value
                    5 -> this.v5 = value
                }
            }
        }
    }

    override fun addPolicy(sce: String, type: String, policies: List<String>) {
        if (policies.isEmpty()) {
            return
        }
        val line = savePolicyLine(type, policies)
        sql.save(line, SaveMode.INSERT_ONLY)
    }


    override fun removePolicy(sce: String, type: String, policies: List<String>) {
        if (policies.isEmpty()) {
            return
        }
        removeFilteredPolicy(sce, type, 0, *policies.toTypedArray())
    }

    override fun removeFilteredPolicy(
        sce: String,
        type: String,
        fieldIndex: Int,
        vararg fieldValues: String
    ) {
        val values = fieldValues.toList().takeIf { it.isNotEmpty() } ?: return
        sql.executeDelete(RbacPolicy::class) {
            where(table.type eq type)
            val fields =
                listOf(table.v0, table.v1, table.v2, table.v3, table.v4, table.v5)
            fields.zip(values).forEach { (field, value) ->
                where(field `eq?` value)
            }
        }
    }
}