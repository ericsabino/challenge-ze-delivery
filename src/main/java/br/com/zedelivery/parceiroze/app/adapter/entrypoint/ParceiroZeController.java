package br.com.zedelivery.parceiroze.app.adapter.entrypoint;

import br.com.zedelivery.parceiroze.app.adapter.entrypoint.dto.ParceiroZeDto;
import br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper.CoordenadaClienteMapper;
import br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper.ParceiroZeMapper;
import br.com.zedelivery.parceiroze.core.usecase.ParceiroZePorCoordenadaUsecase;
import br.com.zedelivery.parceiroze.core.usecase.ParceiroZePorIdUsecase;
import br.com.zedelivery.parceiroze.core.usecase.ParceiroZeUsecase;
import br.com.zedelivery.parceiroze.core.usecase.model.ParceiroZe;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.util.Arrays;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/ze/v1")
@AllArgsConstructor
public class ParceiroZeController {

    public static final String LONGITUDE_NOT_NULL = "Longitude não pode ser nulo";
    public static final String LATITUDE_NOT_NULL = "Latitude não pode ser nulo";
    public static final String IDENTIFICADOR_NOT_NULL = "Identidicador do cliente deve ser informado";
    private ParceiroZeMapper parceiroZeMapper;
    private CoordenadaClienteMapper coordenadaClienteMapper;
    private ParceiroZePorIdUsecase parceiroZePorIdUsecase;
    private ParceiroZeUsecase parceiroZeUsecase;
    private ParceiroZePorCoordenadaUsecase parceiroZePorCoordenadaUsecase;

    @PostMapping(value = "/parceiros")
    public ResponseEntity cadastrarParceiro(@RequestBody @Valid ParceiroZeDto parceiroZeDto) {

        ParceiroZe parceiroZe = parceiroZeMapper.parceiroZeDtoToParceiroZeModel(parceiroZeDto);
        parceiroZeUsecase.cadastrar(parceiroZe);
        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping(value = "/parceiros/{identificador}")
    public ResponseEntity buscarParceiroPorId(@PathVariable("identificador")
                                              @Valid @NotNull(message = IDENTIFICADOR_NOT_NULL)
                                              String identificador) {
        var parceiroZe = parceiroZePorIdUsecase.buscarParceiroPorId(identificador);

        return ResponseEntity.ok().body(parceiroZeMapper.parceiroZeModelToParceiroZeDto(Arrays.asList(parceiroZe)).get(0));
    }

    @GetMapping(value = "/parceiros")
    public ResponseEntity buscarParceiro(@RequestParam @Valid @NotNull(message = LONGITUDE_NOT_NULL) Double longitude,
                                         @RequestParam @Valid @NotNull(message = LATITUDE_NOT_NULL) Double latitude) {

        var coordenadaCliente = coordenadaClienteMapper.coordenadaClienteDtoToCoordenadaClienteModel(longitude, latitude);
        var parceiroZe = parceiroZePorCoordenadaUsecase.buscarParceirosProximoPorCoordenadas(coordenadaCliente);

        return ResponseEntity.ok().body(parceiroZeMapper.parceiroZeModelToParceiroZeDto(parceiroZe));
    }

}
