package com.upload.infraestructure.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime startDate;

	@Schema(description = "Fecha de finalización de la carga")
	@Column(name="end_date")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime endDate;

	@Schema(description = "Estado de la carga")
	@Column(name="status")
	@NotNull
	private String status;

	@Schema(description = "Nombre del archivo cargado")
	@Column(name="fileName")
	@NotNull
	private String fileName;

	@Schema(description = "Error generado en la carga")
	@Column(name="error")
	private String error;

}
