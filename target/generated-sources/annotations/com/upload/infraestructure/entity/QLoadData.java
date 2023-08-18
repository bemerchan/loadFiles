package com.upload.infraestructure.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLoadData is a Querydsl query type for LoadData
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLoadData extends EntityPathBase<LoadData> {

    private static final long serialVersionUID = 521533227L;

    public static final QLoadData loadData = new QLoadData("loadData");

    public final StringPath description = createString("description");

    public final StringPath id = createString("id");

    public final NumberPath<Long> loadId = createNumber("loadId", Long.class);

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final NumberPath<Double> price = createNumber("price", Double.class);

    public final StringPath recordId = createString("recordId");

    public final DateTimePath<java.time.LocalDateTime> startTime = createDateTime("startTime", java.time.LocalDateTime.class);

    public QLoadData(String variable) {
        super(LoadData.class, forVariable(variable));
    }

    public QLoadData(Path<? extends LoadData> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLoadData(PathMetadata metadata) {
        super(LoadData.class, metadata);
    }

}

