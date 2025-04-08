package beebooks.repository;

import beebooks.entities.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ImageRepository extends JpaRepository<Images, Integer>{
    @Modifying
    @Transactional
    void deleteProductImageByPath(String path);
}
