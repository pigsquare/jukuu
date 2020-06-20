package zfz.ds.jukuu.repository;

import org.springframework.data.repository.CrudRepository;
import zfz.ds.jukuu.model.Word;

import java.util.List;

public interface WordRepository extends CrudRepository<Word,Integer> {
    List<Word> findByWord(String word);
}
