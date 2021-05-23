package com.googongs.kids.googonskids.controller;

import com.googongs.kids.googonskids.dto.ReqDto;
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
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private String url = "video/snow.mov";
//    @Autowired
//    ResourceLoader resourceLoader;


    @GetMapping(value = "/test")
    public void test(
                       HttpServletRequest request, HttpServletResponse response) throws IOException {
        Resource video;


        video = new ClassPathResource("video/"+ "test" +".mp4");


        InputStream inputStream = video.getInputStream();

        File file = File.createTempFile("temp","mp4");

        try{
            FileUtils.copyInputStreamToFile(inputStream, file);
        }finally {
            IOUtils.closeQuietly(inputStream);
        }

//        File file = video.getFile();
        RandomAccessFile randomFile = new RandomAccessFile(file, "r");
        long rangeStart = 0; //요청 범위의 시작 위치
        long rangeEnd = 0; //요청 범위의 끝 위치
        boolean isPart = false; //부분 요청일 경우 true, 전체 요청의 경우 false
        try { //동영상 파일 크기
            long movieSize = randomFile.length(); //스트림 요청 범위, request의 헤더에서 range를 읽는다.
            String range = request.getHeader("range");
            if (range != null) {
                if (range.endsWith("-")) {
                    range = range + (movieSize - 1);
                }
                int idxm = range.trim().indexOf("-");
                rangeStart = Long.parseLong(range.substring(6, idxm));
                rangeEnd = Long.parseLong(range.substring(idxm + 1));
                if (rangeStart > 0) {
                    isPart = true;
                }
            } else {
                rangeStart = 0;
                rangeEnd = movieSize - 1;
            }
            long partSize = rangeEnd - rangeStart + 1;
            response.reset();
            response.setStatus(isPart ? 206 : 200);
            response.setContentType("video/mp4");
            response.setHeader("Content-Range", "bytes " + rangeStart + "-" + rangeEnd + "/" + movieSize);
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Content-Length", "" + partSize);
            OutputStream out = response.getOutputStream();
            randomFile.seek(rangeStart);
            int bufferSize = 8 * 1024;
            byte[] buf = new byte[bufferSize];
            do {
                int block = partSize > bufferSize ? bufferSize : (int) partSize;
                int len = randomFile.read(buf, 0, block);
                out.write(buf, 0, len);
                partSize -= block;
            } while (partSize > 0);
        } catch (IOException e) {
        } finally {
            randomFile.close();
        }
    }


    @GetMapping(value = "/getVideo/{startX:.+}/{startY:.+}/{endX:.+}/{endY:.+}/{type}")
    public void stream(
            @PathVariable("startX") Double startX,
            @PathVariable("startY") Double startY,
            @PathVariable("endX") Double endX,
            @PathVariable("endY") Double endY,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        Resource video;


        video = new ClassPathResource("video/"+ "test" +".mp4");

        InputStream inputStream = video.getInputStream();
        File file = File.createTempFile("temp","mp4");
        try{
            FileUtils.copyInputStreamToFile(inputStream, file);
        }finally {
            IOUtils.closeQuietly(inputStream);
        }

//        File file = video.getFile();
        RandomAccessFile randomFile = new RandomAccessFile(file, "r");
        long rangeStart = 0; //요청 범위의 시작 위치
        long rangeEnd = 0; //요청 범위의 끝 위치
        boolean isPart = false; //부분 요청일 경우 true, 전체 요청의 경우 false
        try { //동영상 파일 크기
            long movieSize = randomFile.length(); //스트림 요청 범위, request의 헤더에서 range를 읽는다.
            String range = request.getHeader("range");
            if (range != null) {
                if (range.endsWith("-")) {
                    range = range + (movieSize - 1);
                }
                int idxm = range.trim().indexOf("-");
                rangeStart = Long.parseLong(range.substring(6, idxm));
                rangeEnd = Long.parseLong(range.substring(idxm + 1));
                if (rangeStart > 0) {
                    isPart = true;
                }
            } else {
                rangeStart = 0;
                rangeEnd = movieSize - 1;
            }
            long partSize = rangeEnd - rangeStart + 1;
            response.reset();
            response.setStatus(isPart ? 206 : 200);
            response.setContentType("video/mp4");
            response.setHeader("Content-Range", "bytes " + rangeStart + "-" + rangeEnd + "/" + movieSize);
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Content-Length", "" + partSize);
            OutputStream out = response.getOutputStream();
            randomFile.seek(rangeStart);
            int bufferSize = 8 * 1024;
            byte[] buf = new byte[bufferSize];
            do {
                int block = partSize > bufferSize ? bufferSize : (int) partSize;
                int len = randomFile.read(buf, 0, block);
                out.write(buf, 0, len);
                partSize -= block;
            } while (partSize > 0);
        } catch (IOException e) {
        } finally {
            randomFile.close();
        }
    }
}