package pe.datasys.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.modelmapper.ModelMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import pe.datasys.commons.Filter;
import pe.datasys.commons.SortModel;
import pe.datasys.dto.ImprimirOrdenDTO;
import pe.datasys.dto.OrdenAsignacionDTO;
import pe.datasys.dto.OrdenDTO;
import pe.datasys.model.AbonadoEntity;
import pe.datasys.model.CargoEntity;
import pe.datasys.model.OrdenAsignacionEntity;
import pe.datasys.model.OrdenEntity;
import pe.datasys.model.TipoCargoEntity;
import pe.datasys.model.User;
import pe.datasys.repo.IAbonadoRepo;
import pe.datasys.repo.ICargoRepo;
import pe.datasys.repo.IGenericRepo;
import pe.datasys.repo.IOntRepo;
import pe.datasys.repo.IOrdenAsignacionOntRepo;
import pe.datasys.repo.IOrdenAsignacionProductoRepo;
import pe.datasys.repo.IOrdenAsignacionRepo;
import pe.datasys.repo.IOrdenRepo;
import pe.datasys.repo.ITipoCargoRepo;
import pe.datasys.service.IOrdenService;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@RequiredArgsConstructor
public class OrdenServiceImpl extends CRUDImpl<OrdenEntity, Long> implements IOrdenService {

    private final IOrdenRepo repo;
    private final IOrdenAsignacionRepo repoOrdenAsignacion;
    private final IOrdenAsignacionProductoRepo repoOrdenAsignacionProducto;
    private final IOrdenAsignacionOntRepo repoOrdenAsignacionOnt;
    private final IOntRepo repoOnt;
    private final IAbonadoRepo repoAbonado;
    private final ICargoRepo repoCargo;
    private final ITipoCargoRepo repoTipoCargo;

    private final EntityManager entityManager;
    private final ModelMapper mapper;

    @Override
    protected IGenericRepo<OrdenEntity, Long> getRepo() {
        return repo;
    }

    @Override
    public Page<OrdenDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts) {
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
        List<OrdenDTO> lista = querySelect.getResultList();

        PageRequest pageable = PageRequest.of(page, rowPage);

        Page<OrdenDTO> rtnPage = new PageImpl<>(lista, pageable, total);
        return rtnPage;
    }

    public StringBuilder getSelect() {
        StringBuilder sql = new StringBuilder(
                "SELECT new pe.datasys.dto.OrdenTablaDTO(a.idOrden, a.fechaRegistro, a.abonado.tercero.nombreTercero, a.tipoOrden.nombreTipoOrden, a.detalle, a.abonado.sector.nombreSector, concat(a.abonado.via.tipoVia.idTipoVia, '. ', a.abonado.via.nombreVia, ' ', a.abonado.numero), a.estado) ");
        return sql;
    }

    public StringBuilder getFrom() {
        StringBuilder sql = new StringBuilder(" FROM OrdenEntity a ");
        return sql;
    }

    public StringBuilder getFilters(List<Filter> filters) {
        StringBuilder sql = new StringBuilder("where 1=1 ");

        for (Filter filtro : filters) {

            if (filtro.getField().equals("idOrden")) {
                sql.append(getFiltersColumns("a.idOrden", filtro.getMatchMode(), ":idOrden"));
            }

            if (filtro.getField().equals("fechaRegistro")) {
                sql.append(getFiltersColumns("a.fechaRegistro", filtro.getMatchMode(), ":fechaRegistro"));
            }

            if (filtro.getField().equals("nombreTercero")) {
                sql.append(
                        getFiltersColumns("a.abonado.tercero.nombreTercero", filtro.getMatchMode(), ":nombreTercero"));
            }

            if (filtro.getField().equals("nombreTipoOrden")) {
                sql.append(getFiltersColumns("a.tipoOrden.nombreTipoOrden", filtro.getMatchMode(), ":nombreTipoOrden"));
            }

            if (filtro.getField().equals("nombreSector")) {
                sql.append(getFiltersColumns("a.abonado.sector.nombreSector", filtro.getMatchMode(), ":nombreSector"));
            }

            if (filtro.getField().equals("estado")) {
                sql.append(getFiltersColumns("a.estado", filtro.getMatchMode(), ":estado"));
            }
        }

        return sql;
    }

    public Query setParams(List<Filter> filters, Query query) {
        for (Filter filtro : filters) {
            if (filtro.getField().equals("idOrden")) {
                query.setParameter("idOrden", filtro.getValue());
            }
            if (filtro.getField().equals("fechaRegistro")) {
                query.setParameter("fechaRegistro", filtro.getValue());
            }
            if (filtro.getField().equals("nombreTercero")) {
                query.setParameter("nombreTercero", filtro.getValue());
            }
            if (filtro.getField().equals("nombreTipoOrden")) {
                query.setParameter("nombreTipoOrden", filtro.getValue());
            }
            if (filtro.getField().equals("nombreSector")) {
                query.setParameter("nombreSector", filtro.getValue());
            }
            if (filtro.getField().equals("estado")) {
                query.setParameter("estado", filtro.getValue());
            }
        }

        return query;
    }

    public StringBuilder getOrder(List<SortModel> sorts) {

        String preSql = "";

        for (SortModel sort : sorts) {
            if (sort.getField().equals("idOrden")) {
                preSql = " ORDER BY a.idOrden " + sort.getSort();
            }
            if (sort.getField().equals("fechaRegistro")) {
                preSql = " ORDER BY a.fechaRegistro " + sort.getSort();
            }
            if (sort.getField().equals("nombreTercero")) {
                preSql = " ORDER BY a.abonado.tercero.nombreTercero " + sort.getSort();
            }
            if (sort.getField().equals("nombreTipoOrden")) {
                preSql = " ORDER BY a.tipoOrden.nombreTipoOrden " + sort.getSort();
            }
            if (sort.getField().equals("nombreSector")) {
                preSql = " ORDER BY a.abonado.sector.nombreSector " + sort.getSort();
            }
            if (sort.getField().equals("estado")) {
                preSql = " ORDER BY a.estado " + sort.getSort();
            }
        }

        StringBuilder sql = new StringBuilder(preSql);

        return sql;
    }

    @Override
    public byte[] generateOrdenPDF(Long id) throws Exception {
        byte[] data = null;
        OrdenEntity orden = findById(id);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("idOrden", orden.getIdOrden());
        parameters.put("detalle", orden.getDetalle());
        parameters.put("fechaRegistro", "2023-10-25");
        parameters.put("idAbonado", orden.getAbonado().getIdAbonado());
        parameters.put("nombreTercero", orden.getAbonado().getTercero().getNombreTercero());
        parameters.put("direccion", orden.getAbonado().getVia().getNombreVia().concat(orden.getAbonado().getNumero()));
        parameters.put("referencia", orden.getAbonado().getReferencia());
        parameters.put("nombreTecnico", "Tecnico Provicional");
        parameters.put("fechaAsignacion", "2023-10-25");
        parameters.put("empresaContacto", "TELEFONO: 966120389 - EMAIL: email@gmail.com");
        parameters.put("empresaNombre", "CABLE ALTOMAYO COMUNICACIONES S.A.C.");
        parameters.put("empresaNombre", "JR. INDEPENDENCIA 871 - MOYOBAMBA");

        File file = new ClassPathResource("/reports/ordenservicio_a5.jasper").getFile();
        JasperPrint print = JasperFillManager.fillReport(file.getPath(), parameters,
                new JRBeanCollectionDataSource(findFirst1ByIdOrden(id)));
        data = JasperExportManager.exportReportToPdf(print);

        return data;
    }

    @Override
    public List<ImprimirOrdenDTO> findOrdenImprimir(Long id) {
        return repo.findOrdenImprimir(id);
    }

    @Override
    public List<OrdenEntity> findFirst2ByIdOrden(Long id) {
        return repo.findFirst2ByIdOrden(id);
    }

    @Override
    public List<OrdenEntity> findFirst1ByIdOrden(Long id) {
        return repo.findFirst1ByIdOrden(id);
    }

    @Transactional
    @Override
    public OrdenAsignacionEntity registrarOrdenAsignacionTransactional(OrdenAsignacionDTO dto) {
        repo.desactivarOrdenAsignaciones(dto.getOrden().getIdOrden());
        repo.updateOrdenAsignacion(dto.getFechaAsignacion(), dto.getOrden().getIdOrden());
        OrdenAsignacionEntity ordenAsignacion = new OrdenAsignacionEntity();
        OrdenEntity orden = mapper.map(dto.getOrden(), OrdenEntity.class);
        User user = mapper.map(dto.getUser(), User.class);
        ordenAsignacion.setFechaAsignacion(dto.getFechaAsignacion());
        ordenAsignacion.setOrden(orden);
        ordenAsignacion.setUser(user);
        ordenAsignacion.setActivo(1);
        return repoOrdenAsignacion.save(ordenAsignacion);
    }

    @Transactional
    @Override
    public Integer atenderOrdenAsignacionTransactional(OrdenAsignacionDTO dto) {
        repo.atenderOrdenAsignacion(dto.getFechaAtencion(), dto.getOrden().getIdOrden());

        if (dto.getProductosUtilizados().size() > 0) {
            dto.getProductosUtilizados()
                    .forEach(pro -> repoOrdenAsignacionProducto.saveOrdenAsigancionProducto(dto.getIdOrdenAsignacion(),
                            pro.getProducto().getIdProducto(), pro.getCantidad()));
        }
        if (dto.getOnts().size() > 0) {
            dto.getOnts().forEach(e -> {
                repoOrdenAsignacionOnt.saveOrdenAsigancionOnt(dto.getIdOrdenAsignacion(), e.getOnt().getSerie());
                repoOnt.updateEstado("INSTALADO", e.getOnt().getSerie());
            });
        }

        AbonadoEntity abonado = repoAbonado.findById(dto.getOrden().getAbonado().getIdAbonado()).orElse(null);
        LocalDate fechaInicio;
        LocalDate fechaFin;
        long dias = 0;
        BigDecimal total = new BigDecimal(0);

        switch (dto.getOrden().getTipoOrden().getIdTipoOrden()) {
            case 1:
                // System.out.println("Activar el abonado");
                // System.out.println(dto.getOrden().getAbonado().getIdAbonado());

                repoAbonado.activarAbonado(dto.getFechaAtencion(), dto.getOrden().getAbonado().getIdAbonado());
                break;
            case 2:
                System.out.println("reconectar el abonado");
                repoAbonado.activarAbonado(dto.getFechaAtencion(), dto.getOrden().getAbonado().getIdAbonado());
                break;
            case 3: //CORTE TEMPORAL
                // System.out.println("Cortar temporalmente");
                // Calcular Saldo

                if (abonado.getFechaUltimaLiquidacion().equals(dto.getFechaAtencion())) { // FechaUltimaLiquidacion es
                                                                                          // igual que fechaAtencion
                    // no genera cargos
                    // corta y actualizar fechaUltimaLiquidacion
                    repoAbonado.cortarAbonado(dto.getFechaAtencion(), abonado.getIdAbonado());
                }
                if (abonado.getFechaUltimaLiquidacion().isAfter(dto.getFechaAtencion())) { // FechaUltimaLiquidacion es
                                                                                           // mayor que fechaAtencion
                    // genera cargo negativo por la diferencia de dias entre fechaAtencion y
                    // fechaUltimaLiquidacion
                    fechaInicio = dto.getFechaAtencion();
                    fechaFin = abonado.getFechaUltimaLiquidacion();
                    dias = DAYS.between(fechaInicio, fechaFin);
                    total = abonado.getPlan().getPrecioDia().multiply(new BigDecimal(dias))
                            .multiply(new BigDecimal(-1));
                    
                    generarCargo(abonado, fechaInicio, fechaFin, dias, total);

                    //CORTA abonado y actualiza la fechaUltimaLiquidacion = fechaAtencion
                    repoAbonado.cortarAbonado(fechaInicio, abonado.getIdAbonado());

                }
                if (abonado.getFechaUltimaLiquidacion().isBefore(dto.getFechaAtencion())) { // FechaUltimaLiquidacion es
                                                                                            // Menor que fechaAtencion
                    // genera cargo por los dias entre fechaUltimaLiquidacion y fechaAtencion
                    // dias = DAYS.between(abonado.getFechaUltimaLiquidacion(), dto.getFechaAtencion());

                    fechaInicio = abonado.getFechaUltimaLiquidacion();
                    fechaFin = dto.getFechaAtencion();
                    dias = DAYS.between(fechaInicio, fechaFin);
                    total = abonado.getPlan().getPrecioDia().multiply(new BigDecimal(dias));
                    
                    generarCargo(abonado, fechaInicio, fechaFin, dias, total);

                    // actualiza la fechaUltimaLiquidacion = fechaAtencion
                    repoAbonado.cortarAbonado(fechaInicio, abonado.getIdAbonado());
                }

                break;
            case 4:  //CORTE POR MORA
                
                if (abonado.getFechaUltimaLiquidacion().equals(dto.getFechaAtencion())) { // FechaUltimaLiquidacion es
                    repoAbonado.cortarAbonado(dto.getFechaAtencion(), abonado.getIdAbonado());
                }

                if (abonado.getFechaUltimaLiquidacion().isAfter(dto.getFechaAtencion())) { // FechaUltimaLiquidacion es
                    fechaInicio = dto.getFechaAtencion();
                    fechaFin = abonado.getFechaUltimaLiquidacion();
                    dias = DAYS.between(fechaInicio, fechaFin);
                    total = abonado.getPlan().getPrecioDia().multiply(new BigDecimal(dias))
                            .multiply(new BigDecimal(-1));
                    
                    generarCargo(abonado, fechaInicio, fechaFin, dias, total);

                    repoAbonado.cortarAbonado(fechaInicio, abonado.getIdAbonado());

                }
                if (abonado.getFechaUltimaLiquidacion().isBefore(dto.getFechaAtencion())) { // FechaUltimaLiquidacion es                   
                    fechaInicio = abonado.getFechaUltimaLiquidacion();
                    fechaFin = dto.getFechaAtencion();
                    dias = DAYS.between(fechaInicio, fechaFin);
                    total = abonado.getPlan().getPrecioDia().multiply(new BigDecimal(dias));
                    
                    generarCargo(abonado, fechaInicio, fechaFin, dias, total);

                    repoAbonado.cortarAbonado(fechaInicio, abonado.getIdAbonado());
                }
                break;
            case 5: //CORTE DEFINITIVO
                if (abonado.getFechaUltimaLiquidacion().equals(dto.getFechaAtencion())) { // FechaUltimaLiquidacion es
                    repoAbonado.cortarAbonado(dto.getFechaAtencion(), abonado.getIdAbonado());
                }
                if (abonado.getFechaUltimaLiquidacion().isAfter(dto.getFechaAtencion())) { // FechaUltimaLiquidacion es
                    fechaInicio = dto.getFechaAtencion();
                    fechaFin = abonado.getFechaUltimaLiquidacion();
                    dias = DAYS.between(fechaInicio, fechaFin);
                    total = abonado.getPlan().getPrecioDia().multiply(new BigDecimal(dias))
                            .multiply(new BigDecimal(-1));
                    
                    generarCargo(abonado, fechaInicio, fechaFin, dias, total);

                    repoAbonado.cortarAbonado(fechaInicio, abonado.getIdAbonado());

                }
                if (abonado.getFechaUltimaLiquidacion().isBefore(dto.getFechaAtencion())) { // FechaUltimaLiquidacion es
                    fechaInicio = abonado.getFechaUltimaLiquidacion();
                    fechaFin = dto.getFechaAtencion();
                    dias = DAYS.between(fechaInicio, fechaFin);
                    total = abonado.getPlan().getPrecioDia().multiply(new BigDecimal(dias));
                    
                    generarCargo(abonado, fechaInicio, fechaFin, dias, total);

                    repoAbonado.cortarAbonado(fechaInicio, abonado.getIdAbonado());
                }
                break;
            case 6:
                System.out.println("migracion de plan");
                // Calcular Saldo
                break;
            case 7:
                System.out.println("traslado");
                break;
            case 8:
                System.out.println("anexos");
                break;
            case 9:
                System.out.println("mala señal");
                break;
            default:
                System.out.println("otros");
        }

        return repoOrdenAsignacion.atenderOrdenAsignacion(dto.getFechaAtencion(), dto.getReporte(),
                dto.getOrden().getIdOrden(), dto.getUser().getIdUser());
    }

    @Override
    public List<OrdenEntity> findByAbonadoIdAbonado(Long idAbonado) {
        return repo.findByAbonadoIdAbonado(idAbonado);
    }

    public CargoEntity generarCargo(AbonadoEntity abonado, LocalDate fechaInicio, LocalDate fechaFin, long dias,
            BigDecimal total) {
        TipoCargoEntity tipoCargo = repoTipoCargo.findById(1).orElse(null);
        CargoEntity cargo = new CargoEntity();
        cargo.setAbonado(abonado);
        cargo.setTipoCargo(tipoCargo);
        cargo.setFechaEmision(fechaInicio);
        cargo.setFechaInicio(fechaInicio);
        cargo.setFechaFin(fechaFin);
        cargo.setFechaVencimiento(fechaFin);
        cargo.setAnio(fechaInicio.getYear());
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("MM");
        cargo.setPeriodo(fechaInicio.format(formato));
        cargo.setTipo(1);
        cargo.setPlan(abonado.getPlan());
        String glosa = "RECALCULO POR CORTE DEL " + fechaInicio + " AL " + fechaFin;
        cargo.setGlosa(glosa);
        cargo.setCantidad(Math.toIntExact(dias));
        cargo.setPrecio(abonado.getPlan().getPrecioDia());
        cargo.setTotal(total);

        return repoCargo.save(cargo);
    }

}
