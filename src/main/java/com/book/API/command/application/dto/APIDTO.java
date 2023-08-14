package com.book.API.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class APIDTO {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class BookApiResponse {
        private String version;
        private String logo;
        private String title;
        private String link;
        private String pubDate;
        private int totalResults;
        private int startIndex;
        private int itemsPerPage;
        private String query;
        private int searchCategoryId;
        private String searchCategoryName;
        private List<APIVO.Item> item;

    }
}
