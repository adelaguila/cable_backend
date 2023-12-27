package pe.datasys.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import pe.datasys.model.TerceroDireccionEntity;

import pe.datasys.repo.IGenericRepo;
import pe.datasys.repo.ITerceroDireccionRepo;

import pe.datasys.service.ITerceroDireccionService;

@Service
@RequiredArgsConstructor
public class TerceroDireccionServiceImpl extends CRUDImpl<TerceroDireccionEntity, Long> implements ITerceroDireccionService{

    private final ITerceroDireccionRepo repo;

    @Override
    protected IGenericRepo<TerceroDireccionEntity, Long> getRepo() {
       return repo;
    }

}
