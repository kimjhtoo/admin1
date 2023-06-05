package com.tuflex.admin.app.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.threeten.bp.LocalDate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import com.tuflex.admin.app.user.model.ERole;
import com.tuflex.admin.app.user.model.Hotel;
import com.tuflex.admin.app.user.model.Payment;
import com.tuflex.admin.app.user.model.Product;
import com.tuflex.admin.app.user.payload.dto.Criteria;
import com.tuflex.admin.app.user.payload.dto.HotelSimpleDto;
import com.tuflex.admin.app.user.payload.dto.PageMakerDTO;
import com.tuflex.admin.app.user.payload.dto.UserSimpleDto;
import com.tuflex.admin.app.user.service.HotelService;
import com.tuflex.admin.app.user.service.UserService;
import com.tuflex.admin.tool.Utils;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/manage")
@Log4j2
public class HotelController {

    @Autowired
    UserService userService;

    @Autowired
    HotelService hotelService;

    @GetMapping(value = "/hotel")
    public String manageUser(Model model,
            @PageableDefault(size = 15, page = 0, sort = "regDt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(value = "searchType", defaultValue = "") String searchType,
            @RequestParam(value = "searchValue", defaultValue = "") String searchValue) {
        List<HotelSimpleDto> list = hotelService.findHotels(searchType, searchValue, pageable);
        long totalCount = hotelService.getCount(searchType, searchValue);
        PageMakerDTO pageMaker = new PageMakerDTO(new Criteria(pageable.getPageNumber() + 1, 15l), totalCount);
        model.addAttribute("list", list);
        model.addAttribute("pageMaker", pageMaker);
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchValue", searchValue);
        ERole role = Utils.getRole();
        switch (role) {
            case ROLE_ADMIN:
            case ROLE_MANAGER:
                model.addAttribute("name", Utils.getName());
                model.addAttribute("role", role.equals(ERole.ROLE_ADMIN) ? "총관리자" : "일반 관리자");
                model.addAttribute("pid", Utils.getPid());
                return "admin/manage-hotel";
            default:
                return "redirect:/user/loginView";
        }
    }

    @GetMapping(value = "/hotel/add")
    public String addHotel(Model model) {
        // List<PaymentDto> list = paymentService.list(pid, pageable);
        // long totalCount = paymentService.getCount(pid);
        // PageMakerDTO pageMaker = new PageMakerDTO(new
        // Criteria(pageable.getPageNumber() + 1, 15l), totalCount);
        // model.addAttribute("list", list);
        // model.addAttribute("user", userService.findSellerDetail(pid));
        // model.addAttribute("pageMaker", pageMaker);
        // model.addAttribute("userPid", pid);
        ERole role = Utils.getRole();
        switch (role) {
            case ROLE_ADMIN:
            case ROLE_MANAGER:
                model.addAttribute("name", Utils.getName());
                model.addAttribute("role", role.equals(ERole.ROLE_ADMIN) ? "총관리자" : "일반 관리자");
                model.addAttribute("pid", Utils.getPid());
                return "admin/manage-hotel-add";
            default:
                return "redirect:/user/loginView";
        }
    }

    @Transactional
    @GetMapping(value = "/hotel/detail/{pid}")
    public String manageUser(Model model, @PathVariable("pid") Long pid) {
        Hotel hotel = hotelService.findByPid(pid);
        List<Product> products = hotel.getProducts();
        while (products.size() < 3) {
            Product product = new Product();
            product.setIsEnable(false);
            product.setHotel(hotel);
            products.add(hotelService.save(product));
        }
        model.addAttribute("hotel", hotel);
        model.addAttribute("list", products);
        ERole role = Utils.getRole();
        switch (role) {
            case ROLE_ADMIN:
            case ROLE_MANAGER:
                model.addAttribute("name", Utils.getName());
                model.addAttribute("role", role.equals(ERole.ROLE_ADMIN) ? "총관리자" : "일반 관리자");
                model.addAttribute("pid", Utils.getPid());
                return "admin/manage-hotel-detail";
            default:
                return "redirect:/user/loginView";
        }
    }

    @GetMapping(value = "/hotel/room/{pid}")
    public String addRoom(Model model, @PathVariable("pid") Long pid) {
        Product product = hotelService.findProudctByPid(pid);
        model.addAttribute("product", product);
        ERole role = Utils.getRole();
        switch (role) {
            case ROLE_ADMIN:
            case ROLE_MANAGER:
                model.addAttribute("name", Utils.getName());
                model.addAttribute("role", role.equals(ERole.ROLE_ADMIN) ? "총관리자" : "일반 관리자");
                model.addAttribute("pid", Utils.getPid());
                return "admin/manage-hotel-room";
            default:
                return "redirect:/user/loginView";
        }
    }
}