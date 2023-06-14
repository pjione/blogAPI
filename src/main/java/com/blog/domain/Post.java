package com.blog.domain;

import com.blog.request.PostEdit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;
    private String title;
    @Lob
    private String content;

    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }
    public void edit(PostEdit postEdit){
        /*this.title = title == null ? this.title : title;
        this.content = content == null ? this.content : title;*/
        this.title = postEdit.getTitle();
        this.content = postEdit.getContent();
    }

}
