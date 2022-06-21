package br.com.zedelivery.parceiroze.app.adapter.entrypoint;

import br.com.zedelivery.parceiroze.app.adapter.entrypoint.dto.ParceiroZeDto;
import br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper.ParceiroZeMapper;
import br.com.zedelivery.parceiroze.core.usecase.model.ParceiroZe;
import org.elasticsearch.geometry.utils.Geohash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/ze/v1")
public class ParceiroZeController {

    //@Autowired
    private final ParceiroZeMapper parceiroZeMapper;

    public ParceiroZeController(ParceiroZeMapper parceiroZeMapper) {
        this.parceiroZeMapper = parceiroZeMapper;
    }

    @PostMapping(value = "/parceiro")
    public ResponseEntity cadastrarParceiro(@RequestBody ParceiroZeDto parceiroZeDto) {

        ParceiroZe parceiroZe = parceiroZeMapper.parceiroZeDtoToParceiroZeModel(parceiroZeDto);

        return ResponseEntity.status(CREATED).body(parceiroZeMapper.parceiroZeModelToParceiroZeDto(parceiroZe));
    }

    @GetMapping(value = "/parceiro")
    public ResponseEntity buscarParceiro(@RequestParam Double longitude, @RequestParam Double latitude) {
        String geohash = Geohash.stringEncode(longitude, latitude, 7);

        Collection<? extends CharSequence> geoHashNeighbors = Geohash.getNeighbors(geohash);
        geoHashNeighbors.forEach(s->System.out.println(String.format("Localização Vizinho: %s", s)));

        return ResponseEntity.ok().body(geoHashNeighbors);
    }

}
