package com.upload.infraestructure.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="load", schema = "upload")
public class Load {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Schema(description = "Fecha de inicio de la carga")
	@Column(name="start_date")
	@NotNull
	private Date startDate;

	@Schema(description = "Fecha de finalización de la carga")
	@Column(name="end_date")
	@NotNull
	private Date endDate;

	@Schema(description = "Estado de la carga")
	@Column(name="status")
	@NotNull
	private String status;

}
