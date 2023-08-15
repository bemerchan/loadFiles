package com.upload.infraestructure.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
	@Basic(optional = false)
	@Column(name = "id")
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
