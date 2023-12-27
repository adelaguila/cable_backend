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
import pe.datasys.dto.CuentaBancariaDTO;
import pe.datasys.model.CuentaBancariaEntity;
import pe.datasys.service.ICuentaBancariaService;

@RestController
@RequestMapping("cuentas-bancarias")
@RequiredArgsConstructor
public class CuentaBancariaController {

    private final ICuentaBancariaService service;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<CuentaBancariaDTO>> findAll() {
        List<CuentaBancariaDTO> lista = service.findAll().stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaBancariaDTO> findById(@PathVariable("id") String id) {
        CuentaBancariaEntity obj = service.findById(id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @PostMapping("/paginate")
    public ResponseEntity<Page<CuentaBancariaDTO>> ubigeos(@RequestBody PaginationModel pagination) {
        Page<CuentaBancariaDTO> pag = service.paginate(pagination.getPageNumber(), pagination.getRowsPerPage(),
                pagination.getFilters(), pagination.getSorts());
        return new ResponseEntity<>(pag, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CuentaBancariaDTO> save(@Valid @RequestBody CuentaBancariaDTO dto) {
        CuentaBancariaEntity obj = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getNumeroCuenta())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuentaBancariaDTO> update(@Valid @PathVariable("id") String id, @RequestBody CuentaBancariaDTO dto) {
        CuentaBancariaEntity obj = service.update(convertToEntity(dto), id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private CuentaBancariaDTO convertToDto(CuentaBancariaEntity obj) {
        return mapper.map(obj, CuentaBancariaDTO.class);
    }

    private CuentaBancariaEntity convertToEntity(CuentaBancariaDTO dto) {
        return mapper.map(dto, CuentaBancariaEntity.class);
    }
}
