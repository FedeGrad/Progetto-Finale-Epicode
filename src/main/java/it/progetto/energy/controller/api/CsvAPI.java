package it.progetto.energy.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.progetto.energy.dto.ErrorDTO;
import it.progetto.energy.dto.csv.CsvImportedOutputDTO;
import it.progetto.energy.dto.provincia.ProvinciaDTO;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

public interface CsvAPI {

    //TODO IMPLEMENTS
    @Operation(operationId = "", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "File uploaded",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProvinciaDTO.class))),
            @ApiResponse(responseCode = "400", description = "File not uploaded",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDTO.class))) })
    @Parameter(name = "file", required = true, description = "The file to upload",
            content = @Content(mediaType = "multipart/form-data",
                    schema = @Schema(type = "string", format = "binary")))
    CsvImportedOutputDTO importProvinceAndComuniCSV(MultipartFile province, MultipartFile comuni);

    @Operation(operationId = "", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "File uploaded",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProvinciaDTO.class))),
            @ApiResponse(responseCode = "400", description = "File not uploaded",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDTO.class))) })
    @Parameter(name = "file", required = true, description = "The file to upload",
            content = @Content(mediaType = "multipart/form-data",
                    schema = @Schema(type = "string", format = "binary")))
    CsvImportedOutputDTO importProvinceCSV(MultipartFile province);

    @Operation(operationId = "", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "File uploaded",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProvinciaDTO.class))),
            @ApiResponse(responseCode = "400", description = "File not uploaded",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDTO.class))) })
    @Parameter(name = "file", required = true, description = "The file to upload",
            content = @Content(mediaType = "multipart/form-data",
                    schema = @Schema(type = "string", format = "binary")))
    CsvImportedOutputDTO importComuniCSV(MultipartFile comuni);

}
