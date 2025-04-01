package beebooks.repository;

import beebooks.entities.BlogImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface BlogImageRepository extends JpaRepository<BlogImage, Integer> {
    @Modifying
    @Transactional
    void deleteByBlogId(@Param("blogId") Integer blogId);
}
