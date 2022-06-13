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
/*     */ public class Soundex
/*     */   implements StringEncoder
/*     */ {
/*  36 */   public static final Soundex US_ENGLISH = new Soundex();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String US_ENGLISH_MAPPING_STRING = "01230120022455012623010202";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   public static final char[] US_ENGLISH_MAPPING = "01230120022455012623010202".toCharArray();
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
/*  78 */     return SoundexUtils.difference(this, s1, s2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   private int maxLength = 4;
/*     */ 
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
/*     */   public Soundex() {
/* 101 */     this(US_ENGLISH_MAPPING);
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
/*     */   public Soundex(char[] mapping) {
/* 115 */     setSoundexMapping(mapping);
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
/* 132 */     if (!(pObject instanceof String)) {
/* 133 */       throw new EncoderException("Parameter supplied to Soundex encode is not of type java.lang.String");
/*     */     }
/* 135 */     return soundex((String)pObject);
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
/*     */   public String encode(String pString) {
/* 148 */     return soundex(pString);
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
/*     */   private char getMappingCode(String str, int index) {
/* 165 */     char mappedChar = map(str.charAt(index));
/*     */     
/* 167 */     if (index > 1 && mappedChar != '0') {
/* 168 */       char hwChar = str.charAt(index - 1);
/* 169 */       if ('H' == hwChar || 'W' == hwChar) {
/* 170 */         char preHWChar = str.charAt(index - 2);
/* 171 */         char firstCode = map(preHWChar);
/* 172 */         if (firstCode == mappedChar || 'H' == preHWChar || 'W' == preHWChar) {
/* 173 */           return Character.MIN_VALUE;
/*     */         }
/*     */       } 
/*     */     } 
/* 177 */     return mappedChar;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxLength() {
/* 187 */     return this.maxLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private char[] getSoundexMapping() {
/* 196 */     return this.soundexMapping;
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
/*     */   private char map(char ch) {
/* 209 */     int index = ch - 65;
/* 210 */     if (index < 0 || index >= (getSoundexMapping()).length) {
/* 211 */       throw new IllegalArgumentException("The character is not mapped: " + ch);
/*     */     }
/* 213 */     return getSoundexMapping()[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxLength(int maxLength) {
/* 224 */     this.maxLength = maxLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setSoundexMapping(char[] soundexMapping) {
/* 234 */     this.soundexMapping = soundexMapping;
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
/*     */   public String soundex(String str) {
/* 247 */     if (str == null) {
/* 248 */       return null;
/*     */     }
/* 250 */     str = SoundexUtils.clean(str);
/* 251 */     if (str.length() == 0) {
/* 252 */       return str;
/*     */     }
/* 254 */     char[] out = { '0', '0', '0', '0' };
/*     */     
/* 256 */     int incount = 1, count = 1;
/* 257 */     out[0] = str.charAt(0);
/* 258 */     char last = getMappingCode(str, 0);
/* 259 */     while (incount < str.length() && count < out.length) {
/* 260 */       char mapped = getMappingCode(str, incount++);
/* 261 */       if (mapped != '\000') {
/* 262 */         if (mapped != '0' && mapped != last) {
/* 263 */           out[count++] = mapped;
/*     */         }
/* 265 */         last = mapped;
/*     */       } 
/*     */     } 
/* 268 */     return new String(out);
/*     */   }
/*     */ }


/* Location:              D:\safari downloads\apache-commons-codec-1.3.jar!\org\apache\commons\codec\language\Soundex.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */