package com.upload.infraestructure.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLoad is a Querydsl query type for Load
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLoad extends EntityPathBase<Load> {

    private static final long serialVersionUID = -1899992095L;

    public static final QLoad load = new QLoad("load");

    public final DateTimePath<java.time.LocalDateTime> endDate = createDateTime("endDate", java.time.LocalDateTime.class);

    public final StringPath error = createString("error");

    public final StringPath fileName = createString("fileName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> startDate = createDateTime("startDate", java.time.LocalDateTime.class);

    public final StringPath status = createString("status");

    public QLoad(String variable) {
        super(Load.class, forVariable(variable));
    }

    public QLoad(Path<? extends Load> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLoad(PathMetadata metadata) {
        super(Load.class, metadata);
    }

}

