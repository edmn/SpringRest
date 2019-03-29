package start;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import start.domain.Blog;

import java.util.List;
import java.util.Map;

@RestController
public class BlogController {


    // use autowire and spring will handle DI automatically
    // BlogMockedData blogMockedData = BlogMockedData.getInstance();

    private final BlogRepository blogRepository;

    @Autowired
    public BlogController(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @GetMapping("/blog")
    public List<Blog> index() {
        return blogRepository.findAll();
    }

    @GetMapping("/blog/{id}")
    public Blog show(@PathVariable String id) {
        int blogId = Integer.parseInt(id);
        //return blogMockedData.getBlogById(blogId);
        return blogRepository.findOne(blogId);
    }

    @PostMapping("/blog/search")
    public List<Blog> search(@RequestBody Map<String, String> body) {
        String searchTerm = body.get("text");
        //return blogMockedData.searchBlogs(searchTerm);
        return blogRepository.findByTitleContainingOrContentContaining(searchTerm,searchTerm);
    }

    @PostMapping("/blog")
    public Blog create(@RequestBody Map<String, String> body) {
        //int id = Integer.parseInt(body.get("id"));
        String title = body.get("title");
        String content = body.get("content");
        //return blogMockedData.createBlog(id, title, content);
        return blogRepository.save(new Blog(title, content));
    }

    @PutMapping("/blog/{id}")
    public Blog update(@PathVariable String id, @RequestBody Map<String, String> body) {
        //        int blogId = Integer.parseInt(id);
        //        String title = body.get("title");
        //        String content = body.get("content");
        //        return blogMockedData.updateBlog(blogId, title, content);
        int blogId = Integer.parseInt(id);
        Blog blog = blogRepository.findOne(blogId);
        blog.setTitle(body.get("title"));
        blog.setContent("content");
        return this.blogRepository.save(blog);
    }

    @DeleteMapping("/blog/{id}")
    public boolean delete(@PathVariable String id) {
        int blogId = Integer.parseInt(id);
        //return blogMockedData.delete(blogId);
        blogRepository.delete(blogId);
        return true;
    }


}
