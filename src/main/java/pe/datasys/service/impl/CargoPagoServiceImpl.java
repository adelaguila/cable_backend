package pe.datasys.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pe.datasys.model.CargoPagoEntity;
import pe.datasys.repo.ICargoPagoRepo;
import pe.datasys.repo.IGenericRepo;
import pe.datasys.service.ICargoPagoService;

@Service
@RequiredArgsConstructor
public class CargoPagoServiceImpl extends CRUDImpl<CargoPagoEntity, Long> implements ICargoPagoService{

    private final ICargoPagoRepo repo;

    @Override
    protected IGenericRepo<CargoPagoEntity, Long> getRepo() {
       return repo;
    }

}
