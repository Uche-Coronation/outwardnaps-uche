/**
 * 
 */
package com.cmb.model.datatable;

import java.util.Date;
import java.util.List;

import io.micrometer.core.instrument.search.Search;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author waliu.faleye
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class PagingRequest {
    private int start;
    private int length;
    private int draw;
    private List<Order> order;
    private List<Column> columns;
    private Search search;
}
