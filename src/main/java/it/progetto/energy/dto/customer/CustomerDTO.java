package it.progetto.energy.dto.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import it.progetto.energy.model.Tipologia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDTO {
	
	static final String DATE_PATTERN = "dd/MM/yyyy";
	static final String DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm:ss";

	@NotEmpty
	@Schema(example = "company name", type = "string", defaultValue = "new Company")
	private String companyName;

	@NotEmpty
	@Schema(example = "000990000099", type = "string", defaultValue = "000990000099")
	private String NPI;

	@NotEmpty
	@Email
	@Schema(example = "example@example.it", type = "string", defaultValue = "example@example.it")
	private String email;

	@Schema(hidden = true)
	@JsonFormat(pattern = DATE_TIME_PATTERN)
	private LocalDate dataCreate = LocalDate.now();

	@Schema(hidden = true)
	@JsonFormat(pattern = DATE_PATTERN)
	private LocalDate dataLastUpdate = LocalDate.now();

	private BigDecimal annualTurnover;

	@NotEmpty
	@Schema(example = "PA / SAS / SPA / SRL", type = "string", defaultValue = "SRL")
	private Tipologia type;

	@Schema(example = "example@pec.it", type = "string")
	private String pec;

	@NotEmpty
	@Schema(example = "3279999999", type = "string", defaultValue = "3279999999")
	private String companyPhone;

	@Email
	@Schema(example = "example@example.it", type = "string")
	private String customerEmail;

	@NotBlank
	@Schema(example = "name", type = "string", defaultValue = "Name")
	private String name;

	@NotBlank
	@Schema(example = "surname", type = "string", defaultValue = "surname")
	private String surname;

	@Schema(example = "20/01/2000", type = "string")
	@JsonFormat(pattern = DATE_PATTERN)
	private LocalDate dateOfBirth;

	@Schema(example = "3279999999", type = "string")
	private String customerPhone;

	private Long addressOperationalId;

	private Long addressMainId;

	private List<Long> invoiceIdList;

}
