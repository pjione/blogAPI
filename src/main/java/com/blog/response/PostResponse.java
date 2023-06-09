package com.blog.response;

import com.blog.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
public class PostResponse {
    private Long id;
    private String title;
    private String content;

    @Builder
    public PostResponse(Long id, String title, String content) {
        this.id = id;
        this.title = title.substring(0,Math.min(title.length(),10));
        this.content = content;
    }
    public PostResponse(Post post){
        this.id = post.getId();
        this.title = post.getTitle().substring(0,Math.min(post.getTitle().length(),10));
        this.content = post.getContent();
    }

}
