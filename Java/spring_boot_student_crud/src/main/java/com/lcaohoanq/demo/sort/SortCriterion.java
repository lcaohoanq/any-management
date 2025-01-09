package com.lcaohoanq.demo.sort;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SortCriterion(
    @JsonProperty("sort_by") String sortBy,
    SortOrder order
) { }
