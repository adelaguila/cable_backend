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
import pe.datasys.dto.BancoDTO;
import pe.datasys.model.BancoEntity;
import pe.datasys.service.IBancoService;

@RestController
@RequestMapping("bancos")
@RequiredArgsConstructor
public class BancoController {

    private final IBancoService service;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<BancoDTO>> findAll() {
        List<BancoDTO> lista = service.findAll().stream().map( this::convertToDto).toList();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BancoDTO> findById(@PathVariable("id") Integer id) {
        BancoEntity obj = service.findById(id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @PostMapping("/paginate")
    public ResponseEntity<Page<BancoDTO>> ubigeos(@RequestBody PaginationModel pagination) {
        Page<BancoDTO> pag = service.paginate(pagination.getPageNumber(), pagination.getRowsPerPage(),
                pagination.getFilters(), pagination.getSorts());
        return new ResponseEntity<>(pag, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BancoDTO> save(@Valid @RequestBody BancoDTO dto) {
        BancoEntity obj = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdBanco()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BancoDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody BancoDTO dto) {
        BancoEntity obj = service.update(convertToEntity(dto), id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private BancoDTO convertToDto(BancoEntity obj){
        return mapper.map(obj, BancoDTO.class);
    }

    private BancoEntity convertToEntity(BancoDTO dto){
        return mapper.map(dto, BancoEntity.class);
    }
}
