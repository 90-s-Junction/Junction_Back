package com.googongs.kids.googonskids.controller;

import com.googongs.kids.googonskids.dto.ReqDto;
import com.googongs.kids.googonskids.service.VideoService;
import lombok.RequiredArgsConstructor;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;

import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@ResponseBody
public class VideoController {

    @Autowired
    VideoService videoService;


    @GetMapping(value = "/getVideo/{startX:.+}/{startY:.+}/{endX:.+}/{endY:.+}/{type}")
    public void stream(
            @PathVariable("startX") Double startX,
            @PathVariable("startY") Double startY,
            @PathVariable("endX") Double endX,
            @PathVariable("endY") Double endY,
            @PathVariable("type") Integer type,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {


        String videoUrl = videoService.searchWay(startX, startY, endX, endY, type);


        videoService.streaming(videoUrl, request, response);

    }
}