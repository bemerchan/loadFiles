package com.upload.infraestructure.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.upload.infraestructure.entity.LoadData;
import com.upload.infraestructure.entity.QLoadData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "load-data", path = "load-data")
public interface LoadDataRepository extends JpaRepository<LoadData, Long>, QuerydslPredicateExecutor<LoadData>,
		QuerydslBinderCustomizer<QLoadData> {

	@Override
	default void customize(QuerydslBindings bindings, QLoadData root) {
		bindings.bind(String.class)
				.first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
	}

	List<LoadData> findByLoadId(Long loadId);
}
