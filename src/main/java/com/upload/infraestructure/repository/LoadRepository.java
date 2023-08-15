package com.upload.infraestructure.repository;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.upload.infraestructure.entity.Load;
import com.upload.infraestructure.entity.QLoad;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;

@RepositoryRestResource(collectionResourceRel = "load", path = "load")
@Tag(name = "Load", description = "Rest Api for Load")
public interface LoadRepository extends JpaRepository<Load, Long>, QuerydslPredicateExecutor<Load>,
		QuerydslBinderCustomizer<QLoad> {

	@Override
	default void customize(QuerydslBindings bindings, QLoad root) {
		bindings.bind(String.class)
				.first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
	}

}