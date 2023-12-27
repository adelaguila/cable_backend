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
import pe.datasys.dto.ComprobanteDTO;
import pe.datasys.model.ComprobanteEntity;
import pe.datasys.service.IComprobanteService;


@RestController
@RequestMapping("comprobantes")
@RequiredArgsConstructor
public class ComprobanteController {

    private final IComprobanteService service;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<ComprobanteDTO>> findAll() {
        List<ComprobanteDTO> lista = service.findAll().stream().map( this::convertToDto).toList();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{serie}")
    public ResponseEntity<ComprobanteDTO> findById(@PathVariable("serie") String serie) {
        ComprobanteEntity obj = service.findById(serie);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @PostMapping("/paginate")
    public ResponseEntity<Page<ComprobanteDTO>> paginate(@RequestBody PaginationModel pagination) {
        Page<ComprobanteDTO> pag = service.paginate(pagination.getPageNumber(), pagination.getRowsPerPage(),
                pagination.getFilters(), pagination.getSorts());
        return new ResponseEntity<>(pag, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ComprobanteDTO> save(@Valid @RequestBody ComprobanteDTO dto) {
        ComprobanteEntity obj = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{serie}").buildAndExpand(obj.getSerie()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{serie}")
    public ResponseEntity<ComprobanteDTO> update(@Valid @PathVariable("serie") String serie, @RequestBody ComprobanteDTO dto) {
        ComprobanteEntity obj = service.update(convertToEntity(dto), serie);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @DeleteMapping("/{serie}")
    public ResponseEntity<Void> delete(@PathVariable("serie") String serie) {
        service.delete(serie);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private ComprobanteDTO convertToDto(ComprobanteEntity obj){
        return mapper.map(obj, ComprobanteDTO.class);
    }

    private ComprobanteEntity convertToEntity(ComprobanteDTO dto){
        return mapper.map(dto, ComprobanteEntity.class);
    }
}
