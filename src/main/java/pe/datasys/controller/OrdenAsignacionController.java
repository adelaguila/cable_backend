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
import pe.datasys.dto.OrdenAsignacionDTO;
import pe.datasys.dto.OrdenAsignacionTablaDTO;
import pe.datasys.model.OrdenAsignacionEntity;
import pe.datasys.service.IOrdenAsignacionService;

@RestController
@RequestMapping("ordenes-asignaciones")
@RequiredArgsConstructor
public class OrdenAsignacionController {

    private final IOrdenAsignacionService service;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<OrdenAsignacionDTO>> findAll() {
        List<OrdenAsignacionDTO> lista = service.findAll().stream().map( this::convertToDto).toList();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenAsignacionDTO> findById(@PathVariable("id") Long id) {
        OrdenAsignacionEntity obj = service.findById(id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @GetMapping("/activa/{idOrden}")
    public ResponseEntity<OrdenAsignacionDTO> findOrdenAsignacionActiva(@PathVariable("idOrden") Long idOrden) {
        OrdenAsignacionEntity obj = service.findOrdenAsignacionActiva(idOrden);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @PostMapping("/paginate")
    public ResponseEntity<Page<OrdenAsignacionTablaDTO>> paginate(@RequestBody PaginationModel pagination) {
        Page<OrdenAsignacionTablaDTO> pag = service.paginate(pagination.getPageNumber(), pagination.getRowsPerPage(),
                pagination.getFilters(), pagination.getSorts());
        return new ResponseEntity<>(pag, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrdenAsignacionDTO> save(@Valid @RequestBody OrdenAsignacionDTO dto) {
        OrdenAsignacionEntity obj = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdOrdenAsignacion()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdenAsignacionDTO> update(@Valid @PathVariable("id") Long id, @RequestBody OrdenAsignacionDTO dto) {
        OrdenAsignacionEntity obj = service.update(convertToEntity(dto), id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private OrdenAsignacionDTO convertToDto(OrdenAsignacionEntity obj){
        return mapper.map(obj, OrdenAsignacionDTO.class);
    }

    private OrdenAsignacionEntity convertToEntity(OrdenAsignacionDTO dto){
        return mapper.map(dto, OrdenAsignacionEntity.class);
    }
}
