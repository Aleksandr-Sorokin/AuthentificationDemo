package ru.sorokin.model;

import lombok.Data;

@Data
public class BookRequest {
    private String author;
    private String title;
    private Double price;
}
