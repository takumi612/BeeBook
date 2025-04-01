package beebooks.repository;

import beebooks.entities.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface SubscribeRepository extends JpaRepository<Subscribe, Integer>, JpaSpecificationExecutor<Subscribe> {
    Subscribe findByEmail(String email);
}
