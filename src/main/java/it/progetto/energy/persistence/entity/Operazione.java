package it.progetto.energy.persistence.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Entity
//Non persiste l'entità sul DB ma mappa solamente
@MappedSuperclass
@Data
public abstract class Operazione {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean attivo;

}
