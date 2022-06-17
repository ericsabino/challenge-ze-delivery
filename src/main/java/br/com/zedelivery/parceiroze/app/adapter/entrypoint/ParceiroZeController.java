package br.com.zedelivery.parceiroze.app.adapter.entrypoint;

import br.com.zedelivery.parceiroze.app.adapter.entrypoint.dto.ParceiroZeDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ze/v1")
public class ParceiroZeController {

    @PostMapping(value = "/parceiro")
    public void cadastrarParceiro(@RequestBody ParceiroZeDto parceiroZeDto) {

        System.out.println(parceiroZeDto.toString());
    }

}
