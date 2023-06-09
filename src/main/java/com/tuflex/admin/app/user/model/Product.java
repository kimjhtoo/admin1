package com.tuflex.admin.app.user.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.tuflex.admin.app.common.CommonVO;
import com.tuflex.admin.app.user.payload.request.RoomAddRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
public class Product extends CommonVO {
    @Size(max = 30)
    private String name;

    @Size(max = 5000)
    private String information;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Boolean status;

    @ColumnDefault("0")
    private Long likeCount, sellCount, reviewCount;

    private int price;

    @ColumnDefault("0.0")
    private Double rate;

    private String imageUrl, imagePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_pid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Hotel hotel;

    public Product(String name, int price, String information, String url, String uri) {
        this.name = name;
        this.price = price;
        this.information = information;
        this.imageUrl = url;
        this.imagePath = uri;
    }

    public void update(RoomAddRequest req, String url, String uri) {
        this.name = req.getName();
        this.price = req.getPrice();
        this.information = req.getInformation();
        this.imageUrl = url;
        this.imagePath = uri;
        setIsEnable(true);
    }

    // public double getRate() {
    // return rate
    // double rate = 0;
    // int reviewCount = 0;
    // for (ProductDetail productDetail : productDetails) {
    // rate += productDetail.getRate();
    // reviewCount += productDetail.getReviewCount();
    // }
    // if (reviewCount == 0) {
    // return 0;
    // } else {
    // return rate / reviewCount;
    // }
    // }
}