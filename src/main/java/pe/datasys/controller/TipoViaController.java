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
import pe.datasys.dto.TipoViaDTO;
import pe.datasys.model.TipoViaEntity;
import pe.datasys.service.ITipoViaService;

@RestController
@RequestMapping("tipos-vias")
@RequiredArgsConstructor
public class TipoViaController {

    private final ITipoViaService service;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<TipoViaDTO>> findAll() {
        List<TipoViaDTO> lista = service.findAll().stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoViaDTO> findById(@PathVariable("id") String id) {
        TipoViaEntity obj = service.findById(id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @PostMapping("/paginate")
    public ResponseEntity<Page<TipoViaDTO>> paginate(@RequestBody PaginationModel pagination) {
        Page<TipoViaDTO> pag = service.paginate(pagination.getPageNumber(), pagination.getRowsPerPage(),
                pagination.getFilters(), pagination.getSorts());
        return new ResponseEntity<>(pag, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TipoViaDTO> save(@Valid @RequestBody TipoViaDTO dto) {
        TipoViaEntity obj = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdTipoVia())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoViaDTO> update(@Valid @PathVariable("id") String id, @RequestBody TipoViaDTO dto) {
        TipoViaEntity obj = service.update(convertToEntity(dto), id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private TipoViaDTO convertToDto(TipoViaEntity obj) {
        return mapper.map(obj, TipoViaDTO.class);
    }

    private TipoViaEntity convertToEntity(TipoViaDTO dto) {
        return mapper.map(dto, TipoViaEntity.class);
    }
}
