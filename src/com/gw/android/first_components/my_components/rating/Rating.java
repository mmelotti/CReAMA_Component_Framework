package com.gw.android.first_components.my_components.rating;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table RATING.
 */
public class Rating extends com.gw.android.first_components.my_fragment.ComponentSimpleModel  {

    private Long id;
    private Long targetId;
    private Long serverId;
    private float value;

    public Rating() {
    }

    public Rating(Long id) {
        this.id = id;
    }

    public Rating(Long id, Long targetId, Long serverId, float value) {
        this.id = id;
        this.targetId = targetId;
        this.serverId = serverId;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

}