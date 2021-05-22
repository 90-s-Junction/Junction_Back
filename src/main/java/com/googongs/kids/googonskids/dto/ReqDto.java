package com.googongs.kids.googonskids.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqDto {
    private Float startX;
    private Float endX;
    private Float startY;
    private Float endY;
    private Integer type;
}
