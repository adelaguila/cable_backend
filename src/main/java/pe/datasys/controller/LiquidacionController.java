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
import pe.datasys.dto.LiquidacionDTO;
import pe.datasys.model.LiquidacionEntity;
import pe.datasys.service.ILiquidacionService;


@RestController
@RequestMapping("liquidaciones")
@RequiredArgsConstructor
public class LiquidacionController {

    private final ILiquidacionService service;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<LiquidacionDTO>> findAll() {
        List<LiquidacionDTO> lista = service.findAll().stream().map( this::convertToDto).toList();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LiquidacionDTO> findById(@PathVariable("id") Integer id) {
        LiquidacionEntity obj = service.findById(id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @PostMapping("/paginate")
    public ResponseEntity<Page<LiquidacionDTO>> paginate(@RequestBody PaginationModel pagination) {
        Page<LiquidacionDTO> pag = service.paginate(pagination.getPageNumber(), pagination.getRowsPerPage(),
                pagination.getFilters(), pagination.getSorts());
        return new ResponseEntity<>(pag, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LiquidacionDTO> save(@Valid @RequestBody LiquidacionDTO dto) {
        LiquidacionEntity obj = service.saveLiquidacionTransactional(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdLiquidacion()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<LiquidacionDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody LiquidacionDTO dto) {
        LiquidacionEntity obj = service.update(convertToEntity(dto), id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private LiquidacionDTO convertToDto(LiquidacionEntity obj){
        return mapper.map(obj, LiquidacionDTO.class);
    }

    private LiquidacionEntity convertToEntity(LiquidacionDTO dto){
        return mapper.map(dto, LiquidacionEntity.class);
    }
}
