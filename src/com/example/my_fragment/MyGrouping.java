package com.example.my_fragment;

public class MyGrouping {

	private Long id;
    private Long sourceId;
    private Long targetId;

    public MyGrouping() {
    }

    public MyGrouping(Long id) {
        this.id = id;
    }

    public MyGrouping(Long id, Long sourceId, Long targetId) {
        this.id = id;
        this.sourceId = sourceId;
        this.targetId = targetId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }
	
	
	
}


