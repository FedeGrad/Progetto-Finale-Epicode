package it.progetto.energy.model;

import lombok.Data;

import javax.persistence.*;

@Entity
//Non persiste l'entità sul DB ma mappa solamente
@MappedSuperclass
@Data
public abstract class Operazione {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean attivo;

}
