package it.progetto.energy.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.progetto.energy.controller.api.ProvinciaApi;
import it.progetto.energy.persistence.entity.Provincia;
import it.progetto.energy.service.ProvinciaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/provincia")
@Tag(name = "Provincia Controller", description = "Gestione delle Province")
@Slf4j
@RequiredArgsConstructor
public class ProvinciaController implements ProvinciaApi {

	private final ProvinciaService provServ;

	@Deprecated
	@Override
	@GetMapping
	public List<Provincia> findAllProvince() {
		return provServ.getAllProvince();
	}

	@Override
	@GetMapping("/page")
	public Page<Provincia> findAllProvince(Pageable page) {
		return provServ.getAllProvince(page);
	}

}
