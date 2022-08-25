package com.example.corestudy.node;

import ch.qos.logback.classic.Logger;
import org.hibernate.search.mapper.pojo.bridge.mapping.annotation.TypeBinderRef;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.DocumentId;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.TypeBinding;
import org.slf4j.LoggerFactory;

import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by jaeho on 2017. 3. 31..
 */

@Indexed
@TypeBinding(binder = @TypeBinderRef(type = NodeTypeBinder.class))
public class Node implements Serializable, Cloneable {
    private static final long serialVersionUID = -8080180774568744031L;
    private static final Logger logger = (Logger) LoggerFactory.getLogger(Node.class);

    @Id
    @DocumentId
    private String nodeId;

    private String typeId;

    private String owner;

    private String modifier;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }
}

