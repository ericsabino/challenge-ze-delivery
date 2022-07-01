package br.com.zedelivery.parceiroze.app.adapter.entrypoint;

import br.com.zedelivery.parceiroze.app.adapter.entrypoint.dto.ParceiroZeDto;
import br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper.CoordenadaClienteMapper;
import br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper.ParceiroZeMapper;
import br.com.zedelivery.parceiroze.core.usecase.ParceiroZeUsecase;
import br.com.zedelivery.parceiroze.core.usecase.model.ParceiroZe;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/ze/v1")
public class ParceiroZeController {

    public static final String LONGITUDE_NOT_NULL = "Longitude não pode ser nulo";
    public static final String LATITUDE_NOT_NULL = "Latitude não pode ser nulo";
    public static final String IDENTIFICADOR_NOT_NULL = "Identidicador do cliente deve ser informado";
    private final ParceiroZeMapper parceiroZeMapper;
    private final CoordenadaClienteMapper coordenadaClienteMapper;
    private final ParceiroZeUsecase parceiroZeUsecase;

    public ParceiroZeController(ParceiroZeMapper parceiroZeMapper, CoordenadaClienteMapper coordenadaClienteMapper, ParceiroZeUsecase parceiroZeUsecase) {
        this.parceiroZeMapper = parceiroZeMapper;
        this.coordenadaClienteMapper = coordenadaClienteMapper;
        this.parceiroZeUsecase = parceiroZeUsecase;
    }

    @PostMapping(value = "/parceiro")
    public ResponseEntity cadastrarParceiro(@RequestBody @Valid ParceiroZeDto parceiroZeDto) {

        ParceiroZe parceiroZe = parceiroZeMapper.parceiroZeDtoToParceiroZeModel(parceiroZeDto);
        parceiroZeUsecase.cadastrarParceiro(parceiroZe);
        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping(value = "/parceiro/{identificador}")
    public ResponseEntity buscarParceiroPorId(@PathVariable("identificador")
                                              @Valid @NotNull(message = IDENTIFICADOR_NOT_NULL)
                                              String identificador) {
        var parceiroZe = parceiroZeUsecase.buscarParceiroPorId(identificador);

        return ResponseEntity.ok().body(parceiroZeMapper.parceiroZeModelToParceiroZeDto(parceiroZe));
    }

    @GetMapping(value = "/parceiro")
    public ResponseEntity buscarParceiro(@RequestParam @Valid @NotNull(message = LONGITUDE_NOT_NULL) Double longitude,
                                         @RequestParam @Valid @NotNull(message = LATITUDE_NOT_NULL) Double latitude) {

        var coordenadaCliente = coordenadaClienteMapper.coordenadaClienteDtoToCoordenadaClienteModel(longitude, latitude);
        var parceiroZe = parceiroZeUsecase.buscarParceirosProximoPorCoordenadas(coordenadaCliente);

        return ResponseEntity.ok().body(parceiroZeMapper.parceiroZeModelToParceiroZeDto(parceiroZe));
    }

}
