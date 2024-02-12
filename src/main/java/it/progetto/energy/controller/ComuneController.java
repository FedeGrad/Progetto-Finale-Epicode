package it.progetto.energy.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.progetto.energy.controller.api.ComuneApi;
import it.progetto.energy.dto.comune.ComuneOutputDTO;
import it.progetto.energy.mapper.dtotodomain.ComuneDTOMapper;
import it.progetto.energy.model.ComuneDomain;
import it.progetto.energy.service.impl.ComuneServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comune")
@Tag(name = "Comune Controller", description = "Gestione Comuni")
@Slf4j
@RequiredArgsConstructor
public class ComuneController implements ComuneApi {

	private final ComuneServiceImpl comuneServiceImpl;
	private final ComuneDTOMapper comuneDTOMapper;

	@Override
	@Deprecated
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<ComuneOutputDTO> findAllComuni() {
		List<ComuneDomain> comuneDomainList = comuneServiceImpl.findAllComuni();
		return comuneDTOMapper.fromComuneDomainListToComuneOutputDTOList(comuneDomainList);
	}

	@Override
	@GetMapping("/page")
	@ResponseStatus(HttpStatus.OK)
	public List<ComuneOutputDTO> findAllComuni(Pageable page) {
		List<ComuneDomain> comuneDomainList = comuneServiceImpl.findAllComuni(page);
		return comuneDTOMapper.fromComuneDomainListToComuneOutputDTOList(comuneDomainList);
	}

}
