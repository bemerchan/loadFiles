package com.upload.infraestructure.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="load_detail", schema = "upload")
public class LoadDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;

	@NotNull
	@Schema(description = "Columna donde se genera el error")
	@Column(name="column")
	private Integer column;

	@NotNull
	@Schema(description = "Fila donde se genera el error")
	@Column(name="row")
	private Integer row;

	@NotNull
	@Schema(description = "Id de la carga")
	@Column(name="load_id")
	private Long loadId;

	@Schema(description = "Error generado en el registro")
	@Column(name="error")
	@NotNull
	private String error;
}
