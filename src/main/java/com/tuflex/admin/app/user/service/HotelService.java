package com.tuflex.admin.app.user.service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.tuflex.admin.app.user.model.Admin;
import com.tuflex.admin.app.user.model.Hotel;
import com.tuflex.admin.app.user.model.Payment;
import com.tuflex.admin.app.user.model.Product;
import com.tuflex.admin.app.user.payload.dto.HotelSimpleDto;
import com.tuflex.admin.app.user.payload.request.HotelAddRequest;
import com.tuflex.admin.app.user.payload.request.RoomAddRequest;
import com.tuflex.admin.app.user.repository.AdminRepository;
import com.tuflex.admin.app.user.repository.HotelRepository;
import com.tuflex.admin.app.user.repository.ProductRepository;

@RequiredArgsConstructor
@Service
public class HotelService {
    private final UserService userService;

    private final AdminRepository adminRepository;
    private final HotelRepository hotelRepository;
    private final ProductRepository productRepository;

    @Value("${upload.image.location}")
    private String imageFolder;

    @Value("${get.image.location}")
    private String imageUrl;

    @Transactional
    public void add(HotelAddRequest req) throws Exception {
        String url1 = "", uri1 = "";
        String url2 = "", uri2 = "";
        String url3 = "", uri3 = "";
        String url4 = "", uri4 = "";
        String url5 = "", uri5 = "";
        String url6 = "", uri6 = "";
        String url7 = "", uri7 = "";
        String url8 = "", uri8 = "";
        String url9 = "", uri9 = "";
        String url10 = "", uri10 = "";

        String url = "https://dapi.kakao.com/v2/local/search/address.json";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK b53c13dec0f5e14191d73356746da94b");
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        HttpEntity request = new HttpEntity(headers);

        // adding the query params to the URL
        System.out.println(req.getAddress());
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("query", req.getAddress());

        URI endUri = uriBuilder.build().encode().toUri();
        ResponseEntity<String> response = restTemplate.exchange(endUri, HttpMethod.GET, request,
                String.class);
        JSONParser jsonParser = new JSONParser();
        double x = Double.parseDouble(
                (String) ((JSONObject) ((JSONObject) ((JSONArray) ((JSONObject) jsonParser.parse(response.getBody()))
                        .get("documents")).get(0)).get("address")).get("x"));
        double y = Double.parseDouble(
                (String) ((JSONObject) ((JSONObject) ((JSONArray) ((JSONObject) jsonParser.parse(response.getBody()))
                        .get("documents")).get(0)).get("address")).get("y"));
        if (req.getImage1() != null) {
            String imageName = UUID.randomUUID() + "-" + req.getImage1().getOriginalFilename();
            File file = new File(imageFolder, imageName);
            url1 = imageUrl + imageName;
            uri1 = imageFolder + imageName;

            try {
                req.getImage1().transferTo(file);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
        if (req.getImage2() != null) {
            String imageName = UUID.randomUUID() + "-" + req.getImage2().getOriginalFilename();
            File file = new File(imageFolder, imageName);
            url2 = imageUrl + imageName;
            uri2 = imageFolder + imageName;

            try {
                req.getImage2().transferTo(file);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
        if (req.getImage3() != null) {
            String imageName = UUID.randomUUID() + "-" + req.getImage3().getOriginalFilename();
            File file = new File(imageFolder, imageName);
            url3 = imageUrl + imageName;
            uri3 = imageFolder + imageName;

            try {
                req.getImage3().transferTo(file);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
        if (req.getImage4() != null) {
            String imageName = UUID.randomUUID() + "-" + req.getImage4().getOriginalFilename();
            File file = new File(imageFolder, imageName);
            url4 = imageUrl + imageName;
            uri4 = imageFolder + imageName;

            try {
                req.getImage4().transferTo(file);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
        if (req.getImage5() != null) {
            String imageName = UUID.randomUUID() + "-" + req.getImage5().getOriginalFilename();
            File file = new File(imageFolder, imageName);
            url5 = imageUrl + imageName;
            uri5 = imageFolder + imageName;

            try {
                req.getImage5().transferTo(file);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
        if (req.getImage6() != null) {
            String imageName = UUID.randomUUID() + "-" + req.getImage6().getOriginalFilename();
            File file = new File(imageFolder, imageName);
            url6 = imageUrl + imageName;
            uri6 = imageFolder + imageName;

            try {
                req.getImage6().transferTo(file);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
        if (req.getImage7() != null) {
            String imageName = UUID.randomUUID() + "-" + req.getImage7().getOriginalFilename();
            File file = new File(imageFolder, imageName);
            url7 = imageUrl + imageName;
            uri7 = imageFolder + imageName;

            try {
                req.getImage7().transferTo(file);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
        if (req.getImage8() != null) {
            String imageName = UUID.randomUUID() + "-" + req.getImage8().getOriginalFilename();
            File file = new File(imageFolder, imageName);
            url8 = imageUrl + imageName;
            uri8 = imageFolder + imageName;

            try {
                req.getImage8().transferTo(file);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
        if (req.getImage9() != null) {
            String imageName = UUID.randomUUID() + "-" + req.getImage9().getOriginalFilename();
            File file = new File(imageFolder, imageName);
            url9 = imageUrl + imageName;
            uri9 = imageFolder + imageName;

            try {
                req.getImage9().transferTo(file);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
        if (req.getImage10() != null) {
            String imageName = UUID.randomUUID() + "-" + req.getImage10().getOriginalFilename();
            File file = new File(imageFolder, imageName);
            url10 = imageUrl + imageName;
            uri10 = imageFolder + imageName;

            try {
                req.getImage10().transferTo(file);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
        hotelRepository.save(new Hotel(req.getName(), req.getAddress(), req.getIntroduce(), x, y, url1, uri1, url2,
                uri2, url3, uri3, url4, uri4, url5, uri5, url6, uri6, url7, uri7, url8, uri8, url9, uri9, url10,
                uri10));
    }

    @Transactional
    public void addRoom(RoomAddRequest req) throws Exception {
        Product product = findProudctByPid(req.getId());
        String url1 = "", uri1 = "";
        if (req.getImage() != null) {
            String imageName = UUID.randomUUID() + "-" + req.getImage().getOriginalFilename();
            File file = new File(imageFolder, imageName);
            url1 = imageUrl + imageName;
            uri1 = imageFolder + imageName;

            try {
                req.getImage().transferTo(file);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
        product.update(req, url1, uri1);
    }

    @Transactional
    public Hotel findByPid(Long pid) {
        return hotelRepository.findById(pid).orElseThrow();
    }

    @Transactional
    public Product findProudctByPid(Long pid) {
        return productRepository.findById(pid).orElseThrow();
    }

    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }

    // @Transactional
    // public ProductDto findProductDetail(Long pid) {
    // return ProductDto.toDto(findDetailByPid(pid));
    // }

    @Transactional
    public long getCount(String searchType, String searchWord) {
        return hotelRepository.count();
    }

    @Transactional
    public List<HotelSimpleDto> findHotels(String searchType, String searchWord, Pageable pageable) {
        List<Hotel> hotels = hotelRepository.findAll(Sort.by(Sort.Direction.DESC, "regDt"));
        long skip = pageable.getOffset();
        return hotels.stream().filter(h -> h.getName().contains(searchWord)).skip(skip).limit(15).toList().stream()
                .map(h -> HotelSimpleDto.toDto(h)).toList();
    }

    @Transactional
    public void delete(Long hid) {
        Hotel hotel = findByPid(hid);
        hotelRepository.delete(hotel);
    }
}
