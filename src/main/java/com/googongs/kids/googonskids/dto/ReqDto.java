package com.googongs.kids.googonskids.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqDto {
    private Double startX;
    private Double endX;
    private Double startY;
    private Double endY;
    private Integer type;
}
