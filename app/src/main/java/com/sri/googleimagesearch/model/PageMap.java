package com.sri.googleimagesearch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageMap {

    private List<CSEThumbnail> cse_thumbnail;
    private List<Metatags> metatags;
    private List<Website> website;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CSEThumbnail {
        private String src;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Website {
        private String image;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Metatags {

        @JsonProperty("og:title")
        private String title;

        @JsonProperty("og:description")
        private String description;

        @JsonProperty("og:image")
        private String imageSrc;
    }

}
