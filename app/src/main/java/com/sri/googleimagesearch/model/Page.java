package com.sri.googleimagesearch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Page {
    private String title;
    private int count;
    private int startIndex;

}
