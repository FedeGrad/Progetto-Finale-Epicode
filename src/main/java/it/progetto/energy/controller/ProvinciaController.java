package it.progetto.energy.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.progetto.energy.controller.api.ProvinciaApi;
import it.progetto.energy.dto.provincia.ProvinciaOutputDTO;
import it.progetto.energy.mapper.dtotodomain.ProvinciaDTOMapper;
import it.progetto.energy.model.ProvinciaDomain;
import it.progetto.energy.service.impl.ProvinciaServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

	private final ProvinciaServiceImpl provinciaServiceImpl;
	private final ProvinciaDTOMapper provinciaDTOMapper;

	@Deprecated
	@Override
	@GetMapping
	public List<ProvinciaOutputDTO> findAllProvince() {
		List<ProvinciaDomain> provinciaDomainList = provinciaServiceImpl.getAllProvince();
		return provinciaDTOMapper.fromProvinciaDomainListToProvinciaOutputDTOList(provinciaDomainList);
	}

	@Override
	@GetMapping("/page")
	public List<ProvinciaOutputDTO> findAllProvince(Pageable page) {
		List<ProvinciaDomain> provinciaDomainList = provinciaServiceImpl.getAllProvince(page);
		return provinciaDTOMapper.fromProvinciaDomainListToProvinciaOutputDTOList(provinciaDomainList);
	}

}
