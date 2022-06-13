/*     */ package lib.apache.org.apache.commons.codec.language;
/*     */ 
/*     */ import lib.apache.org.apache.commons.codec.EncoderException;
/*     */ import lib.apache.org.apache.commons.codec.StringEncoder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class SoundexUtils
/*     */ {
/*     */   static String clean(String str) {
/*  40 */     if (str == null || str.length() == 0) {
/*  41 */       return str;
/*     */     }
/*  43 */     int len = str.length();
/*  44 */     char[] chars = new char[len];
/*  45 */     int count = 0;
/*  46 */     for (int i = 0; i < len; i++) {
/*  47 */       if (Character.isLetter(str.charAt(i))) {
/*  48 */         chars[count++] = str.charAt(i);
/*     */       }
/*     */     } 
/*  51 */     if (count == len) {
/*  52 */       return str.toUpperCase();
/*     */     }
/*  54 */     return (new String(chars, 0, count)).toUpperCase();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int difference(StringEncoder encoder, String s1, String s2) throws EncoderException {
/*  84 */     return differenceEncoded(encoder.encode(s1), encoder.encode(s2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int differenceEncoded(String es1, String es2) {
/* 109 */     if (es1 == null || es2 == null) {
/* 110 */       return 0;
/*     */     }
/* 112 */     int lengthToMatch = Math.min(es1.length(), es2.length());
/* 113 */     int diff = 0;
/* 114 */     for (int i = 0; i < lengthToMatch; i++) {
/* 115 */       if (es1.charAt(i) == es2.charAt(i)) {
/* 116 */         diff++;
/*     */       }
/*     */     } 
/* 119 */     return diff;
/*     */   }
/*     */ }


/* Location:              D:\safari downloads\apache-commons-codec-1.3.jar!\org\apache\commons\codec\language\SoundexUtils.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */