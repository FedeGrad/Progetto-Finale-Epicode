package it.progetto.energy.dto.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import it.progetto.energy.model.Tipologia;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUpdateDTO {
	
	static final String DATE_PATTERN = "dd/MM/yyyy";
	static final String DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm:ss";

	@NotNull
	private Long id;

	@Schema(example = "Company name", type = "string")
	private String companyName;

	@Schema(example = "NPI", type = "string")
	private String NPI;

	@Schema(example = "example@example.it", type = "string")
	private String email;

	@Schema(hidden = true)
	@JsonFormat(pattern = DATE_PATTERN)
	private LocalDate dataLastUpdate = LocalDate.now();

	private BigDecimal annualTurnover;

	@Schema(example = "PA / SAS / SPA / SRL", type = "string")
	private Tipologia type;

	@Schema(example = "example@pec.it", type = "string")
	private String pec;

	@Schema(example = "3279999999", type = "string")
	private String companyPhone;

	@Schema(example = "example@example.it", type = "string")
	private String customerEmail;

	@Schema(example = "name", type = "string")
	private String name;

	@Schema(example = "surname", type = "string")
	private String surname;

	private LocalDate dateOfBirth;

	@Schema(example = "3279999999", type = "string")
	private String customerPhone;

	private Long addressOperationalId;

	private Long addressMainId;

	private List<Long> invoiceIdList;

}