package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Consumer {
    private long id;
    private String fio;
}
