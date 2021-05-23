package com.googongs.kids.googonskids.service;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Service
public class VideoService {

    public String searchWay(Double startX, Double startY, Double endX, Double endY, Integer type){

        //search process

        String videoUrl = "video/"+ "test" +".mp4";

        return videoUrl;

    }

    public void streaming(String videoUrl, HttpServletRequest request, HttpServletResponse response)
    throws IOException{

        Resource video = new ClassPathResource(videoUrl);

        InputStream inputStream = video.getInputStream();


        File file = File.createTempFile("temp","mp4");
        try{
            FileUtils.copyInputStreamToFile(inputStream, file);
        }finally {
            IOUtils.closeQuietly(inputStream);
        }

        RandomAccessFile randomFile = new RandomAccessFile(file, "r");
        long rangeStart = 0; //요청 범위의 시작 위치
        long rangeEnd = 0; //요청 범위의 끝 위치
        boolean isPart = false; //부분 요청일 경우 true, 전체 요청의 경우 false
        try { //동영상 파일 크기
            long videoSize = randomFile.length(); //스트림 요청 범위, request의 헤더에서 range를 읽는다.
            String range = request.getHeader("range");
            if (range != null) {
                if (range.endsWith("-")) {
                    range = range + (videoSize - 1);
                }
                int idxm = range.trim().indexOf("-");
                rangeStart = Long.parseLong(range.substring(6, idxm));
                rangeEnd = Long.parseLong(range.substring(idxm + 1));
                if (rangeStart > 0) {
                    isPart = true;
                }
            } else {
                rangeStart = 0;
                rangeEnd = videoSize - 1;
            }

            long partSize = rangeEnd - rangeStart + 1;

            response.reset();
            response.setStatus(isPart ? 206 : 200);
            response.setContentType("video/mp4");
            response.setHeader("Content-Range", "bytes " + rangeStart + "-" + rangeEnd + "/" + videoSize);
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
