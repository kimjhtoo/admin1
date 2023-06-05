package com.tuflex.admin.app.user.payload.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.tuflex.admin.app.user.model.Hotel;
import com.tuflex.admin.app.user.model.Payment;
import com.tuflex.admin.app.user.model.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelSimpleDto {
    private Long pid;
    private String category, name, regDt;
    private double rating;

    static public HotelSimpleDto toDto(Hotel hotel) {
        return new HotelSimpleDto(hotel.getPid(), hotel.getCategory(), hotel.getName(),
                hotel.getRegDt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd hh:mm")), 0);
    }
}