package it.progetto.energy.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.progetto.energy.controller.api.ComuneApi;
import it.progetto.energy.dto.PageDTO;
import it.progetto.energy.dto.comune.ComuneOutputDTO;
import it.progetto.energy.mapper.UtilsMapper;
import it.progetto.energy.mapper.dtotodomain.ComuneDTOMapper;
import it.progetto.energy.model.ComuneDomain;
import it.progetto.energy.model.PageDomain;
import it.progetto.energy.service.impl.ComuneServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/comune")
@Tag(name = "Comune Controller", description = "Gestione Comuni")
@Slf4j
@RequiredArgsConstructor
public class ComuneController implements ComuneApi {

	private final ComuneServiceImpl comuneServiceImpl;
	private final ComuneDTOMapper comuneDTOMapper;
	private final UtilsMapper utilsMapper;

	@Override
	@Deprecated
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<ComuneOutputDTO> findAllComuni() {
		List<ComuneDomain> comuneDomainList = comuneServiceImpl.findAllComuni();
		return comuneDTOMapper.fromComuneDomainListToComuneOutputDTOList(comuneDomainList);
	}

	@Override
	@PostMapping("/page")
	@ResponseStatus(HttpStatus.OK)
	public List<ComuneOutputDTO> findAllComuniPaged(@Valid @RequestBody PageDTO pageDTO) {
		PageDomain pageDomain = utilsMapper.fromPageDTOToPageDomain(pageDTO);
		List<ComuneDomain> comuneDomainList = comuneServiceImpl.findAllComuni(pageDomain);
		return comuneDTOMapper.fromComuneDomainListToComuneOutputDTOList(comuneDomainList);
	}

}
