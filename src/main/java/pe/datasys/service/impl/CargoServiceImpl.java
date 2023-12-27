package pe.datasys.service.impl;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import pe.datasys.model.CargoEntity;
import pe.datasys.repo.IAbonadoRepo;
import pe.datasys.repo.ICargoRepo;
import pe.datasys.repo.IGenericRepo;
import pe.datasys.service.ICargoService;

@Service
@RequiredArgsConstructor
public class CargoServiceImpl extends CRUDImpl<CargoEntity, Long> implements ICargoService {

    private final ICargoRepo cargoRepo;
    private final IAbonadoRepo abonadoRepo;

    @Override
    protected IGenericRepo<CargoEntity, Long> getRepo() {
        return cargoRepo;
    }

    @Override
    public List<CargoEntity> findByAbonadoIdAbonado(Long idAbonado) {
        return cargoRepo.findByAbonadoIdAbonado(idAbonado);
    }

    @Transactional
    @Override
    public CargoEntity saveCargoTransactional(CargoEntity cargo) {
        CargoEntity cargoNew = new CargoEntity();
        // AbonadoEntity abonado =
        // abonadoRepo.findById(cargo.getAbonado().getIdAbonado()).orElse(null);
        BigDecimal generado = new BigDecimal(0);
        BigDecimal precio = new BigDecimal(0);
        BigDecimal pagado = new BigDecimal(0);
        Integer cantidad = 0;
        if (cargo.getTipoCargo().getIdTipoCargo() == 1) { // SI ES MENSUALIDAD
            if (cargo.getAbonado().getFechaUltimaLiquidacion().isBefore(cargo.getFechaFin())) {

                long dias = ChronoUnit.DAYS.between(cargo.getAbonado().getFechaUltimaLiquidacion(),
                        cargo.getFechaFin());

                if (dias >= 28 && dias <= 31) {
                    generado = cargo.getAbonado().getPlan().getPrecioMes();
                    cantidad = 1;
                    precio = cargo.getAbonado().getPlan().getPrecioMes();
                } else {
                    generado = cargo.getAbonado().getPlan().getPrecioDia().multiply(new BigDecimal(dias));
                    cantidad = (int) dias;
                    precio = cargo.getAbonado().getPlan().getPrecioDia();
                }

                cargoNew.setCantidad(cantidad);
                cargoNew.setFechaEmision(cargo.getFechaEmision());
                cargoNew.setFechaInicio(cargo.getAbonado().getFechaUltimaLiquidacion().plusDays(1));
                cargoNew.setFechaFin(cargo.getFechaFin());
                cargoNew.setFechaVencimiento(cargo.getFechaVencimiento());

                String glosa = cargo.getAbonado().getPlan().getNombrePlan() + " DEL "
                        + cargo.getAbonado().getFechaUltimaLiquidacion().plusDays(1) + " AL " + cargo.getFechaFin();

                cargoNew.setGlosa(glosa);
                cargoNew.setPeriodo(cargo.getPeriodo().toString());
                cargoNew.setPrecio(precio);
                cargoNew.setTipo(1);
                cargoNew.setTotal(generado);
                cargoNew.setAbonado(cargo.getAbonado());
                // cargoNew.setLiquidacion(null);
                cargoNew.setPlan(cargo.getAbonado().getPlan());
                cargoNew.setTipoCargo(cargo.getTipoCargo());
                cargoNew.setAnio(cargo.getAnio());
                if (cargo.getAbonado().getSaldoFavor() != null
                        && cargo.getAbonado().getSaldoFavor().compareTo(new BigDecimal(0)) == 1) {
                    if (cargo.getAbonado().getSaldoFavor().compareTo(generado) == -1
                            || cargo.getAbonado().getSaldoFavor().compareTo(generado) == 0) {
                        pagado = cargo.getAbonado().getSaldoFavor();
                    } else {
                        pagado = generado;
                    }
                }
                cargoNew.setPagado(pagado);

                abonadoRepo.actualizarFechaUltimaLiquidacionGenerarCargos(cargoNew.getFechaFin());

                cargoRepo.save(cargoNew);
                abonadoRepo.actualizarDeudaSaldoFavorIdAbonado(cargoNew.getAbonado().getIdAbonado());

            } else {
                return null;
            }
        } else {
            cargoNew.setCantidad(cargo.getCantidad());
            cargoNew.setFechaEmision(cargo.getFechaEmision());
            cargoNew.setFechaInicio(cargo.getFechaEmision());
            cargoNew.setFechaFin(cargo.getFechaFin());
            cargoNew.setFechaVencimiento(cargo.getFechaVencimiento());
            cargoNew.setGlosa(cargo.getGlosa());
            cargoNew.setPeriodo(cargo.getPeriodo().toString());
            cargoNew.setPrecio(cargo.getPrecio());
            cargoNew.setTipo(1);
            cargoNew.setTotal(cargo.getTotal());
            cargoNew.setAbonado(cargo.getAbonado());
            // cargoNew.setLiquidacion(null);
            // cargoNew.setPlan(abonado.getPlan());
            cargoNew.setTipoCargo(cargo.getTipoCargo());
            cargoNew.setAnio(cargo.getAnio());
            if (cargo.getAbonado().getSaldoFavor() != null
                    && cargo.getAbonado().getSaldoFavor().compareTo(new BigDecimal(0)) == 1) {
                if (cargo.getAbonado().getSaldoFavor().compareTo(generado) == -1
                        || cargo.getAbonado().getSaldoFavor().compareTo(generado) == 0) {
                    pagado = cargo.getAbonado().getSaldoFavor();
                } else {
                    pagado = generado;
                }
            }
            cargoNew.setPagado(pagado);

            cargoRepo.save(cargoNew);
            abonadoRepo.actualizarDeudaSaldoFavorIdAbonado(cargoNew.getAbonado().getIdAbonado());
            System.out.println("aqui se genero");
        }

        return cargoNew;

    }

}
