package com.sri.googleimagesearch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResult {

    private String kind;
    private Queries queries;
    private List<Item> items;

}
