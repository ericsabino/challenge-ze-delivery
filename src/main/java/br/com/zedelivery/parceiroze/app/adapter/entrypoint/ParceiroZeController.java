package br.com.zedelivery.parceiroze.app.adapter.entrypoint;

import br.com.zedelivery.parceiroze.app.adapter.entrypoint.dto.ParceiroZeDto;
import br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper.CoordenadaClienteMapper;
import br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper.ParceiroZeMapper;
import br.com.zedelivery.parceiroze.core.usecase.ParceiroZeUsecase;
import br.com.zedelivery.parceiroze.core.usecase.model.ParceiroZe;
import org.elasticsearch.geometry.utils.Geohash;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/ze/v1")
public class ParceiroZeController {

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

    @GetMapping(value = "/parceiro")
    public ResponseEntity buscarParceiro(@RequestParam @Valid @NotNull(message = "Longitude não pode ser nulo") Double longitude,
                                         @RequestParam @Valid @NotNull(message = "Latitude não pode ser nulo") Double latitude) {

        var coordenadaCliente = coordenadaClienteMapper.coordenadaClienteDtoToCoordenadaClienteModel(longitude, latitude);

        String geohash = Geohash.stringEncode(longitude, latitude, 7);

        Collection<? extends CharSequence> geoHashNeighbors = Geohash.getNeighbors(geohash);
        geoHashNeighbors.forEach(s->System.out.println(String.format("Localização Vizinho: %s", s)));

        return ResponseEntity.ok().body(geoHashNeighbors);
    }

}
