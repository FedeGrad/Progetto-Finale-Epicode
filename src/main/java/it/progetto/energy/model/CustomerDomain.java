package it.progetto.energy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class CustomerDomain {

    private Long id;

    private String companyName;

    private String NPI;

    private String email;

    private LocalDate dataCreate;

    private LocalDate dataLastUpdate;

    private BigDecimal annualTurnover;

    private Tipologia type;

    private String pec;

    private String companyPhone;

    private String customerEmail;

    private String name;

    private String surname;

    private LocalDate dateOfBirth;

    private String customerPhone;

    private int anni;

    private AddressDomain addressMain;

    private List<InvoiceDomain> invoiceList;

}
