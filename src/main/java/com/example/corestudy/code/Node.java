package com.example.corestudy.code;

import org.hibernate.search.annotations.Indexed;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

@Indexed
public interface Node extends Map<String, Object>, Serializable, Cloneable {
    String ID = "id";
    String NODEID = "nodeId";
    String TYPEID = "typeId";
    String USERID = "userId";
    String ANONYMOUS = "anonymous";
    String SYSTEM = "system";
    String TYPE_SEPERATOR = "::";
    String ID_SEPERATOR = ">";
    String OWNER = "owner";
    String MODIFIER = "modifier";
    String CREATED = "created";
    String CHANGED = "changed";
    String ORDERNO = "orderNo";
    String NULLVALUE = "_null_";
    List<String> NODE_VALUE_KEYS = Arrays.asList(ID, TYPEID, OWNER, MODIFIER, CREATED, CHANGED);

    Long getOrderNo();

    String getId();

    void setId(String id);

    String getTypedNodeId();

    String getTypeId();

    void setTypeId(String typeId);

    Date getCreated();

    Date getChanged();

    boolean getBooleanValue(String pid);

    String getStringValue(String pid);

    String getStringBindingValue(String pid);

    List<String> getRefsListValue(String pid);


    Integer getIntValue(String pid);

    Integer getIntValue(String pid, Integer defaultValue);

    Long getLongValue(String pid);

    Double getDoubleValue(String pid);

    BigDecimal getBigDecimalValue(String pid);

    Date getDateValue(String pid);

    Object getIndexValue(String pid);

    boolean isNullValue(String pid);

    boolean isEmptyValue(String pid);

    String getIndexLabel();

    String getStringValue(String pid, Locale locale);

    String getStringValue(String pid, String language);

    Object getLabelValue();

    Node clone();

    Map<String, Object> toMap();

    Node clone(String typeId);

    void setUpdate(String userId, Date changed);

//    Node toDisplay();

//    Node toDisplay(String... skipPids);

    Map<String, Object> toEnableDisplay(String... pids);

    Node toCode();

    Node toStore();

    Node toSync();

    Map<String, Object> toJson(String parentNodeId);

    default Object makeListJson(String parentNodeId, List<Node> nodes) {
        List value = new ArrayList<>();
        for (Node node : nodes) {
            if (node != null && !node.getTypedNodeId().equals(parentNodeId)) {
                value.add(node.toJson(this.getTypedNodeId()));
            }
        }
        return value;
    }

    Object getStoreValue(String pid);

    Object getValue(String pid);

    Object getBindingValue(String pid);

    Object getPremitiveValue(String pid);

    Node getReferenceNode(String pid);

    List<Node> getReferencesList(String pid);

    Node toIndexing();

    String getOwner();

    String getModifier();
    //TODO INFINISPAN
//    @Spatial(spatialMode = SpatialMode.HASH)
//    Coordinates getGeoLocation();

    List<Node> getReferencedList(String pid);

    List<Node> getReferencedList(String pid, String searchText);

    Map<String, Object> getData();

    Map<String, Object> getObjectValue(String pid);

    String getFileUrl(String pid);

    String getReferenceValue(String pid, String refPid);

    Object getLabel();

    Object getImageLabel();

    Map<String, Object> toBindingMap();

    String getParentNodeId();

    void setParentNodeId(String parentNodeId);

    void putAll(Map<String, Object> data, String locale);

}
