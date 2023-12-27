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
import pe.datasys.dto.TipoOrdenDTO;
import pe.datasys.model.TipoOrdenEntity;
import pe.datasys.service.ITipoOrdenService;

@RestController
@RequestMapping("tipos-ordenes")
@RequiredArgsConstructor
public class TipoOrdenController {

    private final ITipoOrdenService service;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<TipoOrdenDTO>> findAll() {
        List<TipoOrdenDTO> lista = service.findAll().stream().map( this::convertToDto).toList();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoOrdenDTO> findById(@PathVariable("id") Integer id) {
        TipoOrdenEntity obj = service.findById(id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @GetMapping("/usoEstadoAbonado/{usoEstadoAbonado}")
    public ResponseEntity<List<TipoOrdenDTO>> findByUsoEstadoAbonado(@PathVariable("usoEstadoAbonado") String usoEstadoAbonado) {
         List<TipoOrdenDTO> lista = service.findUsoEstadoAbonado(usoEstadoAbonado).stream().map( this::convertToDto).toList();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PostMapping("/paginate")
    public ResponseEntity<Page<TipoOrdenDTO>> paginate(@RequestBody PaginationModel pagination) {
        Page<TipoOrdenDTO> pag = service.paginate(pagination.getPageNumber(), pagination.getRowsPerPage(),
                pagination.getFilters(), pagination.getSorts());
        return new ResponseEntity<>(pag, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TipoOrdenDTO> save(@Valid @RequestBody TipoOrdenDTO dto) {
        TipoOrdenEntity obj = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdTipoOrden()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoOrdenDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody TipoOrdenDTO dto) {
        TipoOrdenEntity obj = service.update(convertToEntity(dto), id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private TipoOrdenDTO convertToDto(TipoOrdenEntity obj){
        return mapper.map(obj, TipoOrdenDTO.class);
    }

    private TipoOrdenEntity convertToEntity(TipoOrdenDTO dto){
        return mapper.map(dto, TipoOrdenEntity.class);
    }
}
