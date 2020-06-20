package zfz.ds.jukuu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.WebContext;
import zfz.ds.jukuu.model.Sentence;
import zfz.ds.jukuu.model.Word;
import zfz.ds.jukuu.model.WordSentence;
import zfz.ds.jukuu.repository.SentenceRepository;
import zfz.ds.jukuu.repository.WordRepository;
import zfz.ds.jukuu.repository.WordSentenceRepository;
import zfz.ds.jukuu.util.ChunkSentence;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class SearchController {
    private static final Logger log = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private WordRepository wordRepository;
    @Autowired
    private SentenceRepository sentenceRepository;
    @Autowired
    private WordSentenceRepository wordSentenceRepository;

    @RequestMapping(path = "search")
    public String searchWord(
            @RequestParam String word,
            Model model
    ){
        List<Word> words = wordRepository.findByWord(word);
        model.addAttribute("found",false);
        if(!words.isEmpty()){
            model.addAttribute("wordInfo",words.get(0));
            model.addAttribute("found",true);
            List<WordSentence> wordSentences = wordSentenceRepository.findByWordId(words.get(0).getId());
            ArrayList<Sentence> sentences = new ArrayList<>();
            int count=0;
            if(!wordSentences.isEmpty()){
                for (WordSentence ws:wordSentences) {
                    Optional<Sentence> sentence= sentenceRepository.findById(ws.getSentenceId());
                    if(sentence.isPresent()){
                        Sentence sent = sentence.get();
                        sentences.add(sent);count++;
                        model.addAttribute("sentence"+count,sent);
                    }
                    if(count>=5)break;
                }
            }
            model.addAttribute("sentences",sentences);
            model.addAttribute("nums",count);
            System.out.println(count);
            System.out.println(sentences);
        }
        return "result";
    }

    @RequestMapping(path = "newword")
    public String addWord(
            @RequestParam String word,
            @RequestParam String phonetic,
            @RequestParam String definition,
            @RequestParam String translation,
            @RequestParam String exchange,
            Model model
            ){
        Word newWord = new Word();
        List<Word> words = wordRepository.findByWord(word);
        model.addAttribute("msg",false);
        if(words.isEmpty()&&!word.isEmpty()){
            newWord.setWord(word);
            newWord.setPhonetic(phonetic);
            newWord.setDefinition(definition);
            newWord.setTranslation(translation);
            newWord.setExchange(exchange);
            System.out.println(newWord);
            wordRepository.save(newWord);
            model.addAttribute("msg",newWord);
        }

        return "success";
    }

    @RequestMapping(path = "newmat")
    public String addMat(
            @RequestParam String message,
            Model model
    ){
        model.addAttribute("msg","您的请求已提交，我们正在进行处理");
        int count = 0;
        List<String> strings= ChunkSentence.chunkSentence(message);
        if (strings != null) {
            for (String s:strings) {
                List<String> wds = ChunkSentence.handlerWord(s);
                if(wds.size()>4){
                    Sentence sentence = new Sentence();
                    sentence.setSentence(s);
                    sentence.setBad(0);
                    sentence.setGood(0);
                    sentenceRepository.save(sentence);
                    count++;
                    for(String wd:wds){
                        List<Word> words = wordRepository.findByWord(wd);
                        List<WordSentence> wordSentences = new ArrayList<>();
                        if(!words.isEmpty()){
                            WordSentence wordSentence = new WordSentence();
                            wordSentence.setSentenceId(sentence.getId());
                            wordSentence.setWordId(words.get(0).getId());
                            wordSentences.add(wordSentence);
                            // wordSentenceRepository.save(wordSentence);
                        }
                        wordSentenceRepository.saveAll(wordSentences);
                    }
                }
            }
        }
        model.addAttribute("msg","成功解析句子数："+count);
        return "success";
    }
}
