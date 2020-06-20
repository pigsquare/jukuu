package zfz.ds.jukuu.util;

import com.aliasi.chunk.Chunk;
import com.aliasi.chunk.Chunking;
import com.aliasi.sentences.MedlineSentenceModel;
import com.aliasi.sentences.SentenceChunker;
import com.aliasi.sentences.SentenceModel;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.TokenizerFactory;

import java.util.*;

public class ChunkSentence {
    static public List<String> chunkSentence(String text){
        TokenizerFactory TOKENIZER_FACTORY = IndoEuropeanTokenizerFactory.INSTANCE;
        SentenceModel SENTENCE_MODEL  = new MedlineSentenceModel();
        SentenceChunker SENTENCE_CHUNKER
                = new SentenceChunker(TOKENIZER_FACTORY,SENTENCE_MODEL);
        Chunking chunking
                = SENTENCE_CHUNKER.chunk(text.toCharArray(),0,text.length());
        Set<Chunk> sentences = chunking.chunkSet();
        if (sentences.size() < 1) {
            System.out.println("No sentence chunks found.");
            return null;
        }
        String slice = chunking.charSequence().toString();
        int i = 1;
        List<String> stringList = new ArrayList<String>();
        for (Iterator<Chunk> it = sentences.iterator(); it.hasNext(); ) {
            Chunk sentence = it.next();
            int start = sentence.start();
            int end = sentence.end();
            System.out.println("SENTENCE "+(i++)+":");
            System.out.println(slice.substring(start,end));
            stringList.add(slice.substring(start,end));
        }
        return stringList;
    }

   public static List<String> handlerWord(String text){
       List<String> stringList = new ArrayList<String>();
       HashSet<String> hashSet = new HashSet<>();
       StringTokenizer stringTokenizer = new StringTokenizer(text, ",!\" .;?");
       while(stringTokenizer.hasMoreElements()){
           String s = (String) stringTokenizer.nextElement();
           if(s.length()>0){
               if(hashSet.add(s))
                   stringList.add(s);
           }
       }
       return stringList;
   }
}
