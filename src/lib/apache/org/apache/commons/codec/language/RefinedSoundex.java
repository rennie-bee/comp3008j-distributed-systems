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
/*     */ public class RefinedSoundex
/*     */   implements StringEncoder
/*     */ {
/*  36 */   public static final RefinedSoundex US_ENGLISH = new RefinedSoundex();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   public static final char[] US_ENGLISH_MAPPING = "01360240043788015936020505".toCharArray();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private char[] soundexMapping;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RefinedSoundex() {
/*  57 */     this(US_ENGLISH_MAPPING);
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
/*     */   public RefinedSoundex(char[] mapping) {
/*  70 */     this.soundexMapping = mapping;
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
/*     */   public int difference(String s1, String s2) throws EncoderException {
/*  96 */     return SoundexUtils.difference(this, s1, s2);
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
/*     */   public Object encode(Object pObject) throws EncoderException {
/* 113 */     if (!(pObject instanceof String)) {
/* 114 */       throw new EncoderException("Parameter supplied to RefinedSoundex encode is not of type java.lang.String");
/*     */     }
/* 116 */     return soundex((String)pObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String encode(String pString) {
/* 127 */     return soundex(pString);
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
/*     */   char getMappingCode(char c) {
/* 140 */     if (!Character.isLetter(c)) {
/* 141 */       return Character.MIN_VALUE;
/*     */     }
/* 143 */     return this.soundexMapping[Character.toUpperCase(c) - 65];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String soundex(String str) {
/* 154 */     if (str == null) {
/* 155 */       return null;
/*     */     }
/* 157 */     str = SoundexUtils.clean(str);
/* 158 */     if (str.length() == 0) {
/* 159 */       return str;
/*     */     }
/*     */     
/* 162 */     StringBuffer sBuf = new StringBuffer();
/* 163 */     sBuf.append(str.charAt(0));
/*     */ 
/*     */     
/* 166 */     char last = '*';
/*     */     
/* 168 */     for (int i = 0; i < str.length(); i++) {
/*     */       
/* 170 */       char current = getMappingCode(str.charAt(i));
/* 171 */       if (current != last) {
/*     */         
/* 173 */         if (current != '\000') {
/* 174 */           sBuf.append(current);
/*     */         }
/*     */         
/* 177 */         last = current;
/*     */       } 
/*     */     } 
/*     */     
/* 181 */     return sBuf.toString();
/*     */   }
/*     */ }


/* Location:              D:\safari downloads\apache-commons-codec-1.3.jar!\org\apache\commons\codec\language\RefinedSoundex.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */