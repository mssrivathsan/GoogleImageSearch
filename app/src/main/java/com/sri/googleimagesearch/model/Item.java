package com.sri.googleimagesearch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {
    private String title;
    private String link;
    private PageMap pagemap;
}
