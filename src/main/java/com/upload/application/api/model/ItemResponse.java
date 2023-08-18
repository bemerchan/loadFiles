package com.upload.application.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ItemResponse {

    private Integer code;
    private ItemBody body;

    @Getter
    @NoArgsConstructor
    public class ItemBody {
        private String id;
        private double price;
        private LocalDateTime start_time;
        private String category_id;
        private String currency_id;
        private Long seller_id;
    }
}
