package com.upload.infraestructure.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.upload.infraestructure.entity.Load;
import com.upload.infraestructure.entity.QLoad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "load", path = "load")
public interface LoadRepository extends JpaRepository<Load, Long>, QuerydslPredicateExecutor<Load>,
		QuerydslBinderCustomizer<QLoad> {

	@Override
	default void customize(QuerydslBindings bindings, QLoad root) {
		bindings.bind(String.class)
				.first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
	}

}
