package com.packt.springboot.formhandling.blogpost;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

/**
 * This controller demonstrates the various methods to process data from a web form:
 *
 * <ul>
 * <li>Multiple key/value pairs</li>
 * <li>Backing bean</li>
 * <li>Validated backing bean</li>
 * </ul>
 */
@Controller
@RequestMapping("/blogposts")
@Slf4j
public class BlogPostController {

    @GetMapping("new-multiple-values")
    public String renderFormViewForSeparateValues() {
        return "blogposts/form-multiple-values";
    }

    /**
     * Render the view that contains a Thymeleaf form utilizing a backing bean.
     */
    @GetMapping("new-backing-bean")
    public ModelAndView renderFormViewForBackingBean() {
        BlogPostCommand blogPostCommand = new BlogPostCommand();
        blogPostCommand.setTitle("Default Title");

        return new ModelAndView("blogposts/form-backing-bean", "blogPostCommand", blogPostCommand);
    }

    /**
     * Render the view that contains a Thymeleaf form utilizing a validated backing bean.
     */
    @GetMapping("new-validated-bean")
    public String renderFormViewForValidatedBean() {
        ValidatedBlogPostCommand validatedBlogPostCommand = new ValidatedBlogPostCommand();
        validatedBlogPostCommand.setTitle("Default Title");

        return "blogposts/form-validated-bean";
    }

    /**
     * Create a new blog post by processing multiple key/value parameters and display the result.
     */
    @PostMapping("create-multiple-values")
    public ModelAndView createBlogPostFromMultipleValues(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "slug", required = false) String slug,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "visible", required = false) boolean visible) {
        BlogPost createdBlogPost = createBlogPost(title, slug, content, visible);

        return new ModelAndView("blogposts/show", "blogPost", createdBlogPost);
    }

    /**
     * Create a new blog post by processing separate key/value parameters and display the result.
     */
    @PostMapping("create-backing-bean")
    public ModelAndView createBlogPostFromBackingBean(@RequestBody BlogPostCommand blogPostCommand) {
        BlogPost createdBlogPost = createBlogPost(
                blogPostCommand.getTitle(),
                blogPostCommand.getSlug(),
                blogPostCommand.getContent(),
                blogPostCommand.isVisible());

        return new ModelAndView("blogposts/show", "blogPost", createdBlogPost);
    }

    /**
     * Create a new blog post by processing separate key/value parameters and display the result.
     */
    @PostMapping("create-validated-bean")
    public ModelAndView createBlogPostFromValidatedBean(@RequestBody @Validated ValidatedBlogPostCommand validatedBlogPostCommand) {
        BlogPost createdBlogPost = createBlogPost(
                validatedBlogPostCommand.getTitle(),
                validatedBlogPostCommand.getSlug(),
                validatedBlogPostCommand.getContent(),
                validatedBlogPostCommand.isVisible());

        return new ModelAndView("blogposts/show", "blogPost", createdBlogPost);
    }

    /**
     * Simulate some processing being done to create a given blog post.
     */
    private BlogPost createBlogPost(String title, String slug, String content, boolean visible) {
        BlogPost createdBlogPost = new BlogPost(
                LocalDateTime.now(),
                title,
                slug,
                content,
                visible);

        log.info("Created blog post {}", createdBlogPost);

        return createdBlogPost;
    }

}
