package test.metrics;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Stack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



public class DiffText 
{

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

		
		
//		String originalContent = "সরকার গণ পরিবহণে সমাজিক দূর দূরত্ব বজায় রাখার জন্য ৬০%ভাগ ভাড়া বৃদ্ধি করেছিল কিন্তু এখন গণপরিবহণে সামাজিক দূরত্ব মানা হয় না কিন্তু যাত্রীদের ঠিকই ৬০%ভাগ বেশি ভাড়া দিতে হচ্ছে। এখন এই ভাড়া কবে কমানো হবে?";
//		String correctedContent = "সরকার গণপরিবহণে সামাজিক দূরত্ব বজায় রাখার জন্য ৬০% ভাগ ভাড়া বৃদ্ধি করেছিল কিন্তু এখন গণপরিবহণে সামাজিক দূরত্ব মানা হয় না কিন্তু যাত্রীদের ঠিকই ৬০% ভাগ বেশি ভাড়া দিতে হচ্ছে। এখন এই ভাড়া কবে কমানো হবে?";
		
		String originalContent = "সরকার গণ পরিবহণে সমাজ সমাজিক দূর দূরত্ব বজায় রাখার জন্য ৬০%ভাগ ভাড়া বৃদ্ধি করেছিল কিন্তু এখন গণপরিবহণে সামাজিক দূরত্ব মানা হয় না কিন্তু যাত্রীদের ঠিকই ৬০%ভাগ বেশি ভাড়া দিতে হচ্ছে। এখন এই ভাড়া কবে কমানো হবে?";
		String correctedContent = "সরকার গণপরিবহণে সামাজিক দূরত্ব বজায় রাখার জন্য ৬০% ভাগ ভাড়া বৃদ্ধি করেছিল কিন্তু এখন গণপরিবহণে সামাজিক দূরত্ব মানা হয় না কিন্তু যাত্রীদের ঠিকই ৬০% ভাগ বেশি ভাড়া দিতে হচ্ছে।?। এখন এই ভাড়া কবে কমানো হবে?";		
		
//		String originalContent = "সরকার গণ পরিবহণে সমাজিক দূর দূরত্ব বজায় রাখার জন্য ভাড়া বৃদ্ধি করেছিল কিন্তু এখন গণপরিবহণে সামাজিক দূরত্ব মানা হয় না কিন্তু যাত্রীদের ঠিকই ৬০%ভাগ বেশি ভাড়া দিতে হচ্ছে। 	এখন এই ভাড়া কবে কমানো হবে?";
//		String correctedContent = "সরকার গণপরিবহণে সামাজিক দূরত্ব বজায় রাখার জন্য ৬০% ভাগ ভাড়া বৃদ্ধি করেছিল কিন্তু এখন গণপরিবহণে সামাজিক দূরত্ব মানা হয় না কিন্তু যাত্রীদের ঠিকই ৬০% ভাগ বেশি ভাড়া দিতে হচ্ছে এখন এই ভাড়া কবে কমানো হবে?";		
		
//		String originalContent = "উচ্চ হাসি হেসে সভা মাতিয়ে রাখা তার কাজ। বিষয়টির আদি-অন্ত পর্যন্ত তার জানা আছে।";
//		String correctedContent = "অট্টহাসিতে সভা মাতিয়ে রাখা তার কাজ। বিষয়টির আদ্যোপান্ত তার জানা আছে।";
		
//		String originalContent = "\"হাতে নাতে ধরার পর পুলিশ এসে যদি বলে তুমি নিজেই নাশকতাকারি তখন কি হবে? কারণ সরিষার (পুলিশের) ভেতরে ভুল থাকে কথাটি এমনি এমনি প্রচলন হয়নি। আজকের এই পত্রিকায়ই এক ওসিকে নিয়ে ছি ছি শিরোনামের খবর বের হয়েছে। এদেশের মানুষ পুলিশকে ডাকাতের চেয়ে বেশি ভয়। কথায় আছে বাঘে ছুঁলে ১৮ ঘাঁ আর পুলিসে ছুঁলে ৭২ ঘাঁ।\r\n" + 
//				"পুরস্কার ঘোষণা করলেও শিবিরের গুপত হামলাবাজদের এভাবে ধরা যাবে কিনা সনদেহ আছে কারণ এরা হত্যা ও অরাজকতায় পটু।\r\n" + 
//				"thhik.\r\n" + 
//				"BIG LIKE\r\n" + 
//				"ভালো উদ্দোগ - কোটি টাকার বিনিময়ে হলেও এদের ধরা উচিত।\r\n" + 
//				"ভাল সিদ্ধান্ত ।\r\n" + 
//				"ভাল উদ্দেগ ! এধরনের সার্কুলার সারা দেশে কার্যকর করা উচিত । পাশাপশি নাসকতা প্রতিরোধে সম্পৃক্ত পুলিশকেও পুরুস্কার দিয়ে উৎসাহিত করা উচিত ।\r\n" + 
//				"খুব যে লাভ হবে তা মনে হচ্ছে না.... কারণ সঠিক রোগের সঠিক ঔষধ প্রয়োগ না করলে রোগ কখনো সারবে না। জাতীর বর্তমান মূল সমস্যা সুষ্ঠ নির্বাচন। আগে এটার সমাধান করা উচিত। তখন বাকী গুলো অটোমেটিকলি সমাধান হয়ে যাবে।\r\n" + 
//				"ভাই , আমিও ১ লাখ দিব, advance , একাউন্ট নামভার den.\r\n" + 
//				"ভালো উদ্দ্যোগ। কিন্তু কাজে দেবে কি?\r\n" + 
//				"সঠিক সময়ে সঠিক উদ্যেগ ।\r\n" + 
//				"কোন নাশকতাকারী ?\r\n" + 
//				"নাশকতা, জনগণকে জিম্মি কখনো আন্দলনের ভাসা হতে পারে না, তাতে জনসমর্থন কমে।\r\n" + 
//				"বাংলাদেশ কি এখন তাহলে ঢাকার মধ্যে সীমাবদ্ধ হয়ে গেছে ? আমাদের মতো যাদের পরিবার জেলা সহরগুলতে বাস করে , তাদের নিরাপত্তার জন্য সরকারের কি ব্যবস্থা করছেন? দেশের অবস্থা আর কোন অবস্থায় গেলে প্রধানমন্ত্রী মনে করবেন তার অবস্থান পরিবর্তন করা দরকার।\r\n" + 
//				"শয়তান গুলোকে হাতেনাতে ধরে হাতপা চোখ বেধে গাড়িতে তুলে পেট্রল বোমা দিয়ে আগুন ধরিয়ে দেওয়া উচিত তখন বুঝতে পারত আগুনের কি কষ্ট\r\n" + 
//				"নাশকতা সৃষ্টিকারিদের হাতে নাতে ধরিয়ে দিতে পারলে লাখটাকা। লাখটাকার লোভের চেয়ে জীবনের লোভ অনেক বেশী তাই নাশকতা সৃষ্টিকারিদের ধরিয়ে দিয়ে নিজের জীবনের জন্য হুমকি ডেকে নিয়ে আসবে না। কেউ নাশকতা সৃষ্টিকারি ধরিয়ে দিলে পুলিশের লোকই টাকার বিনিময়ে দেশপ্রেমিকে নাম অপরাধীদের হাতে তুলে দিবে। তাই এ লাখ টাকার টোপ কেউ গিলবে বলে মনে হয় না।\r\n" + 
//				"কাজের অফারটা লোভনীয়, কিন্তু বেশ কষ্ট সাধ্য। বিএনপি- জামাতের ১টা মাঝারি সাইজের নাশকতাকারী ১০জন শক্তিশালি সাধারন পাবলিকের সমান বা বেশী। টাকার অঙ্কটা একটু বাড়িয়ে দেবেন।\r\n" + 
//				"এত দিনে .।\r\n" + 
//				"ভালো। তার পরের নিরাপত্তা কি সরকার আমাদের দিবে?\r\n" + 
//				"good initiative. Some people may find it costly though. Actually If we see the big picture, this amount is thousands times less than the damages done by BNP/ Jamat/ activists to public lives and properties. I highly appreciate this action by DMP.\r\n" + 
//				"ভাল উদ্যোগ। তবে পুলিশকে প্রথমে উদ্যোগী হতে হবে। বগুড়ার শাজাহানপুর থানার ভারপ্রাপ্ত কর্মকর্তার মত বোমাবাজদের থেকে অধিক টাকা নিয়ে চুপ করে বসে থাকলে তাদের প্রতিরোধ করা যাবে না।\r\n" + 
//				"Good decision...\r\n" + 
//				"ভালো প্রস্তাব তবে লাখ টাকা পুরস্কার হাতে নিয়ে বাকি জীবনের নিশ্চয়তা কে দিবে !!\r\n" + 
//				"টাকা পুরষ্কার দিয়ে মানুষের নিরাপত্তা দিবেন তাই না । খবরটা পড়ে হাসি ধরে রাখতে পারলাম না ।\r\n" + 
//				"I highly appreciate this action by DMP\r\n" + 
//				"Good step by government,we should cooparate with Police and give information to find criminals\r\n" + 
//				"কোন কিচুতেই কাজ হবে না।\r\n" + 
//				"এক লক্ষ অনেক কম হয়ে গেল না ? জীবনের ঝুকি নিয়ে বোমাবাজদের ধরিয়ে দিতে দেশপ্রেমিকদের যদি কোনো ক্ষতি হয় তাহলে কি সরকার তার বা তার পরিবারের দায় দায়িত্ব নিবে?\r\n" + 
//				"চাকরী বাকরি বাদ দিয়ে দেখি এই বোমা নাশকতা কারীদের ধরায় মনোযোগ দিতে হবে।\r\n" + 
//				"ধরতে পারলে ফাঁসি দেয়া দরকার...\r\n" + 
//				"খুবই ভালো উদ্যোগ।\r\n" + 
//				"ভালো উদ্যোগ। কিন্তু আরো আগে কেন নিলেন না? সবাইকে অনুরোধ ফেসবুকে শেয়ার করুন।\r\n" + 
//				"Good idea.asha kori nasokota komae emon uddog kaje asbe.\r\n" + 
//				"আসুন সবা্য় ককটেলিস্ট খুজি......\r\n" + 
//				"চান্সে যদি এবার কিছু হাত খরচার টাকা পাওয়া যাই !!!\r\n" + 
//				"এই চমৎকার উদ্যোগের বাংলাদেশ পুলিশকে ধন্যবাদ।\r\n" + 
//				"\"\r\n" + 
//				"";
//		
//		String correctedContent = "হাতেনাতে ধরার পর পুলিশ এসে যদি বলে তুমি নিজেই নাশকতাকারী তখন কী হবে? কারণ সরিষার (পুলিশের) ভেতরে ভুল থাকে কথাটি এমনি এমনি প্রচলন হয়নি। আজকের এই পত্রিকাতেই এক ওসিকে নিয়ে ছি ছি শিরোনামে খবর বের হয়েছে। এদেশের মানুষ পুলিশকে ডাকাতের চেয়ে বেশি ভয়। কথায় আছে বাঘে ছুঁলে ১৮ ঘা আর পুলিশে ছুঁলে ৭২ ঘা।\r\n" + 
//				" পুরস্কার ঘোষণা করলেও শিবিরের গুপ্ত হামলাবাজদের এভাবে ধরা যাবে কি না সন্দেহ আছে কারণ এরা হত্যা ও অরাজকতায় পটু।\r\n" + 
//				" thhik.\r\n" + 
//				" BIG LIKE\r\n" + 
//				" ভালো উদ্যোগ - কোটি টাকার বিনিময়ে হলেও এদের ধরা উচিত।\r\n" + 
//				" ভালো সিদ্ধান্ত ।\r\n" + 
//				" ভালো উদ্যোগ ! এ ধরনের সার্কুলার সারাদেশে কার্যকর করা উচিত । পাশাপশি নাশকতা প্রতিরোধে সম্পৃক্ত পুলিশকেও পুরস্কার দিয়ে উৎসাহিত করা উচিত ।\r\n" + 
//				" খুব যে লাভ হবে তা মনে হচ্ছে না.... কারণ সঠিক রোগের সঠিক ঔষধ প্রয়োগ না করলে রোগ কখনো সারবে না। জাতির বর্তমান মূল সমস্যা সুষ্ঠু নির্বাচন। আগে এটার সমাধান করা উচিত। তখন বাকিগুলো অটোমেটিক্যালি সমাধান হয়ে যাবে।\r\n" + 
//				" ভাই , আমিও ১ লাখ দেব, advance , অ্যাকাউন্ট নম্বর den.\r\n" + 
//				" ভালো উদ্যোগ। কিন্তু কাজে দেবে কি?\r\n" + 
//				" সঠিক সময়ে সঠিক উদ্যোগ ।\r\n" + 
//				" কোন নাশকতাকারী ?\r\n" + 
//				" নাশকতা, জনগণকে জিম্মি কখনো আন্দোলনের ভাষা হতে পারে না, তাতে জনসমর্থন কমে।\r\n" + 
//				" বাংলাদেশ কি এখন তাহলে ঢাকার মধ্যে সীমাবদ্ধ হয়ে গেছে ? আমাদের মতো যাদের পরিবার জেলা শহরগুলোতে বাস করে , তাদের নিরাপত্তার জন্য সরকারের কী ব্যবস্থা করছে? দেশের অবস্থা আর কোন অবস্থায় গেলে প্রধানমন্ত্রী মনে করবেন তার অবস্থান পরিবর্তন করা দরকার।\r\n" + 
//				" শয়তানগুলোকে হাতেনাতে ধরে হাত-পা-চোখ বেঁধে গাড়িতে তুলে পেট্রোল বোমা দিয়ে আগুন ধরিয়ে দেওয়া উচিত তখন বুঝতে পারত আগুনের কী কষ্ট\r\n" + 
//				" নাশকতা সৃষ্টিকারীদের হাতেনাতে ধরিয়ে দিতে পারলে লাখ টাকা। লাখ টাকার লোভের চেয়ে জীবনের লোভ অনেক বেশি তাই নাশকতা সৃষ্টিকারীদের ধরিয়ে দিয়ে নিজের জীবনের জন্য হুমকি ডেকে নিয়ে আসবে না। কেউ নাশকতা সৃষ্টিকারী ধরিয়ে দিলে পুলিশের লোকই টাকার বিনিময়ে দেশপ্রেমিকের নাম অপরাধীদের হাতে তুলে দিবে। তাই এ লাখ টাকার টোপ কেউ গিলবে বলে মনে হয় না।\r\n" + 
//				" কাজের অফারটা লোভনীয়, কিন্তু বেশ কষ্টসাধ্য। বিএনপি- জামায়াতের ১টা মাঝারি সাইজের নাশকতাকারী ১০জন শক্তিশালী সাধারণ পাবলিকের সমান বা বেশি। টাকার অঙ্কটা একটু বাড়িয়ে দেবেন।\r\n" + 
//				" এতদিনে .।\r\n" + 
//				" ভালো। তার পরের নিরাপত্তা কি সরকার আমাদের দেবে?\r\n" + 
//				" good initiative. Some people may find it costly though. Actually If we see the big picture, this amount is thousands times less than the damages done by BNP/ Jamat/ activists to public lives and properties. I highly appreciate this action by DMP.\r\n" + 
//				" ভাল উদ্যোগ। তবে পুলিশকে প্রথমে উদ্যোগী হতে হবে। বগুড়ার শাজাহানপুর থানার ভারপ্রাপ্ত কর্মকর্তার মত বোমাবাজদের থেকে অধিক টাকা নিয়ে চুপ করে বসে থাকলে তাদের প্রতিরোধ করা যাবে না।\r\n" + 
//				" Good decision...\r\n" + 
//				" ভালো প্রস্তাব তবে লাখ টাকা পুরস্কার হাতে নিয়ে বাকি জীবনের নিশ্চয়তা কে দেবে !!\r\n" + 
//				" টাকা পুরস্কার দিয়ে মানুষের নিরাপত্তা দিবেন তাই না । খবরটা পড়ে হাসি ধরে রাখতে পারলাম না ।\r\n" + 
//				" I highly appreciate this action by DMP\r\n" + 
//				" Good step by government,we should cooparate with Police and give information to find criminals\r\n" + 
//				" কোনোকিছুতেই কাজ হবে না।\r\n" + 
//				" এক লক্ষ অনেক কম হয়ে গেল না ? জীবনের ঝুঁকি নিয়ে বোমাবাজদের ধরিয়ে দিতে দেশপ্রেমিকদের যদি কোনো ক্ষতি হয় তাহলে কি সরকার তার বা তার পরিবারের দায় দায়িত্ব নেবে?\r\n" + 
//				" চাকরি-বাকরি বাদ দিয়ে দেখি এই বোমা নাশকতাকারীদের ধরায় মনোযোগ দিতে হবে।\r\n" + 
//				" ধরতে পারলে ফাঁসি দেওয়া দরকার...\r\n" + 
//				" খুবই ভালো উদ্যোগ।\r\n" + 
//				" ভালো উদ্যোগ। কিন্তু আরো আগে কেন নিলেন না? সবাইকে অনুরোধ ফেসবুকে শেয়ার করুন।\r\n" + 
//				" Good idea.asha kori nasokota komae emon uddog kaje asbe.\r\n" + 
//				" আসুন সবাই ককটেলবাজ খুঁজি......\r\n" + 
//				" চান্সে যদি এবার কিছু হাতখরচের টাকা পাওয়া যায়!!!\r\n" + 
//				" এই চমৎকার উদ্যোগের বাংলাদেশ পুলিশকে ধন্যবাদ।\r\n" + 
//				"";
		
		System.out.println("original  : " + originalContent);
		System.out.println("corrected : " + correctedContent);
		
		DiffText dt = new DiffText();
		dt.alignText(originalContent, correctedContent);

		
		
	}
	
	public String preprocessText(String text)
	{
		String s = text;
		
		s = s.replaceAll("(\\x{0964})+", " \u0964 ");
		s = s.replaceAll("(\\x{003F})+", " \u003F ");
		s = s.replaceAll("(\\x{002C})+", " \u002C ");
		s = s.replaceAll("(\\x{0009})+", "\u0020");
		s = s.replaceAll("(\\x{0020})+", "\u0020");
		
		
		return s;
	}
	
	public List<Object> alignText(String originalContent, String correctedContent)
	{

		diff_match_patch dmp = new diff_match_patch();
		
		LinkedList<diff_match_patch.Diff> diff = dmp.diff_main(preprocessText(originalContent), preprocessText(correctedContent));
		
//		List<Map<String, String>> alignment = new LinkedList<Map<String, String>>(); 
//		Map<String, String> token = null;

		dmp.diff_cleanupSemantic(diff);
		
//		System.out.println(diff);
		
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		
		List<Object> alignment1 = findWordIndexV2(diff, originalContent);
		
//		System.out.println(gson.toJson(alignment1));
		
		List<Object> alignment2 = mergeL1(alignment1);
		
//		System.out.println(gson.toJson(alignment2));
		
		List<Object> alignment3 = mergeL2(alignment2);
		
//		System.out.println(gson.toJson(alignment3));
		
//		showRow(processMerging(processAlignment(diff)));
		
		return alignment3;
		
	}
	
	public List<Object> findWordIndexV2(LinkedList<diff_match_patch.Diff> diff, String originalContent)
	{
		int wi = 0;
		
		List<Object> alignment = new LinkedList<>();
		
		Map<Object, Object> map = null;
		
		while(!diff.isEmpty())
		{
			diff_match_patch.Diff d = diff.poll();

			if(d.operation == diff_match_patch.Operation.DELETE)
			{
				map = new LinkedHashMap<>();
				String t = d.text;
				
				wi = originalContent.indexOf(t, wi);

//				map.put("index", wi);
				map.put("startIndex", wi);
				map.put("original", t);
				map.put("corrected", "");
				map.put("operation", "delete");
				map.put("mergeable", "none");
				
				alignment.add(map);
				
				wi += t.length();				
			}
			
			if(d.operation == diff_match_patch.Operation.INSERT)
			{
				map = new LinkedHashMap<>();
				String t = d.text;
				
//				map.put("index", -1);
				map.put("startIndex", -1);
				map.put("original", "");
				map.put("corrected", t);				
				map.put("operation", "insert");
				map.put("mergeable", "none");
				
				alignment.add(map);
			}
			
			if(d.operation == diff_match_patch.Operation.EQUAL)
			{
				
				String t = d.text;
				
				String[] ws = t.split(" ");
				
				for(int i = 0; i< ws.length; i++)
				{
					map = new LinkedHashMap<>();
					
					wi = originalContent.indexOf(ws[i], wi);
	
//					map.put("index", wi);
					map.put("startIndex", wi);
					map.put("original", ws[i]);
					map.put("corrected", ws[i]);
					map.put("operation", "equal");
					map.put("mergeable", "none");
					
					if(i == 0 && !t.startsWith(" "))
						map.put("mergeable", "prev");

					if(i == ws.length-1 && !t.endsWith(" "))
						map.put("mergeable", "next");					
					
					alignment.add(map);
					
					wi += ws[i].length();
				}
			}
		}
		
		return alignment;
	}
	
	public List<Object> mergeL1(List<Object> align)
	{
		List<Object> alignment = new LinkedList<>(); 
		Map<Object, Object> token = null;		
		
		String originalContent = "";
		String correctedContent = "";
		int index = 0;
		
		boolean prevEqual = false;
		
		for(Object element : align)	
		{
			Map<Object, Object> map = (Map<Object, Object>) element;
			
			String operation = (String) map.get("operation");
			
			if(operation.equalsIgnoreCase("delete"))
			{
				originalContent += (String)map.get("original");
//				index = (int) map.get("index");
				index = (int) map.get("startIndex");
				
				prevEqual = false;
			}
			
			if(operation.equalsIgnoreCase("insert"))
			{
				correctedContent += (String)map.get("corrected");
				
				prevEqual = false;
			}
			
			if(operation.equalsIgnoreCase("equal"))
			{
				if(!prevEqual)
				{
					token = new LinkedHashMap<Object, Object>();
//					token.put("index", index);
					token.put("startIndex", index);
					token.put("original", originalContent);
					token.put("corrected", correctedContent);
					token.put("mergeable", "none");
					alignment.add(token);

					originalContent = "";
					correctedContent = "";
				}

				prevEqual = true;
				
				map.remove("operation");
				alignment.add(map);
			}
		}
		
		if(!prevEqual)
		{
			token = new LinkedHashMap<Object, Object>();
//			token.put("index", index);
			token.put("startIndex", index);
			token.put("original", originalContent);
			token.put("corrected", correctedContent);
			token.put("mergeable", "none");
			alignment.add(token);		
		}

		return alignment;
	}
	
	public List<Object> mergeL2(List<Object> alignment)
	{
		ListIterator<Object> iter = alignment.listIterator();
		
		Stack<Object> stack = new Stack<>();
		
		while(iter.hasNext())
		{
			Map<Object, Object> element = (Map<Object, Object>) iter.next();
			
			if(((String) element.get("mergeable")).equalsIgnoreCase("none"))
			{
				Map<Object, Object> top = null;

				if(!stack.isEmpty())
					 top = (Map<Object, Object>) stack.pop();
				
				if(top == null)
					stack.push(element);
				else
				{
					if(((String) top.get("mergeable")).equalsIgnoreCase("none"))
					{
						stack.push(top);
						stack.push(element);
					}
					if(((String) top.get("mergeable")).equalsIgnoreCase("next"))
					{
						Map<Object, Object> elem = new LinkedHashMap<>();
//						elem.put("index", (int)top.get("index"));
						elem.put("startIndex", (int)top.get("startIndex"));
						elem.put("original", (String)top.get("original") + (String)element.get("original"));
						elem.put("corrected", (String)top.get("corrected") + (String)element.get("corrected"));
						elem.put("mergeable", "none");
						
						stack.push(elem);						
					}
				}
			}
			
			if(((String) element.get("mergeable")).equalsIgnoreCase("prev"))
			{
				Map<Object, Object> top = null;

				if(!stack.isEmpty())
					 top = (Map<Object, Object>) stack.pop();
				
				if(top != null)
				{
					Map<Object, Object> elem = new LinkedHashMap<>();
//					elem.put("index", (int)top.get("index"));
					elem.put("startIndex", (int)top.get("startIndex"));
					elem.put("original", (String)top.get("original") + (String)element.get("original"));
					elem.put("corrected", (String)top.get("corrected") + (String)element.get("corrected"));
					elem.put("mergeable", "none");
					
					stack.push(elem);
				}
				else
				{
					stack.push(element);
				}
			}
			
			if(((String) element.get("mergeable")).equalsIgnoreCase("next"))
			{
				stack.push(element);
			}
		}
		
		Stack<Object> stackr = new Stack<>();
		
		while(!stack.isEmpty())
		{
			Object top = stack.pop();
			
			stackr.push(top);
		}
		
		List<Object> target = new LinkedList<>();
		
		while(!stackr.isEmpty())
		{
			Map<Object, Object> top = (Map<Object, Object>) stackr.pop();
			
			top.remove("mergeable");
			
			target.add(top);
		}
		
		return target;
	}	
	
}
