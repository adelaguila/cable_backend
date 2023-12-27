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
import pe.datasys.dto.TipoCargoDTO;
import pe.datasys.model.TipoCargoEntity;
import pe.datasys.service.ITipoCargoService;

@RestController
@RequestMapping("tipos-cargos")
@RequiredArgsConstructor
public class TipoCargoController {

    private final ITipoCargoService service;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<TipoCargoDTO>> findAll() {
        List<TipoCargoDTO> lista = service.findAll().stream().map( this::convertToDto).toList();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoCargoDTO> findById(@PathVariable("id") Integer id) {
        TipoCargoEntity obj = service.findById(id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @PostMapping("/paginate")
    public ResponseEntity<Page<TipoCargoDTO>> paginate(@RequestBody PaginationModel pagination) {
        Page<TipoCargoDTO> pag = service.paginate(pagination.getPageNumber(), pagination.getRowsPerPage(),
                pagination.getFilters(), pagination.getSorts());
        return new ResponseEntity<>(pag, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TipoCargoDTO> save(@Valid @RequestBody TipoCargoDTO dto) {
        TipoCargoEntity obj = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdTipoCargo()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoCargoDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody TipoCargoDTO dto) {
        TipoCargoEntity obj = service.update(convertToEntity(dto), id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private TipoCargoDTO convertToDto(TipoCargoEntity obj){
        return mapper.map(obj, TipoCargoDTO.class);
    }

    private TipoCargoEntity convertToEntity(TipoCargoDTO dto){
        return mapper.map(dto, TipoCargoEntity.class);
    }
}
