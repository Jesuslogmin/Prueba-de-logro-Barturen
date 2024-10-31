package com.ithamarsirlopu.pruebanivel.rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ithamarsirlopu.pruebanivel.converter.DenunciaConverter;
import com.ithamarsirlopu.pruebanivel.dto.DenunciaDto;
import com.ithamarsirlopu.pruebanivel.entity.Denuncia;
import com.ithamarsirlopu.pruebanivel.service.DenunciaService;
import com.ithamarsirlopu.pruebanivel.util.WrapperResponse;

import java.util.List;
import org.springframework.data.domain.Pageable;




@RestController
@RequestMapping("/v1/denuncias")
public class DenunciaController {
@Autowired
    private DenunciaService service;

    @Autowired
    private DenunciaConverter converter;
    @GetMapping
    public ResponseEntity<List<DenunciaDto>> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "limit", required = false, defaultValue = "5") int pageSize
    ) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        List<DenunciaDto> denuncia = converter.fromEntities(service.findAll());
        return new WrapperResponse(true, "success", denuncia).createResponse(HttpStatus.OK);
    }



    
    @PostMapping
    public ResponseEntity<DenunciaDto> create (@RequestBody DenunciaDto denuncia) {
        Denuncia entity = converter.fromDTO(denuncia);
        DenunciaDto dto = converter.fromEntity(service.save(entity));//        return ResponseEntity.ok(dto);
        return new WrapperResponse(true, "success", dto).createResponse(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DenunciaDto> update (@PathVariable("id") int id, @RequestBody DenunciaDto denuncia) {
        Denuncia entity = converter.fromDTO(denuncia);
        DenunciaDto dto = converter.fromEntity(service.save(entity));
//        return ResponseEntity.ok(dto);
        return new WrapperResponse(true, "success", dto).createResponse(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete (@PathVariable("id") int id) {
        service.delete(id);
//        return ResponseEntity.ok(null);
        return new WrapperResponse(true, "success", null).createResponse(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DenunciaDto> findById (@PathVariable("id") int id) {
        DenunciaDto dto = converter.fromEntity(service.findById(id));

//        return ResponseEntity.ok(dto);
        return new WrapperResponse(true, "success", dto).createResponse(HttpStatus.OK);
    }
}
