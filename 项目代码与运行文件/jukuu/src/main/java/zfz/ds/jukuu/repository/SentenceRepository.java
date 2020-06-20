package zfz.ds.jukuu.repository;

import org.springframework.data.repository.CrudRepository;
import zfz.ds.jukuu.model.Sentence;

import java.util.Optional;


public interface SentenceRepository extends CrudRepository<Sentence,Integer> {
    Optional<Sentence> findById(Integer id);
}
