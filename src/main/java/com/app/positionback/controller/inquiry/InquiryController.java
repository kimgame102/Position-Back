package com.app.positionback.controller.inquiry;

import com.app.positionback.domain.inquiry.InquiryDTO;
import com.app.positionback.service.inquiry.InquiryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class InquiryController {
    private InquiryService inquiryService;

    @GetMapping("customer-service-center/inquiry")
    public void goToInquiryForm(InquiryDTO inquiryDTO) {;}
};
