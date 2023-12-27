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
import pe.datasys.dto.MonedaDTO;
import pe.datasys.model.MonedaEntity;
import pe.datasys.service.IMonedaService;

@RestController
@RequestMapping("monedas")
@RequiredArgsConstructor
public class MonedaController {

    private final IMonedaService service;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<MonedaDTO>> findAll() {
        List<MonedaDTO> lista = service.findAll().stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MonedaDTO> findById(@PathVariable("id") String id) {
        MonedaEntity obj = service.findById(id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @PostMapping("/paginate")
    public ResponseEntity<Page<MonedaDTO>> ubigeos(@RequestBody PaginationModel pagination) {
        Page<MonedaDTO> pag = service.paginate(pagination.getPageNumber(), pagination.getRowsPerPage(),
                pagination.getFilters(), pagination.getSorts());
        return new ResponseEntity<>(pag, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MonedaDTO> save(@Valid @RequestBody MonedaDTO dto) {
        MonedaEntity obj = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getCodigo())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MonedaDTO> update(@Valid @PathVariable("id") String id, @RequestBody MonedaDTO dto) {
        MonedaEntity obj = service.update(convertToEntity(dto), id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private MonedaDTO convertToDto(MonedaEntity obj) {
        return mapper.map(obj, MonedaDTO.class);
    }

    private MonedaEntity convertToEntity(MonedaDTO dto) {
        return mapper.map(dto, MonedaEntity.class);
    }
}
