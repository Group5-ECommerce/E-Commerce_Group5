package com.christian.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="user_role") // Table name
public class User_role implements Serializable{
	@EmbeddedId	
	User_RolePK id;
}
// Thanks to https://docs.jboss.org/hibernate/orm/4.3/manual/en-US/html_single/#mapping-declaration-id for this guide on EmbeddedIds.
@Embeddable
class User_RolePK implements Serializable{
	private int userId;
	private int roleId;
}