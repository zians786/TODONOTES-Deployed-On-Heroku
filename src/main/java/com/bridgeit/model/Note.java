package com.bridgeit.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="Note")
public class Note {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer noteId;
	
	private String title;
	
	private String description;
	
	private Date createdDate;

	private Date modifiedDate;
	
	private Date reminder;

	private String image;

	@ManyToOne()
    @JoinColumn(name = "userId")
	private User user;
	
	
	@JoinTable(name = "NoteLabel", joinColumns = {@JoinColumn(name = "noteId")}, inverseJoinColumns = {@JoinColumn(name = "labelId")})
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<Label> label;

	@JoinTable(name="SharedNote",joinColumns= {@JoinColumn(name="noteId")},inverseJoinColumns= {@JoinColumn(name="userId")})
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<User> sharedUser;
	
	public Set<User> getSharedUser() {
		return sharedUser;
	}

	public void setSharedUser(Set<User> sharedUser) {
		this.sharedUser = sharedUser;
	}

	public Set<Label> getLabel() {
		return label;
	}

	public void setLabel(Set<Label> label) {
		this.label = label;
	}

	private boolean isArchived;

	private boolean inTrash;

	private String color =null;

	private boolean isPinned=false;

	
	
	
	public User getUser() {
		return user;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getNoteId() {
		return noteId;
	}

	public void setNoteId(Integer noteId) {
		this.noteId = noteId;
	}



	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isArchived() {
		return isArchived;
	}

	public void setArchived(boolean isArchived) {
		this.isArchived = isArchived;
	}

	public boolean isInTrash() {
		return inTrash;
	}

	public void setInTrash(boolean inTrash) {
		this.inTrash = inTrash;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean isPinned() {
		return isPinned;
	}

	public void setPinned(boolean isPinned) {
		this.isPinned = isPinned;
	}

	public Date getReminder() {
		return reminder;
	}

	public void setReminder(Date reminder) {
		this.reminder = reminder;
	}

	

	// @JsonIgnore is required so as to prevent Jackson fasterxml databind to go
	// into loop
	// if not mentioned, program goes into infinite loop and results into stack
	// overflow

//	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "userid")
//	private User user = new User();
//
//	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
//	@ManyToMany
//	@JoinTable(name = "collabUsers", joinColumns = @JoinColumn(name = "noteid"), inverseJoinColumns = @JoinColumn(name = "userid"))
//	private Set<User> collabUsers = new HashSet<>();


	
	
}
