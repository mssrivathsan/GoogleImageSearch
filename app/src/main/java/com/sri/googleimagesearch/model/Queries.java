package com.sri.googleimagesearch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Queries {
    private List<Page> nextPage;
    private List<Page> previousPage;
}
