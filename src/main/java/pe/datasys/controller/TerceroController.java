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
import pe.datasys.dto.TerceroDTO;
import pe.datasys.dto.TerceroDireccionDTO;
import pe.datasys.model.TerceroDireccionEntity;
import pe.datasys.model.TerceroEntity;
import pe.datasys.service.ITerceroDireccionService;
import pe.datasys.service.ITerceroService;

@RestController
@RequestMapping("terceros")
@RequiredArgsConstructor
public class TerceroController {

    private final ITerceroService service;
    private final ITerceroDireccionService serviceDireccion;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<TerceroDTO>> findAll() {
        List<TerceroDTO> lista = service.findAll().stream().map( this::convertToDto).toList();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TerceroDTO> findById(@PathVariable("id") Long id) {
        TerceroEntity obj = service.findById(id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @GetMapping("/checkDniruc/{dniruc}")
    public ResponseEntity<?> checkDniruc(@PathVariable("dniruc") String dniruc) {
        TerceroEntity obj = service.checkDniruc(dniruc);
        TerceroDTO objDto = null;
        if(obj != null) {
            objDto = mapper.map(obj, TerceroDTO.class);
        }

        return new ResponseEntity<>(objDto, HttpStatus.OK);
    }

    @PostMapping("/paginate")
    public ResponseEntity<Page<TerceroDTO>> paginate(@RequestBody PaginationModel pagination) {
        Page<TerceroDTO> pag = service.paginate(pagination.getPageNumber(), pagination.getRowsPerPage(),
                pagination.getFilters(), pagination.getSorts());
        return new ResponseEntity<>(pag, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TerceroDTO> save(@Valid @RequestBody TerceroDTO dto) {
        TerceroEntity obj = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdTercero()).toUri();
        return ResponseEntity.created(location).body(convertToDto(obj));
    }

    @GetMapping("/autocomplete/{term}")
    public ResponseEntity<List<TerceroDTO>> autocomplete(@PathVariable("term") String term) {
         List<TerceroDTO> lista = service.autocomplete(term).stream().map( this::convertToDto).toList();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TerceroDTO> update(@Valid @PathVariable("id") Long id, @RequestBody TerceroDTO dto) {
        TerceroEntity obj = service.update(convertToEntity(dto), id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/direccion/{id}")
    public ResponseEntity<TerceroDireccionDTO> updateDireccion(@Valid @PathVariable("id") Long id, @RequestBody TerceroDireccionDTO dto) {
        TerceroDireccionEntity obj = serviceDireccion.update(convertDireccionToEntity(dto), id);
        return new ResponseEntity<>(convertDireccionToDto(obj), HttpStatus.OK);
    }

    @DeleteMapping("/direccion/{idTerceroDireccion}")
    public ResponseEntity<Void> deleteDireccion(@PathVariable("idTerceroDireccion") Long idTerceroDireccion) {
        System.out.println("Para Borrar " + idTerceroDireccion);
        serviceDireccion.delete(idTerceroDireccion);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private TerceroDTO convertToDto(TerceroEntity obj){
        return mapper.map(obj, TerceroDTO.class);
    }

    private TerceroEntity convertToEntity(TerceroDTO dto){
        return mapper.map(dto, TerceroEntity.class);
    }

    private TerceroDireccionDTO convertDireccionToDto(TerceroDireccionEntity obj){
        return mapper.map(obj, TerceroDireccionDTO.class);
    }

    private TerceroDireccionEntity convertDireccionToEntity(TerceroDireccionDTO dto){
        return mapper.map(dto, TerceroDireccionEntity.class);
    }
}
