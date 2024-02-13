package it.progetto.energy.dto.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import it.progetto.energy.model.Tipologia;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static it.progetto.energy.utils.ConstantUtils.DATE_PATTERN;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUpdateDTO {

	@NotNull
	private Long id;

	@Schema(example = "Company name", type = "string")
	private String companyName;

	@Schema(example = "000990000099", type = "string")
	private String npi;

	@Email
	@Schema(example = "example@example.it", type = "string")
	private String email;

	@Schema(hidden = true)
	@JsonFormat(pattern = DATE_PATTERN)
	private LocalDate dataLastUpdate = LocalDate.now();

	private BigDecimal annualTurnover;

	@Schema(example = "PA / SAS / SPA / SRL", type = "string")
	@Enumerated(EnumType.STRING)
	private Tipologia type;

	@Email
	@Schema(example = "example@pec.it", type = "string")
	private String pec;

	@Schema(example = "3279999999", type = "string")
	private String companyPhone;

	@Email
	@Schema(example = "example@example.it", type = "string")
	private String customerEmail;

	@Schema(example = "name", type = "string")
	private String name;

	@Schema(example = "surname", type = "string")
	private String surname;

	private LocalDate dateOfBirth;

	@Schema(example = "3279999999", type = "string")
	private String customerPhone;

	private Long addressId;

	private List<Long> invoiceIdList;

}
