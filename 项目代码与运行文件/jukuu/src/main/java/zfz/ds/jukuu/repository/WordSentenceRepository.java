package zfz.ds.jukuu.repository;

import org.springframework.data.repository.CrudRepository;
import zfz.ds.jukuu.model.WordSentence;

import java.util.List;

public interface WordSentenceRepository extends CrudRepository<WordSentence,Integer> {
    List<WordSentence> findByWordId(Integer id);
}
