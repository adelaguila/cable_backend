package pe.datasys.service.impl;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import pe.datasys.commons.Filter;
import pe.datasys.commons.SortModel;
import pe.datasys.dto.LiquidacionDTO;
import pe.datasys.model.AbonadoEntity;
import pe.datasys.model.CargoEntity;
import pe.datasys.model.LiquidacionEntity;
import pe.datasys.model.TipoCargoEntity;
import pe.datasys.repo.IAbonadoRepo;
import pe.datasys.repo.ICargoRepo;
import pe.datasys.repo.IGenericRepo;
import pe.datasys.repo.ILiquidacionRepo;
import pe.datasys.repo.ITipoCargoRepo;
import pe.datasys.service.ILiquidacionService;

@Service
@RequiredArgsConstructor
public class LiquidacionServiceImpl extends CRUDImpl<LiquidacionEntity, Integer> implements ILiquidacionService {

    private final ILiquidacionRepo liquidacionRepo;
    private final IAbonadoRepo abonadoRepo;
    private final ITipoCargoRepo tipoCargoRepo;
    private final ICargoRepo cargoRepo;
    private final EntityManager entityManager;

    @Override
    protected IGenericRepo<LiquidacionEntity, Integer> getRepo() {
        return liquidacionRepo;
    }

    @Override
    public Page<LiquidacionDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts) {
        String sqlCount = "SELECT count(a) " + getFrom().toString() + getFilters(filters).toString();

        String sqlSelect = getSelect().toString() + getFrom().toString() + getFilters(filters).toString()
                + getOrder(sorts).toString();

        Query queryCount = entityManager.createQuery(sqlCount);
        Query querySelect = entityManager.createQuery(sqlSelect);

        queryCount = this.setParams(filters, queryCount);

        querySelect = this.setParams(filters, querySelect);

        Long total = (long) queryCount.getSingleResult();

        querySelect.setFirstResult((page) * rowPage);
        querySelect.setMaxResults(rowPage);

        @SuppressWarnings("unchecked")
        List<LiquidacionDTO> lista = querySelect.getResultList();

        PageRequest pageable = PageRequest.of(page, rowPage);

        Page<LiquidacionDTO> rtnPage = new PageImpl<>(lista, pageable, total);
        return rtnPage;
    }

    public StringBuilder getSelect() {
        StringBuilder sql = new StringBuilder("SELECT a ");
        return sql;
    }

    public StringBuilder getFrom() {
        StringBuilder sql = new StringBuilder(" FROM LiquidacionEntity a ");
        return sql;
    }

    public StringBuilder getFilters(List<Filter> filters) {
        StringBuilder sql = new StringBuilder("where 1=1 ");

        for (Filter filtro : filters) {

            if (filtro.getField().equals("idLiquidacion")) {
                sql.append(getFiltersColumns("a.idLiquidacion", filtro.getMatchMode(), ":idLiquidacion"));
            }

            if (filtro.getField().equals("periodo")) {
                sql.append(getFiltersColumns("a.periodo", filtro.getMatchMode(), ":periodo"));
            }
        }

        return sql;
    }

    public Query setParams(List<Filter> filters, Query query) {
        for (Filter filtro : filters) {
            if (filtro.getField().equals("idLiquidacion")) {
                query.setParameter("idLiquidacion", filtro.getValue());
            }
            if (filtro.getField().equals("periodo")) {
                query.setParameter("periodo", filtro.getValue());
            }
        }

        return query;
    }

    public StringBuilder getOrder(List<SortModel> sorts) {

        String preSql = "";

        for (SortModel sort : sorts) {
            if (sort.getField().equals("idLiquidacion")) {
                preSql = " ORDER BY a.idLiquidacion " + sort.getSort();
            }
            if (sort.getField().equals("periodo")) {
                preSql = " ORDER BY a.periodo " + sort.getSort();
            }
        }

        StringBuilder sql = new StringBuilder(preSql);

        return sql;
    }

    @Transactional
    @Override
    public LiquidacionEntity saveLiquidacionTransactional(LiquidacionEntity liquidacion) {

        LiquidacionEntity liquidacionNew = liquidacionRepo.save(liquidacion);

        List<AbonadoEntity> abonados = abonadoRepo.listaAbonadosGenerarCargos(liquidacionNew.getFechaCierre());

        TipoCargoEntity tipoCargo = tipoCargoRepo.findById(1).orElse(null);

        List<CargoEntity> cargos = new ArrayList<>();
 
        for (AbonadoEntity abonado : abonados) {
            if (abonado.getFechaUltimaLiquidacion().isBefore(liquidacion.getFechaCierre())) {
     
                long dias = ChronoUnit.DAYS.between(abonado.getFechaUltimaLiquidacion(), liquidacion.getFechaCierre());
                BigDecimal generado = new BigDecimal(0);
                BigDecimal precio = new BigDecimal(0);
                BigDecimal pagado = new BigDecimal(0);
                Integer cantidad = 0;
 
                if (dias >= 28 && dias <= 31) {
                    generado = abonado.getPlan().getPrecioMes();
                    cantidad = 1;
                    precio = abonado.getPlan().getPrecioMes();
                } else {
                    generado = abonado.getPlan().getPrecioDia().multiply(new BigDecimal(dias));
                    cantidad = (int) dias;
                    precio = abonado.getPlan().getPrecioDia();
                }

                CargoEntity cargo = new CargoEntity();
                cargo.setCantidad(cantidad);
                cargo.setFechaEmision(liquidacionNew.getFechaEmision());
                cargo.setFechaInicio(abonado.getFechaUltimaLiquidacion());
                cargo.setFechaFin(liquidacionNew.getFechaCierre());
                cargo.setFechaVencimiento(liquidacionNew.getFechaVencimiento());

                String glosa = abonado.getPlan().getNombrePlan() + " DEL " + abonado.getFechaUltimaLiquidacion().plusDays(1) + " AL " + liquidacionNew.getFechaCierre();

                cargo.setGlosa(glosa);
                cargo.setPeriodo(liquidacionNew.getPeriodo().toString());
                cargo.setPrecio(precio);
                cargo.setTipo(1);
                cargo.setTotal(generado);
                cargo.setAbonado(abonado);
                cargo.setLiquidacion(liquidacionNew);
                cargo.setPlan(abonado.getPlan());
                cargo.setTipoCargo(tipoCargo);
                cargo.setAnio(Integer.parseInt(liquidacionNew.getPeriodo().toString().substring(1, 4)));
                if (abonado.getSaldoFavor() != null && abonado.getSaldoFavor().compareTo(new BigDecimal(0)) == 1) {
                    if (abonado.getSaldoFavor().compareTo(generado) == -1 || abonado.getSaldoFavor().compareTo(generado) == 0) {
                        pagado = abonado.getSaldoFavor();
                    } else {
                        pagado = generado;
                    }
                }
                cargo.setPagado(pagado);

                cargos.add(cargo);
            }

        }

        cargoRepo.saveAll(cargos);

        abonadoRepo.actualizarFechaUltimaLiquidacionGenerarCargos(liquidacionNew.getFechaCierre());

        abonadoRepo.actualizarDeudaSaldoFavor();
        
        return liquidacionNew;
    }
}
