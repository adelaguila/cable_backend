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
import pe.datasys.dto.PlanDTO;
import pe.datasys.model.PlanEntity;
import pe.datasys.service.IPlanService;

@RestController
@RequestMapping("planes")
@RequiredArgsConstructor
public class PlanController {

    private final IPlanService service;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<PlanDTO>> findAll() {
        List<PlanDTO> lista = service.findAll().stream().map( this::convertToDto).toList();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanDTO> findById(@PathVariable("id") Integer id) {
        PlanEntity obj = service.findById(id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @PostMapping("/paginate")
    public ResponseEntity<Page<PlanDTO>> ubigeos(@RequestBody PaginationModel pagination) {
        Page<PlanDTO> pag = service.paginate(pagination.getPageNumber(), pagination.getRowsPerPage(),
                pagination.getFilters(), pagination.getSorts());
        return new ResponseEntity<>(pag, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PlanDTO> save(@Valid @RequestBody PlanDTO dto) {
        PlanEntity obj = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdPlan()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody PlanDTO dto) {
        PlanEntity obj = service.update(convertToEntity(dto), id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private PlanDTO convertToDto(PlanEntity obj){
        return mapper.map(obj, PlanDTO.class);
    }

    private PlanEntity convertToEntity(PlanDTO dto){
        return mapper.map(dto, PlanEntity.class);
    }
}
