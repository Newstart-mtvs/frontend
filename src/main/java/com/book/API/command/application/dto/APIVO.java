package com.book.API.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class APIVO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class Item {
        private String title;
        private String author;
        private String cover;
        private String isbn13;

    }
}
