package pe.datasys.controller;

import java.net.URI;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
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
import lombok.extern.slf4j.Slf4j;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import pe.datasys.commons.PaginationModel;
import pe.datasys.dto.UbigeoDTO;
import pe.datasys.model.UbigeoEntity;
import pe.datasys.service.IUbigeoService;

@Slf4j
@RestController
@RequestMapping("ubigeos")
@RequiredArgsConstructor
public class UbigeoController {

    private final IUbigeoService service;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<UbigeoDTO>> findAll() {
        List<UbigeoDTO> lista = service.findAll().stream().map( this::convertToDto).toList();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UbigeoDTO> findById(@PathVariable("id") String id) {
        UbigeoEntity obj = service.findById(id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @GetMapping("/autocomplete/{term}")
    public ResponseEntity<List<UbigeoDTO>> autocomplete(@PathVariable("term") String term) {
         List<UbigeoDTO> lista = service.autocomplete(term).stream().map( this::convertToDto).toList();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PostMapping("/paginate")
    public ResponseEntity<Page<UbigeoDTO>> paginate(@RequestBody PaginationModel pagination) {
        log.info("INGRESO A LA PAGINACION ");
        Page<UbigeoDTO> pag = service.paginate(  pagination.getPageNumber(), pagination.getRowsPerPage(), pagination.getFilters(), pagination.getSorts() );
        return new ResponseEntity<>(pag, HttpStatus.OK);
    }   

    @PostMapping
    public ResponseEntity<UbigeoDTO> save(@Valid @RequestBody UbigeoDTO dto) {
        UbigeoEntity obj = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getCodigo()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UbigeoDTO> update(@Valid @PathVariable("id") String id, @RequestBody UbigeoDTO dto) {
        UbigeoEntity obj = service.update(convertToEntity(dto), id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private UbigeoDTO convertToDto(UbigeoEntity obj){
        return mapper.map(obj, UbigeoDTO.class);
    }

    private UbigeoEntity convertToEntity(UbigeoDTO dto){
        return mapper.map(dto, UbigeoEntity.class);
    }
}
