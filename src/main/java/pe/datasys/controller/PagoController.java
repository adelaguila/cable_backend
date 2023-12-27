package pe.datasys.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.modelmapper.ModelMapper;
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

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import pe.datasys.dto.PagoComprobanteDTO;
import pe.datasys.dto.PagoDTO;
import pe.datasys.model.PagoEntity;
import pe.datasys.service.IPagoService;


@RestController
@RequestMapping("pagos")
@RequiredArgsConstructor
public class PagoController {

    private final IPagoService pagoService;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<PagoDTO>> findAll() {
        List<PagoDTO> lista = pagoService.findAll().stream().map( this::convertToDto).toList();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoDTO> findById(@PathVariable("id") Long id) {
        PagoEntity obj = pagoService.findById(id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @GetMapping("/abonado/{idAbonado}")
    public ResponseEntity<List<PagoDTO>> findByAbonadoIdAbonado(@PathVariable("idAbonado") Long idAbonado) {
         List<PagoDTO> lista = pagoService.findByAbonadoIdAbonado(idAbonado).stream().map( this::convertToDto).toList();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody PagoComprobanteDTO dto) throws IOException {
        PagoEntity pago = this.convertToEntity(dto.getPago());
        PagoEntity pagoNew = pagoService.savePagoComprobanteTransactional(pago, dto.getPagoGeneraComprobante());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pagoNew.getIdPago()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagoDTO> update(@Valid @PathVariable("id") Long id, @RequestBody PagoDTO dto) {
        PagoEntity obj = pagoService.update(convertToEntity(dto), id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        pagoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private PagoDTO convertToDto(PagoEntity obj){
        return mapper.map(obj, PagoDTO.class);
    }

    private PagoEntity convertToEntity(PagoDTO dto){
        return mapper.map(dto, PagoEntity.class);
    }
}
