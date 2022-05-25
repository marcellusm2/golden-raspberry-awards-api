package com.texoit.movieawards.adapters.db

import com.texoit.movieawards.domain.model.Studio
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.r2dbc.annotation.R2dbcRepository
import io.micronaut.data.repository.jpa.criteria.PredicateSpecification
import io.micronaut.data.repository.jpa.kotlin.CoroutineJpaSpecificationExecutor
import io.micronaut.data.repository.kotlin.CoroutineCrudRepository
import java.util.UUID

@R2dbcRepository(dialect = Dialect.H2)
interface StudioRepository: CoroutineCrudRepository<Studio, UUID>, CoroutineJpaSpecificationExecutor<Studio> {

    fun findByName(name: String): Studio? = findOne(Specifications.nameEquals(name))

    object Specifications {
        fun nameEquals(name: String?) = PredicateSpecification<Studio> { root, criteriaBuilder ->
            criteriaBuilder.equal(root.get<Any>("name"), name)
        }
    }
}