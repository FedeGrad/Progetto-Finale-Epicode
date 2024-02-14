package it.progetto.energy.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "operazione")
@Getter
@Setter
@MappedSuperclass
public abstract class OperazioneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "active")
    private boolean active;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OperazioneEntity that)) return false;
        return active == that.active && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, active);
    }

    @Override
    public String toString() {
        return "OperazioneEntity{" +
                "id=" + id +
                ", active=" + active +
                '}';
    }

}
