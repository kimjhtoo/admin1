package com.tuflex.admin.app.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.threeten.bp.LocalDate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import com.tuflex.admin.app.user.model.ERole;
import com.tuflex.admin.app.user.model.Payment;
import com.tuflex.admin.app.user.payload.dto.Criteria;
import com.tuflex.admin.app.user.payload.dto.PageMakerDTO;
import com.tuflex.admin.app.user.payload.dto.UserSimpleDto;
import com.tuflex.admin.app.user.repository.PaymentRepository;
import com.tuflex.admin.app.user.repository.UserRepository;
import com.tuflex.admin.app.user.service.UserService;
import com.tuflex.admin.tool.Utils;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/manage")
@Log4j2
public class StatisticController {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PaymentRepository paymentRepository;

    @GetMapping(value = "/statistics")
    public String manageUser(Model model) {
        ERole role = Utils.getRole();
        long userCount = userRepository.count();
        List<Payment> payments = paymentRepository.findAll();
        List<String> dates = new ArrayList();
        for (Payment payment : payments) {
            if (!dates.contains(payment.getRegDt().toString().substring(0, 10))) {
                dates.add(payment.getRegDt().toString().substring(0, 10));
            }
        }
        int count = payments.size() / dates.size();
        LocalDate now = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1);
        String[] dateList = new String[6];
        int[] countList = new int[6];
        countList[0] = paymentRepository.countByYearMonth(now.getYear(), now.getMonthValue());
        dateList[0] = String.format("%d년%02d월", now.getYear(), now.getMonthValue());
        now = now.minusMonths(1);
        countList[1] = paymentRepository.countByYearMonth(now.getYear(), now.getMonthValue());
        dateList[1] = String.format("%d년%02d월", now.getYear(), now.getMonthValue());
        now = now.minusMonths(1);
        countList[2] = paymentRepository.countByYearMonth(now.getYear(), now.getMonthValue());
        dateList[2] = String.format("%d년%02d월", now.getYear(), now.getMonthValue());
        now = now.minusMonths(1);
        countList[3] = paymentRepository.countByYearMonth(now.getYear(), now.getMonthValue());
        dateList[3] = String.format("%d년%02d월", now.getYear(), now.getMonthValue());
        now = now.minusMonths(1);
        countList[4] = paymentRepository.countByYearMonth(now.getYear(), now.getMonthValue());
        dateList[4] = String.format("%d년%02d월", now.getYear(), now.getMonthValue());
        now = now.minusMonths(1);
        countList[5] = paymentRepository.countByYearMonth(now.getYear(), now.getMonthValue());
        dateList[5] = String.format("%d년%02d월", now.getYear(), now.getMonthValue());

        model.addAttribute("count", count);
        model.addAttribute("userCount", userCount);
        model.addAttribute("dateList", dateList);
        model.addAttribute("countList", countList);
        switch (role) {
            case ROLE_ADMIN:
            case ROLE_MANAGER:
                model.addAttribute("name", Utils.getName());
                model.addAttribute("role", role.equals(ERole.ROLE_ADMIN) ? "총관리자" : "일반 관리자");
                model.addAttribute("pid", Utils.getPid());
                return "admin/manage-statistic";
            default:
                return "redirect:/user/loginView";
        }
    }
}