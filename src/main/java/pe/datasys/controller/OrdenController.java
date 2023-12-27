package pe.datasys.controller;

import java.net.URI;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import pe.datasys.dto.OrdenDTO;
import pe.datasys.model.OrdenAsignacionEntity;
import pe.datasys.model.OrdenEntity;
import pe.datasys.service.IOrdenAsignacionService;
import pe.datasys.service.IOrdenService;

@RestController
@RequestMapping("ordenes")
@RequiredArgsConstructor
public class OrdenController {

    private final IOrdenService service;
    private final IOrdenAsignacionService serviceOrdenAsignacion;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<OrdenDTO>> findAll() {
        List<OrdenDTO> lista = service.findAll().stream().map( this::convertToDto).toList();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/abonado/{idAbonado}")
    public ResponseEntity<List<OrdenDTO>> findByAbonadoIdAbonado(@PathVariable("idAbonado") Long idAbonado) {
         List<OrdenDTO> lista = service.findByAbonadoIdAbonado(idAbonado).stream().map( this::convertToDto).toList();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenDTO> findById(@PathVariable("id") Long id) {
        OrdenEntity obj = service.findById(id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @GetMapping(value= "/imprimirOrden/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> imprimirOrden(@PathVariable("id") Long id) throws Exception {
        byte[] data = service.generateOrdenPDF(id);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("/paginate")
    public ResponseEntity<Page<OrdenDTO>> ubigeos(@RequestBody PaginationModel pagination) {
        Page<OrdenDTO> pag = service.paginate(pagination.getPageNumber(), pagination.getRowsPerPage(),
                pagination.getFilters(), pagination.getSorts());
        return new ResponseEntity<>(pag, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<OrdenDTO> save(@Valid @RequestBody OrdenDTO dto) {
        OrdenEntity obj = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdOrden()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/asignacion")
    public ResponseEntity<OrdenAsignacionDTO> asignacion(@Valid @RequestBody OrdenAsignacionDTO dto) {
        OrdenAsignacionEntity obj = service.registrarOrdenAsignacionTransactional(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdOrdenAsignacion()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/atencion")
    public ResponseEntity<OrdenAsignacionDTO> atencion(@Valid @RequestBody OrdenAsignacionDTO dto) {
        service.atenderOrdenAsignacionTransactional(dto);
        OrdenAsignacionEntity obj = serviceOrdenAsignacion.findById(dto.getIdOrdenAsignacion());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdOrdenAsignacion()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdenDTO> update(@Valid @PathVariable("id") Long id, @RequestBody OrdenDTO dto) {
        OrdenEntity obj = service.update(convertToEntity(dto), id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private OrdenDTO convertToDto(OrdenEntity obj){
        return mapper.map(obj, OrdenDTO.class);
    }

    private OrdenEntity convertToEntity(OrdenDTO dto){
        return mapper.map(dto, OrdenEntity.class);
    }
}
