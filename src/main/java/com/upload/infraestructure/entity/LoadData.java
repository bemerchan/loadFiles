package com.upload.infraestructure.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "load_data", schema = "upload")
public class LoadData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private String id;

	@NotNull
	@Schema(description = "Id del sitio")
	@Column(name="site")
	private String site;

	@NotNull
	@Schema(description = "Id de la carga")
	@Column(name="load_id")
	private Long loadId;

	@Schema(description = "Id del resgistro")
	@Column(name = "record_id")
	@NotNull
	private String recordId;

	@Schema(description = "Precio")
	@Column(name = "price")
	private Double price;

	@Schema(description = "Fecha de inicio")
	@Column(name="start_time")
	private LocalDateTime startTime;

	@Schema(description = "Nombre de la categoria")
	@Column(name = "name")
	private String name;

	@Schema(description = "Moneda")
	@Column(name = "description")
	private String description;

	@Schema(description = "Nombre de usuario")
	@Column(name = "nickname")
	private String nickname;

}
