package pe.datasys.commons;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class PaginationModel {

   
    private Integer pageNumber=0;
    private Integer rowsPerPage=0;
    // private Long totalRows;
    // private Long totalPges;

    private List<Filter> filters = new ArrayList<>();
    private List<SortModel> sorts;

    public PaginationModel(Integer pageNumber, Integer rowsPerPage) {
        this.pageNumber = pageNumber;
        this.rowsPerPage = rowsPerPage;
    }

    public PaginationModel(Integer pageNumber, Integer rowsPerPage, List<Filter> filters ) {
        this.pageNumber = pageNumber;
        this.rowsPerPage = rowsPerPage;
        this.filters = filters;
    }        

    public PaginationModel(Integer pageNumber, Integer rowsPerPage, Long totalRows) {
        this.pageNumber = pageNumber;
        this.rowsPerPage = rowsPerPage;

    }
}
