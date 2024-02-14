package it.progetto.energy.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOutputDTO {

    private Long id;

    private String companyName;

    private String npi;

    private String email;

    private LocalDate dataCreate;

    private LocalDate dataLastUpdate;

    private BigDecimal annualTurnover;

    private String type;

    private String pec;

    private String companyPhone;

    private String customerEmail;

    private String name;

    private String surname;

    private LocalDate dateOfBirth;

    private String customerPhone;

    private Long addressId;

    private List<Long> invoiceIdList;

}
