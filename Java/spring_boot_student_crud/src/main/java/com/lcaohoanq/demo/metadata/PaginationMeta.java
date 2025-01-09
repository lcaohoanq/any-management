package com.lcaohoanq.demo.metadata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.demo.sort.SortCriterion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaginationMeta {
    @JsonProperty("total_pages")
    private int totalPages;

    @JsonProperty("total_items")
    private long totalItems;

    @JsonProperty("current_page")
    private int currentPage;

    @JsonProperty("page_size")
    private int pageSize;
    
    private String search;
    
    private SortCriterion sort;
}
