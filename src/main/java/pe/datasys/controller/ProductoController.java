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
import pe.datasys.dto.ProductoDTO;
import pe.datasys.model.ProductoEntity;
import pe.datasys.service.IProductoService;

@RestController
@RequestMapping("productos")
@RequiredArgsConstructor
public class ProductoController {

    private final IProductoService service;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> findAll() {
        List<ProductoDTO> lista = service.findAll().stream().map( this::convertToDto).toList();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> findById(@PathVariable("id") Integer id) {
        ProductoEntity obj = service.findById(id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @GetMapping("/autocomplete/{term}")
    public ResponseEntity<List<ProductoDTO>> autocomplete(@PathVariable("term") String term) {
         List<ProductoDTO> lista = service.autocomplete(term).stream().map( this::convertToDto).toList();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PostMapping("/paginate")
    public ResponseEntity<Page<ProductoDTO>> paginate(@RequestBody PaginationModel pagination) {
        Page<ProductoDTO> pag = service.paginate(pagination.getPageNumber(), pagination.getRowsPerPage(),
                pagination.getFilters(), pagination.getSorts());
        return new ResponseEntity<>(pag, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> save(@Valid @RequestBody ProductoDTO dto) {
        ProductoEntity obj = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdProducto()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody ProductoDTO dto) {
        ProductoEntity obj = service.update(convertToEntity(dto), id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private ProductoDTO convertToDto(ProductoEntity obj){
        return mapper.map(obj, ProductoDTO.class);
    }

    private ProductoEntity convertToEntity(ProductoDTO dto){
        return mapper.map(dto, ProductoEntity.class);
    }
}
