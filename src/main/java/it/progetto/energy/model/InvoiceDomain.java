package it.progetto.energy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceDomain {

    private Long id;

    private String year;

    private LocalDate date;

    private Double amount;

    private Integer number;

    private Double amountIVA;

    private Double percentageIVA;

    private Double amountDiscount;

    private Double percentageDiscount;

    private StatoFattura state;

    private CustomerDomain customer;

    private MultipartFile file;

}
