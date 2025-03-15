package com.fmicodesproj.fmicodesprojuvservice.controller;

import com.fmicodesproj.fmicodesprojuvservice.domain.UvDataResponse;
import com.fmicodesproj.fmicodesprojuvservice.service.UvService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/uv")
@RequiredArgsConstructor
public class UvController {
    private final UvService uvService;

    @GetMapping
    public ResponseEntity<UvDataResponse> getUvData(@RequestParam Double lat, @RequestParam Double lon) {
        return ResponseEntity.ok(uvService.getUvData(lat, lon));
    }
}
