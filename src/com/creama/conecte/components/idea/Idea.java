package com.creama.conecte.components.idea;

public class Idea extends
		com.gw.android.first_components.my_fragment.ComponentSimpleModel {
	
	private Long id;
	private Long targetId;
	private Long serverId;
	/** Not-null value. */
	private String text;
	private String title;
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	private java.util.Date date;
	
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

	/** Not-null value. */
	public String getText() {
		return text;
	}

	/**
	 * Not-null value; ensure this value is available before it is saved to the
	 * database.
	 */
	public void setText(String text) {
		this.text = text;
	}

	public java.util.Date getDate() {
		return date;
	}

	public void setDate(java.util.Date date) {
		this.date = date;
	}

}
