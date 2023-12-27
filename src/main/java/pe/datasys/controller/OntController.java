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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import pe.datasys.commons.PaginationModel;
import pe.datasys.dto.OntDTO;
import pe.datasys.model.OntEntity;
import pe.datasys.service.IOntService;


@RestController
@RequestMapping("onts")
@RequiredArgsConstructor
public class OntController {

    private final IOntService service;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<OntDTO>> findAll() {
        List<OntDTO> lista = service.findAll().stream().map( this::convertToDto).toList();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{serie}")
    public ResponseEntity<OntDTO> findById(@PathVariable("serie") String serie) {
        OntEntity obj = service.findById(serie);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<OntDTO>> findByEstado(@PathVariable("estado") String estado) {
         List<OntDTO> lista = service.findByEstado(estado).stream().map( this::convertToDto).toList();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PostMapping("/paginate")
    public ResponseEntity<Page<OntDTO>> paginate(@RequestBody PaginationModel pagination) {
        Page<OntDTO> pag = service.paginate(pagination.getPageNumber(), pagination.getRowsPerPage(),
                pagination.getFilters(), pagination.getSorts());
        return new ResponseEntity<>(pag, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OntDTO> save(@Valid @RequestBody OntDTO dto) {
        OntEntity obj = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{serie}").buildAndExpand(obj.getSerie()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{serie}")
    public ResponseEntity<OntDTO> update(@Valid @PathVariable("serie") String serie, @RequestBody OntDTO dto) {
        OntEntity obj = service.update(convertToEntity(dto), serie);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @DeleteMapping("/{serie}")
    public ResponseEntity<Void> delete(@PathVariable("serie") String serie) {
        service.delete(serie);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private OntDTO convertToDto(OntEntity obj){
        return mapper.map(obj, OntDTO.class);
    }

    private OntEntity convertToEntity(OntDTO dto){
        return mapper.map(dto, OntEntity.class);
    }
}
