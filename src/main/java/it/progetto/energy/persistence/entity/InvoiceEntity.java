package it.progetto.energy.persistence.entity;

import it.progetto.energy.model.StatoFattura;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jdk.jfr.Percentage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "invoice")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(value = AccessLevel.NONE)
	private Long id;

	@Column(name = "year")
	private String year;

	@Column(name = "date")
	private LocalDate date;

	@Column(name = "amount")
	private Double amount;

	@Column(name = "number")
	private Integer number;

	@Column(name = "file")
	private File file;

	@Column(name = "amount_iva")
	private Double amountIVA;

	@Percentage
	@Column(name = "percentage_iva")
	private Double percentageIVA;

	@Column(name = "amount_discount")
	private Double amountDiscount;

	@Percentage
	@Column(name = "percentage_discount")
	private Double percentageDiscount;

	@Column(name = "state")
	@Enumerated(EnumType.STRING)
	private StatoFattura state;

	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name = "id_customer")
	private CustomerEntity customer;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof InvoiceEntity)) return false;
		InvoiceEntity invoiceEntity = (InvoiceEntity) o;
		return Objects.equals(id, invoiceEntity.id) && Objects.equals(year, invoiceEntity.year)
				&& Objects.equals(date, invoiceEntity.date) && Objects.equals(amount, invoiceEntity.amount)
				&& Objects.equals(number, invoiceEntity.number) && Objects.equals(file, invoiceEntity.file)
				&& Objects.equals(amountIVA, invoiceEntity.amountIVA)
				&& Objects.equals(percentageIVA, invoiceEntity.percentageIVA)
				&& Objects.equals(amountDiscount, invoiceEntity.amountDiscount)
				&& Objects.equals(percentageDiscount, invoiceEntity.percentageDiscount)
				&& state == invoiceEntity.state && Objects.equals(customer, invoiceEntity.customer);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, year, date, amount, number, file, amountIVA, percentageIVA, amountDiscount,
				percentageDiscount, state, customer);
	}

}
