package com.tuflex.admin.app.user.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.web.multipart.MultipartFile;

import com.tuflex.admin.app.common.CommonVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "hotel")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
public class Hotel extends CommonVO {
    @Size(max = 20)
    private String category;

    @NotBlank
    @Size(max = 20)
    private String name;

    @Column(nullable = true)
    private String address;

    private Double latitude, longitude;
    private Integer price;

    @Size(max = 5000)
    private String introduce;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Product> products;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Review> reviews;

    private String url1 = "", uri1 = "";
    private String url2 = "", uri2 = "";
    private String url3 = "", uri3 = "";
    private String url4 = "", uri4 = "";
    private String url5 = "", uri5 = "";
    private String url6 = "", uri6 = "";
    private String url7 = "", uri7 = "";
    private String url8 = "", uri8 = "";
    private String url9 = "", uri9 = "";
    private String url10 = "", uri10 = "";

    @Builder
    public Hotel(String name, String address, String introduce, double longitude, double latitude, String url1,
            String uri1, String url2, String uri2, String url3, String uri3, String url4, String uri4, String url5,
            String uri5, String url6, String uri6, String url7, String uri7, String url8, String uri8, String url9,
            String uri9, String url10, String uri10) {
        this.name = name;
        this.address = address;
        this.introduce = introduce;
        this.latitude = latitude;
        this.longitude = longitude;
        this.url1 = url1;
        this.uri1 = uri1;
        this.url2 = url2;
        this.uri2 = uri2;
        this.url3 = url3;
        this.uri3 = uri3;
        this.url4 = url4;
        this.uri4 = uri4;
        this.url5 = url5;
        this.uri5 = uri5;
        this.url6 = url6;
        this.uri6 = uri6;
        this.url7 = url7;
        this.uri7 = uri7;
        this.url8 = url8;
        this.uri8 = uri8;
        this.url9 = url9;
        this.uri9 = uri9;
        this.url10 = url10;
        this.uri10 = uri10;
        this.price = 0;
    }
}