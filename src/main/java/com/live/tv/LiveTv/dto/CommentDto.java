package com.live.tv.LiveTv.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CommentDto {
    @NotBlank(message = "Comment не может быть пустым!")
    @Size(min = 3, max = 255, message = "Вы вышли за пределы дозволенной длины комментария. Дозволенная длина коммента = [3,255]")
    private String body;
}
