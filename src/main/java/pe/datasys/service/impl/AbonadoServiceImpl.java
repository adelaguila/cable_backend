package pe.datasys.service.impl;

import java.math.BigDecimal;
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
import pe.datasys.dto.AbonadoDTO;
import pe.datasys.model.AbonadoEntity;
import pe.datasys.model.CargoEntity;
import pe.datasys.model.OrdenEntity;
import pe.datasys.model.PlanEntity;
import pe.datasys.model.TerceroEntity;
import pe.datasys.model.TipoOrdenEntity;
import pe.datasys.repo.IAbonadoRepo;
import pe.datasys.repo.ICargoRepo;
import pe.datasys.repo.IGenericRepo;
import pe.datasys.repo.IOrdenRepo;
import pe.datasys.repo.IPagoRepo;
import pe.datasys.repo.IPlanRepo;
import pe.datasys.repo.ITerceroRepo;
import pe.datasys.repo.ITipoOrdenRepo;
import pe.datasys.service.IAbonadoService;

@Service
@RequiredArgsConstructor
public class AbonadoServiceImpl extends CRUDImpl<AbonadoEntity, Long> implements IAbonadoService {

    private final IAbonadoRepo abonadoRepo;
    private final ITerceroRepo terceroRepo;
    private final ITipoOrdenRepo tipoOrdenRepo;
    private final IOrdenRepo ordenRepo;
    private final IPlanRepo planRepo;
    private final IPagoRepo pagoRepo;
    private final ICargoRepo cargoRepo;

    private final EntityManager entityManager;

    @Override
    protected IGenericRepo<AbonadoEntity, Long> getRepo() {
        return abonadoRepo;
    }

    @Transactional
    @Override
    public AbonadoEntity saveTransactional(TerceroEntity tercero, AbonadoEntity abonado) {

        if (tercero.getIdTercero() != null && tercero.getIdTercero() > 0) {

        } else {
            terceroRepo.save(tercero);
        }

        abonado.setTercero(tercero);
        abonadoRepo.save(abonado);

        TipoOrdenEntity tipoOrden = tipoOrdenRepo.findById(1).orElse(null);
        PlanEntity plan = planRepo.findById(abonado.getPlan().getIdPlan()).orElse(null);

        OrdenEntity ordenNew = new OrdenEntity();

        ordenNew.setAbonado(abonado);
        ordenNew.setTipoOrden(tipoOrden);
        // ordenNew.setFechaRegistro(abonado.getFechaRegistro().toInstant()
        // .atZone(ZoneId.systemDefault())
        // .toLocalDate());
        ordenNew.setFechaRegistro(abonado.getFechaRegistro());
        ordenNew.setDetalle(plan.getNombrePlan());
        ordenNew.setEstado("REGISTRADO");

        ordenRepo.save(ordenNew);

        return abonado;
    }

    @Override
    public Page<AbonadoDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts) {
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
        List<AbonadoDTO> lista = querySelect.getResultList();

        PageRequest pageable = PageRequest.of(page, rowPage);

        Page<AbonadoDTO> rtnPage = new PageImpl<>(lista, pageable, total);
        return rtnPage;
    }

    public StringBuilder getSelect() {
        StringBuilder sql = new StringBuilder(
                "SELECT new pe.datasys.dto.AbonadoTablaDTO(a.idAbonado, a.tercero.dniruc, a.tercero.nombreTercero, a.sector.nombreSector, a.via.nombreVia, a.numero, concat(a.via.tipoVia.idTipoVia, '. ', a.via.nombreVia, ' ', a.numero), concat(a.tercero.telefono1, ' - ', a.tercero.telefono2), a.cajaNap.nombreCajaNap, a.plan.nombrePlan, a.estado, a.deuda, a.saldoFavor) ");
        return sql;
    }

    public StringBuilder getFrom() {
        StringBuilder sql = new StringBuilder(" FROM AbonadoEntity a ");
        return sql;
    }

    public StringBuilder getFilters(List<Filter> filters) {
        StringBuilder sql = new StringBuilder("where 1=1 ");

        for (Filter filtro : filters) {

            if (filtro.getField().equals("idAbonado")) {
                sql.append(getFiltersColumns("a.idAbonado", filtro.getMatchMode(), ":idAbonado"));
            }
            if (filtro.getField().equals("dniruc")) {
                sql.append(getFiltersColumns("a.tercero.dniruc", filtro.getMatchMode(), ":dniruc"));
            }
            if (filtro.getField().equals("nombreTercero")) {
                sql.append(getFiltersColumns("a.tercero.nombreTercero", filtro.getMatchMode(), ":nombreTercero"));
            }
            if (filtro.getField().equals("nombreVia")) {
                sql.append(getFiltersColumns("a.via.nombreVia", filtro.getMatchMode(), ":nombreVia"));
            }
            if (filtro.getField().equals("nombreSector")) {
                sql.append(getFiltersColumns("a.sector.nombreSector", filtro.getMatchMode(), ":nombreSector"));
            }
            if (filtro.getField().equals("nombrePlan")) {
                sql.append(getFiltersColumns("a.plan.nombrePlan", filtro.getMatchMode(), ":nombrePlan"));
            }
            if (filtro.getField().equals("estado")) {
                sql.append(getFiltersColumns("a.estado", filtro.getMatchMode(), ":estado"));
            }
            if (filtro.getField().equals("deuda")) {
                sql.append(getFiltersColumns("a.deuda", filtro.getMatchMode(), ":deuda"));
            }
        }

        return sql;
    }

    public Query setParams(List<Filter> filters, Query query) {
        for (Filter filtro : filters) {
            if (filtro.getField().equals("idAbonado")) {
                query.setParameter("idAbonado", filtro.getValue());
            }
            if (filtro.getField().equals("dniruc")) {
                query.setParameter("dniruc", filtro.getValue());
            }
            if (filtro.getField().equals("nombreTercero")) {
                query.setParameter("nombreTercero", filtro.getValue());
            }
            if (filtro.getField().equals("nombreVia")) {
                query.setParameter("nombreVia", filtro.getValue());
            }
            if (filtro.getField().equals("nombreSector")) {
                query.setParameter("nombreSector", filtro.getValue());
            }
            if (filtro.getField().equals("nombrePlan")) {
                query.setParameter("nombrePlan", filtro.getValue());
            }
            if (filtro.getField().equals("estado")) {
                query.setParameter("estado", filtro.getValue());
            }
            if (filtro.getField().equals("deuda")) {
                query.setParameter("deuda", filtro.getValue());
            }

        }

        return query;
    }

    public StringBuilder getOrder(List<SortModel> sorts) {

        String preSql = "";

        for (SortModel sort : sorts) {
            if (sort.getField().equals("idAbonado")) {
                preSql = " ORDER BY a.idAbonado " + sort.getSort();
            }
            if (sort.getField().equals("dniruc")) {
                preSql = " ORDER BY a.tercero.dniruc " + sort.getSort();
            }
            if (sort.getField().equals("nombreTercero")) {
                preSql = " ORDER BY a.tercero.nombreTercero " + sort.getSort();
            }
            if (sort.getField().equals("nombreVia")) {
                preSql = " ORDER BY a.via.nombreVia " + sort.getSort();
            }
            if (sort.getField().equals("nombreSector")) {
                preSql = " ORDER BY a.sector.nombreSector " + sort.getSort();
            }
            if (sort.getField().equals("nombrePlan")) {
                preSql = " ORDER BY a.plan.nombrePlan " + sort.getSort();
            }
            if (sort.getField().equals("estado")) {
                preSql = " ORDER BY a.estado " + sort.getSort();
            }
            if (sort.getField().equals("deuda")) {
                preSql = " ORDER BY a.deuda " + sort.getSort();
            }

        }

        StringBuilder sql = new StringBuilder(preSql);

        return sql;
    }

    @Transactional
    @Override
    public Integer actualizarCargos() {
        List<Long> abonados = abonadoRepo.listaIdAbonados();
        System.out.println("Total Abonados: " + abonados.size());
        abonados.forEach(a -> {
            System.out.println("Abonado >>>>> " + a);
            // Long a = (long) 24627;
            BigDecimal totalPagos = pagoRepo.totalPagosIdAbonado(a);
            if (totalPagos != null) {

                List<CargoEntity> cargos = cargoRepo.findByAbonadoIdAbonado(a);
                for(CargoEntity cargo: cargos) {

                    BigDecimal pagado = new BigDecimal(0);

                    if (cargo.getTotal().compareTo(totalPagos) == -1 || cargo.getTotal().compareTo(totalPagos) == 0) {
                        pagado = cargo.getTotal();
                    }

                    if (cargo.getTotal().compareTo(totalPagos) == 1) {
                        pagado = totalPagos;
                    }

                    cargoRepo.actualizarPago(pagado, cargo.getIdCargo());

                    totalPagos = totalPagos.subtract(pagado);

                    System.out.println("Saldo >>>>> " + totalPagos);
                    
                    if(totalPagos.compareTo(new BigDecimal(0)) == 0){
                        break;
                    }
                };
            }

        });

        return 1;
    }

}
