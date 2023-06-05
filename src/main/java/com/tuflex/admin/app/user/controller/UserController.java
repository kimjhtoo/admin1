package com.tuflex.admin.app.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import com.tuflex.admin.app.user.model.ERole;
import com.tuflex.admin.app.user.payload.dto.Criteria;
import com.tuflex.admin.app.user.payload.dto.PageMakerDTO;
import com.tuflex.admin.app.user.payload.dto.UserSimpleDto;
import com.tuflex.admin.app.user.service.UserService;
import com.tuflex.admin.tool.Utils;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/manage")
@Log4j2
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/user")
    public String manageUser(Model model,
            @PageableDefault(size = 15, page = 0, sort = "regDt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(value = "searchType", defaultValue = "") String searchType,
            @RequestParam(value = "searchValue", defaultValue = "") String searchValue) {
        List<UserSimpleDto> list = userService.findAllUsersBySearchOption(searchType, searchValue, pageable);
        long totalCount = userService.getCount(searchType, searchValue);
        System.out.println(totalCount);
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
                return "admin/manage-user";
            default:
                return "redirect:/user/loginView";
        }
    }
}