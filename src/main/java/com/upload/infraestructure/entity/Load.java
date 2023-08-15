package com.upload.infraestructure.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="load", schema = "upload")
public class Load {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
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
