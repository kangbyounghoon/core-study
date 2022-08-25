package com.example.corestudy.node;

import org.hibernate.search.engine.backend.document.DocumentElement;
import org.hibernate.search.engine.backend.document.IndexFieldReference;
import org.hibernate.search.engine.backend.document.model.dsl.IndexSchemaElement;
import org.hibernate.search.engine.backend.types.Aggregable;
import org.hibernate.search.engine.backend.types.Projectable;
import org.hibernate.search.engine.backend.types.Searchable;
import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.bridge.TypeBridge;
import org.hibernate.search.mapper.pojo.bridge.binding.TypeBindingContext;
import org.hibernate.search.mapper.pojo.bridge.mapping.programmatic.TypeBinder;
import org.hibernate.search.mapper.pojo.bridge.runtime.TypeBridgeWriteContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

public class NodeTypeBinder implements TypeBinder {
    private static final Logger logger = LoggerFactory.getLogger(NodeTypeBinder.class);

    @Override
    public void bind(TypeBindingContext context) {
        context.dependencies().useRootOnly();


        IndexSchemaElement schemaElement = context.indexSchemaElement();

        IndexFieldReference<String> nodeIdField = schemaElement.field("nodeId",
                f -> f.asString().normalizer("lowercase").sortable(Sortable.YES).projectable(Projectable.YES)).toReference();

        IndexFieldReference<String> typeIdField = schemaElement.field("typeId",
                f -> f.asString().normalizer("lowercase").aggregable(Aggregable.YES).projectable(Projectable.YES)).toReference();

        IndexFieldReference<String> ownerField = schemaElement.field("owner",
                f -> f.asString().normalizer("lowercase").aggregable(Aggregable.YES).projectable(Projectable.YES)).toReference();

        IndexFieldReference<String> modifierField = schemaElement.field("modifier",
                f -> f.asString().normalizer("lowercase").aggregable(Aggregable.YES).projectable(Projectable.YES)).toReference();

        IndexFieldReference<Long> orderNoField = schemaElement.field("orderNo",
                f -> f.asLong().sortable(Sortable.YES).projectable(Projectable.YES)).toReference();

        IndexFieldReference<Instant> createdField = schemaElement.field("created",
                f -> f.asInstant().sortable(Sortable.YES).projectable(Projectable.YES)).toReference();

        IndexFieldReference<Instant> changedField = schemaElement.field("changed",
                f -> f.asInstant().sortable(Sortable.YES).projectable(Projectable.YES)).toReference();

        IndexFieldReference<String> parentNodeIdField = schemaElement.field("parentNodeId",
                f -> f.asString().normalizer("lowercase").sortable(Sortable.YES).projectable(Projectable.YES)).toReference();


        schemaElement.fieldTemplate(
                        "properties_bool",
                        f -> f.asBoolean().sortable(Sortable.YES).projectable(Projectable.YES)
                )
                .matchingPathGlob("*__bool");


        schemaElement.fieldTemplate(
                        "properties_int",
                        f -> f.asInteger().sortable(Sortable.YES).projectable(Projectable.YES)
                )
                .matchingPathGlob("*__int");

        schemaElement.fieldTemplate(
                        "properties_long",
                        f -> f.asLong().sortable(Sortable.YES).projectable(Projectable.YES)
                )
                .matchingPathGlob("*__long");

        schemaElement.fieldTemplate(
                        "properties_double",
                        f -> f.asDouble().sortable(Sortable.YES).projectable(Projectable.YES)
                )
                .matchingPathGlob("*__double");

        schemaElement.fieldTemplate(
                        "properties_date",
                        f -> f.asInstant().sortable(Sortable.YES).projectable(Projectable.YES)
                )
                .matchingPathGlob("*__date");

        schemaElement.fieldTemplate(
                        "properties_file",
                        f -> f.asString().normalizer("lowercase").projectable(Projectable.YES)
                )
                .matchingPathGlob("*__file");


        schemaElement.fieldTemplate(
                        "properties_ref",
                        f -> f.asString().analyzer("code")
                )
                .matchingPathGlob("*__ref");

        schemaElement.fieldTemplate(
                        "properties_refs",
                        f -> f.asString().analyzer("code").searchAnalyzer("code")
                )
                .matchingPathGlob("*__refs");

        schemaElement.fieldTemplate(
                        "properties_id",
                        f -> f.asString().normalizer("lowercase").sortable(Sortable.YES).aggregable(Aggregable.YES).projectable(Projectable.YES)
                )
                .matchingPathGlob("*__id");


        schemaElement.fieldTemplate(
                        "properties_ids",
                        f -> f.asString().analyzer("ids").searchAnalyzer("code")
                )
                .matchingPathGlob("*__ids");

        schemaElement.fieldTemplate(
                        "properties_korean",
                        f -> f.asString().analyzer("korean").searchAnalyzer("standard").searchable(Searchable.YES)
                )
                .matchingPathGlob("*__korean");


        schemaElement.fieldTemplate(
                        "properties_cjk",
                        f -> f.asString().analyzer("cjk")
                )
                .matchingPathGlob("*__cjk");


        schemaElement.fieldTemplate(
                        "properties_code",
                        f -> f.asString().analyzer("code").searchAnalyzer("code").searchable(Searchable.YES)
                )
                .matchingPathGlob("*__code");

        schemaElement.fieldTemplate(
                        "properties_simple",
                        f -> f.asString().analyzer("simple")
                )
                .matchingPathGlob("*__simple");

        schemaElement.fieldTemplate(
                        "properties_whitespace",
                        f -> f.asString().analyzer("whitespace").searchAnalyzer("whitespace")
                )
                .matchingPathGlob("*__whitespace");


        schemaElement.fieldTemplate(
                        "properties_sort",
                        f -> f.asString().normalizer("lowercase").sortable(Sortable.YES).projectable(Projectable.YES)
                )
                .matchingPathGlob("*__sort");

        schemaElement.fieldTemplate(
                        "properties_group",
                        f -> f.asString().normalizer("lowercase").sortable(Sortable.YES).aggregable(Aggregable.YES).projectable(Projectable.YES)
                )
                .matchingPathGlob("*__group");

        schemaElement.fieldTemplate(
                "properties_default",
                f -> f.asString().normalizer("lowercase").indexNullAs("_null_")
        );


        context.bridge(Node.class, new Bridge(schemaElement, nodeIdField, typeIdField, ownerField, modifierField, orderNoField, createdField, changedField, parentNodeIdField));
    }


    private static class Bridge implements TypeBridge<Node> {

        private final IndexSchemaElement userMetadataFieldReference;
        private final IndexFieldReference<String> nodeIdField;
        private final IndexFieldReference<String> typeIdField;
        private final IndexFieldReference<String> ownerField;
        private final IndexFieldReference<String> modifierField;
        private final IndexFieldReference<Long> orderNoField;
        private final IndexFieldReference<Instant> createdField;
        private final IndexFieldReference<Instant> changedField;
        private final IndexFieldReference<String> parentNodeId;

        private Bridge(IndexSchemaElement userMetadataFieldReference, IndexFieldReference<String> nodeIdField, IndexFieldReference<String> typeIdField, IndexFieldReference<String> ownerField, IndexFieldReference<String> modifierField, IndexFieldReference<Long> orderNoField, IndexFieldReference<Instant> createdField, IndexFieldReference<Instant> changedField, IndexFieldReference<String> parentNodeIdField) {
            this.userMetadataFieldReference = userMetadataFieldReference;
            this.nodeIdField = nodeIdField;
            this.typeIdField = typeIdField;
            this.ownerField = ownerField;
            this.modifierField = modifierField;
            this.orderNoField = orderNoField;
            this.createdField = createdField;
            this.changedField = changedField;
            this.parentNodeId = parentNodeIdField;
        }

        @Override
        public void write(DocumentElement target, Node node, TypeBridgeWriteContext context) {
            target.addValue(nodeIdField, node.getNodeId());
            target.addValue(typeIdField, node.getTypeId());
            target.addValue(ownerField, node.getOwner());
            target.addValue(modifierField, node.getModifier());
//            target.addValue(orderNoField, node.getOrderNo());
//            target.addValue(createdField, node.getCreated().toInstant());
//            target.addValue(changedField, node.getChanged().toInstant());
//            if (node.getParentNodeId() != null) {
//                target.addValue(parentNodeId, node.getParentNodeId());
//            }
        }
    }
}
