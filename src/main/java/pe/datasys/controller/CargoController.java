package pe.datasys.controller;

import java.net.URI;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import pe.datasys.dto.CargoDTO;
import pe.datasys.model.CargoEntity;
import pe.datasys.service.ICargoService;



@RestController
@RequestMapping("cargos")
@RequiredArgsConstructor
public class CargoController {

    private final ICargoService service;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<CargoDTO>> findAll() {
        List<CargoDTO> lista = service.findAll().stream().map( this::convertToDto).toList();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CargoDTO> findById(@PathVariable("id") Long id) {
        CargoEntity obj = service.findById(id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @GetMapping("/abonado/{idAbonado}")
    public ResponseEntity<List<CargoDTO>> findByAbonadoIdAbonado(@PathVariable("idAbonado") Long idAbonado) {
         List<CargoDTO> lista = service.findByAbonadoIdAbonado(idAbonado).stream().map( this::convertToDto).toList();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CargoDTO> save(@Valid @RequestBody CargoDTO dto) {
        CargoEntity obj = service.saveCargoTransactional(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdCargo()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CargoDTO> update(@Valid @PathVariable("id") Long id, @RequestBody CargoDTO dto) {
        CargoEntity obj = service.update(convertToEntity(dto), id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private CargoDTO convertToDto(CargoEntity obj){
        return mapper.map(obj, CargoDTO.class);
    }

    private CargoEntity convertToEntity(CargoDTO dto){
        return mapper.map(dto, CargoEntity.class);
    }
}
