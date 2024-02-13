package it.progetto.energy.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.progetto.energy.model.Tipologia;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Jacksonized
public class CustomerEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(value = AccessLevel.NONE)
	private Long id;

	@Column(name = "company_name")
	private String companyName;

	@Column(name = "npi")
	private String npi;

	@Column(name = "email")
	private String email;

	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;

	@Column(name = "age")
	private int age;

	@Column(name = "data_create")
	@CreationTimestamp
	private LocalDate dataCreate;

	@Column(name = "data_last_update")
	//@Temporal(TemporalType.DATE) solo con la classe Date
	private LocalDate dataLastUpdate;

	@Column(name = "annual_turnover")
	private BigDecimal annualTurnover;

	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private Tipologia type;

	@Column(name = "pec")
	private String pec;

	@Column(name = "company_phone")
	private String companyPhone;

	@Column(name = "customer_email")
	private String customerEmail;

	@Column(name = "name")
	private String name;

	@Column(name = "surname")
	private String surname;

	@Column(name = "customer_phone")
	private String customerPhone;

	// @JsonIgnore
	// @ToStringExclude
	@OneToOne(mappedBy = "customer", cascade = {CascadeType.MERGE, CascadeType.DETACH})
	private AddressEntity address;

	@JsonIgnore
	@ToStringExclude
	@OneToMany(mappedBy = "customer", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REMOVE},
	fetch = FetchType.LAZY)
	private List<InvoiceEntity> invoiceList;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof CustomerEntity)) return false;
		CustomerEntity customerEntity = (CustomerEntity) o;
		return age == customerEntity.age && Objects.equals(id, customerEntity.id)
				&& Objects.equals(companyName, customerEntity.companyName) && Objects.equals(npi, customerEntity.npi)
				&& Objects.equals(email, customerEntity.email) && Objects.equals(dateOfBirth, customerEntity.dateOfBirth)
				&& Objects.equals(dataCreate, customerEntity.dataCreate)
				&& Objects.equals(dataLastUpdate, customerEntity.dataLastUpdate)
				&& Objects.equals(annualTurnover, customerEntity.annualTurnover) && type == customerEntity.type
				&& Objects.equals(pec, customerEntity.pec) && Objects.equals(companyPhone, customerEntity.companyPhone)
				&& Objects.equals(customerEmail, customerEntity.customerEmail) && Objects.equals(name, customerEntity.name)
				&& Objects.equals(surname, customerEntity.surname) && Objects.equals(customerPhone, customerEntity.customerPhone)
				&& Objects.equals(address, customerEntity.address) && Objects.equals(invoiceList, customerEntity.invoiceList);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, companyName, npi, email, dateOfBirth, age, dataCreate, dataLastUpdate,
				annualTurnover, type, pec, companyPhone, customerEmail, name, surname, customerPhone,
				address, invoiceList);
	}

}
