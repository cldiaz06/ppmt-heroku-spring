package com.cldiaz.springreact.projmanagetool.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonFormat;
@Entity
public class ProjectTask {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(updatable=false)
	private String projSequence;
	@NotBlank(message="Please include a project summary")
	private String summary;
	private String acceptCriteria;
	private String status;
	private Integer priority;

	@JsonFormat(pattern = "yyyy-mm-dd")
	private Date dueDate;
	//ManyToOne with Backlog
	
	@Column(updatable=false)
	private String projectIdentifier;

	@JsonFormat(pattern = "yyyy-mm-dd")
	private Date createDate;

	@JsonFormat(pattern = "yyyy-mm-dd")
	private Date lastModifiedDate;
	
	@ManyToOne(fetch=FetchType.EAGER)//, cascade=CascadeType.REFRESH)
	@JoinColumn(name="backlog_id", updatable=false, nullable=false)
	@JsonIgnore
	private Backlog backlog;
	
	@PrePersist
	protected void onCreate() {
		this.createDate = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.lastModifiedDate = new Date();
	}

	public ProjectTask() {}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjSequence() {
		return projSequence;
	}

	public void setProjSequence(String projSequence) {
		this.projSequence = projSequence;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getAcceptCriteria() {
		return acceptCriteria;
	}

	public void setAcceptCriteria(String acceptCriteria) {
		this.acceptCriteria = acceptCriteria;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	

	public Backlog getBacklog() {
		return backlog;
	}

	public void setBacklog(Backlog backlog) {
		this.backlog = backlog;
	}

	@Override
	public String toString() {
		return "ProjectTask [id=" + id + ", projSequence=" + projSequence + ", summary=" + summary + ", acceptCriteria="
				+ acceptCriteria + ", status=" + status + ", priority=" + priority + ", dueDate=" + dueDate
				+ ", projectIdentifier=" + projectIdentifier + ", createDate=" + createDate + ", lastModifiedDate="
				+ lastModifiedDate + "]";
	}
	
	
	
	
}
