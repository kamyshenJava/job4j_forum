package ru.job4j.forum.control;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.forum.Main;
import ru.job4j.forum.model.Comment;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.model.User;
import ru.job4j.forum.repository.CommentRepository;
import ru.job4j.forum.service.PostService;
import ru.job4j.forum.service.UserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class PostControlTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser
    public void shouldReturnCreate() throws Exception {
        this.mockMvc.perform(get("/create"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("post/create"));
    }

    @Test
    @WithMockUser
    public void shouldReturnEdit() throws Exception {
        given(this.postService.findById(1)).willReturn(Optional.of(
                (Post.of("post1", "description1", User.of("user", "password")))));
        this.mockMvc.perform(get("/edit?id=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("post/edit"));
    }

    @Test
    @WithMockUser
    public void shouldReturnPost() throws Exception {
        given(this.postService.findById(1)).willReturn(Optional.of(
                (Post.of("post1", "description1", User.of("user", "password")))));
        this.mockMvc.perform(get("/post?id=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("post/post"));
    }

    @Test
    @WithMockUser
    public void shouldReturnDefaultMessage() throws Exception {
        given(this.userService.findByUsername("user"))
                .willReturn(Optional.of(User.of("user", "password")));
        this.mockMvc.perform(post("/save")
                        .param("name","Куплю ладу-грант. Дорого.")
                        .param("description", "some description"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Post> argument = ArgumentCaptor.forClass(Post.class);
        verify(postService).save(argument.capture());
        assertThat(argument.getValue().getName(), is("Куплю ладу-грант. Дорого."));
        assertThat(argument.getValue().getDescription(), is("some description"));
    }

    @Test
    @WithMockUser
    public void shouldUpdate() throws Exception {
        this.mockMvc.perform(post("/edit")
                        .param("id", "1")
                        .param("name","some name")
                        .param("description", "some description")
                        .param("created1", "2022-09-08T13:57:00.979438"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Post> argument = ArgumentCaptor.forClass(Post.class);
        verify(postService).update(argument.capture());
        assertThat(argument.getValue().getName(), is("some name"));
        assertThat(argument.getValue().getDescription(), is("some description"));
    }

    @Test
    @WithMockUser
    public void shouldPostComment() throws Exception {
        given(this.userService.findByUsername("user"))
                .willReturn(Optional.of(User.of("user", "password")));
        given(this.postService.findById(1)).willReturn(Optional.of(
                (Post.of("post1", "description1", User.of("user", "password")))));
        this.mockMvc.perform(post("/comment")
                        .param("post_id","1")
                        .param("body", "some comment"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Comment> argument = ArgumentCaptor.forClass(Comment.class);
        verify(postService).saveComment(argument.capture());
        assertThat(argument.getValue().getBody(), is("some comment"));
    }

}