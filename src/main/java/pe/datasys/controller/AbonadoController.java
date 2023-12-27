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
import pe.datasys.dto.AbonadoDTO;
import pe.datasys.dto.TerceroDTO;
import pe.datasys.model.AbonadoEntity;
import pe.datasys.model.TerceroEntity;
import pe.datasys.service.IAbonadoService;

@RestController
@RequestMapping("abonados")
@RequiredArgsConstructor
public class AbonadoController {

    private final IAbonadoService service;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<AbonadoDTO>> findAll() {
        List<AbonadoDTO> lista = service.findAll().stream().map( this::convertToDto).toList();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbonadoDTO> findById(@PathVariable("id") Long id) {
        AbonadoEntity obj = service.findById(id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @PostMapping("/paginate")
    public ResponseEntity<Page<AbonadoDTO>> ubigeos(@RequestBody PaginationModel pagination) {
        Page<AbonadoDTO> pag = service.paginate(pagination.getPageNumber(), pagination.getRowsPerPage(),
                pagination.getFilters(), pagination.getSorts());
        return new ResponseEntity<>(pag, HttpStatus.OK);
    }

    @PostMapping("/actualizarCargos")
    public ResponseEntity<?> actualizarCargos() {
        Integer resp = service.actualizarCargos();
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AbonadoDTO> save(@Valid @RequestBody AbonadoDTO dto) {
        
        TerceroEntity tercero = this.convertToEntityTercero(dto.getTercero());
        AbonadoEntity abonado = this.convertToEntityAbonado(dto);
        
        AbonadoEntity abonadoNew = service.saveTransactional(tercero, abonado);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(abonadoNew.getIdAbonado()).toUri();
        return ResponseEntity.created(location).body(convertToDto(abonadoNew));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AbonadoDTO> update(@Valid @PathVariable("id") Long id, @RequestBody AbonadoDTO dto) {
        AbonadoEntity obj = service.update(convertToEntityAbonado(dto), id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private AbonadoDTO convertToDto(AbonadoEntity obj){
        return mapper.map(obj, AbonadoDTO.class);
    }

    private AbonadoEntity convertToEntityAbonado(AbonadoDTO dto){
        return mapper.map(dto, AbonadoEntity.class);
    }

    private TerceroEntity convertToEntityTercero(TerceroDTO dto){
        return mapper.map(dto, TerceroEntity.class);
    }
}
