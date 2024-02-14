package it.progetto.energy.utils.csvbuilder;

import it.progetto.energy.csv.AddressCSV;
import it.progetto.energy.csv.ComuneCSV;
import it.progetto.energy.csv.ProvinciaCSV;

import static it.progetto.energy.utils.ConstantForTest.LOCATION;
import static it.progetto.energy.utils.ConstantForTest.NAME;
import static it.progetto.energy.utils.ConstantForTest.NUMBER;
import static it.progetto.energy.utils.ConstantForTest.POSTAL_CODE;
import static it.progetto.energy.utils.ConstantForTest.REGION;
import static it.progetto.energy.utils.ConstantForTest.SIGLA_PROVINCIA;
import static it.progetto.energy.utils.ConstantForTest.WAY;

public class buildCsv {

    public static AddressCSV buildAddressCSV(){
        return AddressCSV.builder()
                .way(WAY)
                .number(NUMBER)
                .location(LOCATION)
                .postalCode(POSTAL_CODE)
                .build();
    }

    public static ComuneCSV buildComuneCSV(){
        return ComuneCSV.builder()
                .name(NAME)
                .siglaProvincia(SIGLA_PROVINCIA)
                .postalCode(POSTAL_CODE)
                .build();
    }

    public static ProvinciaCSV buildProvinciaCSV(){
        return ProvinciaCSV.builder()
                .name(NAME)
                .siglaProvincia(SIGLA_PROVINCIA)
                .region(REGION)
                .build();
    }

}
