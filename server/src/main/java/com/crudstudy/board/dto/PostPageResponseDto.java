package com.crudstudy.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostPageResponseDto {
    private List<PostListReponseDto> content;
    private int totalPages;
    private int number;
    private boolean first;
    private boolean last;

    public PostPageResponseDto(Page<PostListReponseDto> page) {
        this.content = page.getContent();
        this.totalPages = page.getTotalPages();
        this.number = page.getNumber();
        this.first = page.isFirst();
        this.last = page.isLast();
    }
}
