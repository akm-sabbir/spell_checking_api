package bangla.grammarchecker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bangla.tokenizer.WordTokenizer;


import bangla.preprocessingUnit.*;
import bangla.spellchecker.spell_checking_dto;

public class ValidateSubVerbRelError implements BanglaGrammerChecker{
	private static final String notFound = "NotFound";
	private static final List<String> priority1 = Arrays.asList("আমি","আমরা","মোরা","তুমি","তোমরা","তোমাদের","সে","তারা","আপনি","আপনারা","তিনি","তিঁনি","তারা","ইনি","এরা","উনি","ওরা");
	static Map<String,String> purushMap;
	static WordTokenizer WT = new WordTokenizer();
	static Map<String,Integer> verbIndex = new HashMap<>();
	static List<List<String>> subVerbMatrix = new ArrayList<>();								 
	static TrieNode verbList = new TrieNode();
	public GrammerCheckerDto generateNgramSentences(String sentence) {
		
		WT.set_text(sentence);
		List<String> words = WT._tokenization();
		List<String> sentences;
		GrammerCheckerDto retDto = null;
		for(int ngram = 3; ngram < 5; ngram+=1) {
			sentences = nGramGenerator.ngramGenerator(words, ngram);
			
			for(int  each_sentence = 0; each_sentence < sentences.size() ; each_sentence += 1) {
				retDto = generateNgramSentences(sentences.get(each_sentence));
				retDto.wordSuggestion.get(0).wordIndex = nGramGenerator.getWordIndex(each_sentence, ngram, retDto.wordSuggestion.get(0).wordIndex);
			}
		}
		return retDto;
	}
	public GrammerCheckerDto hasError(String sentence) {
		WT.set_text(sentence);
		List<String> words = WT._tokenization();
		GrammerCheckerDto retDto = new GrammerCheckerDto();
		
		if(words.size()>=5) {
			retDto.isValidSentence=true;
			return retDto;
		}
		String subject = findSubject(words);
		String verb = findVerb(words);
		System.out.println(subject + " " + verb);
		
		if(subject.equals(notFound) || verb.equals(notFound)) {
			retDto.isValidSentence=true;
			return retDto;
		}
		if(!isSubjectVerbConflict(subject, verb)) {
			retDto.isValidSentence=true;
			return retDto;
		} else {
			retDto.isValidSentence=false;
			retDto.errorType="subject-verb-agreement-error";
			List<String> alternativ_subjects = getSuggestedSubject(verb);
			WordSuggestion suggestion = new WordSuggestion();
			suggestion.suggestedWordList = new ArrayList<String>();
			suggestion.wordName=subject;
			suggestion.wordIndex = findWordIndex(words, subject);
			suggestion.wordType="subject";														   
			if(alternativ_subjects != null) {
				for(String each_subjects : alternativ_subjects) {
					suggestion.suggestedWordList.add(each_subjects);
				}
			}
			
			retDto.wordSuggestion = Arrays.asList( suggestion);
			return retDto;
		}
	}
    private static int findWordIndex(List<String> words, String word) {
		//List<String> words = Arrays.asList(sentence.split(" "));
		for(int i=0;i<words.size();i++) {
			if(words.get(i).equals(word)) {
				return i;
			}
		}
		return 0;
	}
	private static List<String> getSuggestedSubject(String verb) {
		int index = verbIndex.get(verb);
		System.out.println(subVerbMatrix.get(index));
		if(subVerbMatrix.get(index).size()>0) return subVerbMatrix.get(index);
		return null;
	}
	private static boolean isSubjectVerbConflict(String subject, String verb) {
		if(verbIndex.containsKey(verb)) {
			int index = verbIndex.get(verb);
		//	System.out.println(verb);
			if(subVerbMatrix.get(index).contains(subject)) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}
	private static String findSubject(List<String> words) {
		List<String> allSubject = new ArrayList<>();
		for(String word: words) {
			String type = isPurushWord(word);
			if(!type.equals(notFound)) {
				allSubject.add(word);
			}
		}
		if(allSubject.size()==0) {
			return notFound;
		} else if(allSubject.size()==1) {
			return allSubject.get(0);
		} else {
			if(priority1.contains(allSubject.get(0))) {
				return allSubject.get(0);
			} else if(priority1.contains(allSubject.get(1))) {
				return allSubject.get(1);
			} else return allSubject.get(0);
		}
	}
	private static String findVerb(List<String> words) {
		String verb=notFound;
		String bivokti=notFound;
		for(String word: words) {
			String verbCandidate = removeIfNeg(word);
			if(isVerb(verbCandidate)) {
				if(verbCandidate.substring(verbCandidate.length()-1).equals("র")) {
					bivokti=verbCandidate;
				} else {
					verb=verbCandidate;
					break;
				}
			}
		}
		if(!verb.equals(notFound)) {
			return verb;
		} else {
			return bivokti;
		}
	}
	private static String removeIfNeg(String word) {
		int len = word.length();
		if(len>2) {
			if(word.substring(len-2).equals("নি") || word.substring(len-2).equals("না")) {
				return word.substring(0, len-2);
			} else return word;
		} else return word;
	}
	public static void setPurushMap(List<spell_checking_dto> rows) {
		purushMap = new HashMap<>();
		/*
		List<String> lines = new ArrayList<>();
		try {
			lines = ReadCsvFile.getPurusWordsFromCsvFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		for(spell_checking_dto row: rows) {
			//List<String> words = Arrays.asList(line.split(","));
			//if(line.size()>1) {
			purushMap.put(row.word, row.wordType);
			//}
		}
	}
	public static String isPurushWord(String word) {
		if(purushMap.containsKey(word)) {
			return purushMap.get(word);
		} else {
			return notFound;
		}
	}
	public static void buildTrie(List<spell_checking_dto> words) {
		spell_checking_dto mixed ;
	     
		for(int i=0;i<words.size();i++) {
			mixed = words.get(i); 
			if(mixed.word != null && !mixed.word.equals("invalid")) {
				TrieSubVerbRel.insert(mixed.word, verbList);
			}
			if(mixed.wordType != null && !mixed.wordType.equals("invalid")) {
				TrieSubVerbRel.insert(mixed.wordType, verbList);
			}
		}
		//VerbTagging.writeSubject();
		//System.out.println("Finish");
	}
	public static void buildSubVerbMap(List<ArrayList<String>> subVerbMap) {
                /*
		List<String> line = new ArrayList<>();
		try {
			line = ReadCsvFile.getSubVerbFromCsvFile();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		int index=0;
		for(int i=0;i<subVerbMap.size();i++) {
			List<String> mixed = subVerbMap.get(i);//Arrays.asList(line.get(i).split(","));
			List<String> subjects = new ArrayList<>();
			if(mixed.size()>2) {
				subjects = getAllSubjects(mixed.get(2));
			}
			
			if(mixed.size()>0 && !mixed.get(0).equals("invalid")) {
				verbIndex.put(mixed.get(0), index);
				subVerbMatrix.add(new ArrayList<>());
				for(String subject: subjects) {
					subVerbMatrix.get(index).add(subject);
				}
				index++;
			}
			if(mixed.size()>1 && !mixed.get(1).equals("invalid")) {
				verbIndex.put(mixed.get(1), index);
				subVerbMatrix.add(new ArrayList<>());
				for(String subject: subjects) {
					subVerbMatrix.get(index).add(subject);
				}
				index++;
			}
		}
		//System.out.println("finish");
	}
	private static List<String> getAllSubjects(String subjects) {
		if(subjects == null || subjects.length()==0) return new ArrayList<>();
		List<String> ret = Arrays.asList(subjects.split(","));
		return ret;
	}
	public static boolean isVerb(String word) {
		return TrieSubVerbRel.searchWord(word, verbList);
	}

}
