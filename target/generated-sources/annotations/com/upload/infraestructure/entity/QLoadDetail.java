package com.upload.infraestructure.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLoadDetail is a Querydsl query type for LoadDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLoadDetail extends EntityPathBase<LoadDetail> {

    private static final long serialVersionUID = -1314045038L;

    public static final QLoadDetail loadDetail = new QLoadDetail("loadDetail");

    public final NumberPath<Integer> column = createNumber("column", Integer.class);

    public final StringPath error = createString("error");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> loadId = createNumber("loadId", Long.class);

    public final NumberPath<Integer> row = createNumber("row", Integer.class);

    public QLoadDetail(String variable) {
        super(LoadDetail.class, forVariable(variable));
    }

    public QLoadDetail(Path<? extends LoadDetail> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLoadDetail(PathMetadata metadata) {
        super(LoadDetail.class, metadata);
    }

}

