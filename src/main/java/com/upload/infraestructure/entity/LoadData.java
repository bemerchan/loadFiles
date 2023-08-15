package com.upload.infraestructure.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "load_data", schema = "upload")
public class LoadData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	@Schema(description = "Id del resgistro")
	@Column(name = "record_id")
	@NotNull
	private String record_id;

	@Schema(description = "Precio")
	@Column(name = "price")
	private Double price;

	@Schema(description = "Fecha de inicio")
	@Column(name="start_time")
	@NotNull
	private Date startTime;

}
