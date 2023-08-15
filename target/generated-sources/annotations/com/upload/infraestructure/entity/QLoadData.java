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

    public final StringPath id = createString("id");

    public final NumberPath<Double> price = createNumber("price", Double.class);

    public final StringPath record_id = createString("record_id");

    public final DateTimePath<java.util.Date> startTime = createDateTime("startTime", java.util.Date.class);

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

