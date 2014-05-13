/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.resource;

import be.wolkmaan.klimtoren.party.Party;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author karl
 */
@Table(name="books")
@Entity
@PrimaryKeyJoinColumn(name="resource_id")
public class Book extends Resource {
	@Getter @Setter
	private String title;

	@Getter @Setter
	@ManyToOne
	@JoinColumn(name="author")
	private Party author;

	@Getter @Setter
	@ManyToOne
	@JoinColumn(name="publisher")
	private Party publisher;
}
