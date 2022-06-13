/*     */ package lib.apache.org.apache.commons.codec.net;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.BitSet;
/*     */ import lib.apache.org.apache.commons.codec.DecoderException;
/*     */ import lib.apache.org.apache.commons.codec.EncoderException;
/*     */ import lib.apache.org.apache.commons.codec.StringDecoder;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class QCodec
/*     */   extends RFC1522Codec
/*     */   implements StringEncoder, StringDecoder
/*     */ {
/*  51 */   private String charset = "UTF-8";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   private static final BitSet PRINTABLE_CHARS = new BitSet(256);
/*     */ 
/*     */   
/*     */   static {
/*  60 */     PRINTABLE_CHARS.set(32);
/*  61 */     PRINTABLE_CHARS.set(33);
/*  62 */     PRINTABLE_CHARS.set(34);
/*  63 */     PRINTABLE_CHARS.set(35);
/*  64 */     PRINTABLE_CHARS.set(36);
/*  65 */     PRINTABLE_CHARS.set(37);
/*  66 */     PRINTABLE_CHARS.set(38);
/*  67 */     PRINTABLE_CHARS.set(39);
/*  68 */     PRINTABLE_CHARS.set(40);
/*  69 */     PRINTABLE_CHARS.set(41);
/*  70 */     PRINTABLE_CHARS.set(42);
/*  71 */     PRINTABLE_CHARS.set(43);
/*  72 */     PRINTABLE_CHARS.set(44);
/*  73 */     PRINTABLE_CHARS.set(45);
/*  74 */     PRINTABLE_CHARS.set(46);
/*  75 */     PRINTABLE_CHARS.set(47); int i;
/*  76 */     for (i = 48; i <= 57; i++) {
/*  77 */       PRINTABLE_CHARS.set(i);
/*     */     }
/*  79 */     PRINTABLE_CHARS.set(58);
/*  80 */     PRINTABLE_CHARS.set(59);
/*  81 */     PRINTABLE_CHARS.set(60);
/*  82 */     PRINTABLE_CHARS.set(62);
/*  83 */     PRINTABLE_CHARS.set(64);
/*  84 */     for (i = 65; i <= 90; i++) {
/*  85 */       PRINTABLE_CHARS.set(i);
/*     */     }
/*  87 */     PRINTABLE_CHARS.set(91);
/*  88 */     PRINTABLE_CHARS.set(92);
/*  89 */     PRINTABLE_CHARS.set(93);
/*  90 */     PRINTABLE_CHARS.set(94);
/*  91 */     PRINTABLE_CHARS.set(96);
/*  92 */     for (i = 97; i <= 122; i++) {
/*  93 */       PRINTABLE_CHARS.set(i);
/*     */     }
/*  95 */     PRINTABLE_CHARS.set(123);
/*  96 */     PRINTABLE_CHARS.set(124);
/*  97 */     PRINTABLE_CHARS.set(125);
/*  98 */     PRINTABLE_CHARS.set(126);
/*     */   }
/*     */   
/* 101 */   private static byte BLANK = 32;
/*     */   
/* 103 */   private static byte UNDERSCORE = 95;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean encodeBlanks = false;
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
/*     */   public QCodec(String charset) {
/* 125 */     this.charset = charset;
/*     */   }
/*     */   
/*     */   protected String getEncoding() {
/* 129 */     return "Q";
/*     */   }
/*     */   
/*     */   protected byte[] doEncoding(byte[] bytes) throws EncoderException {
/* 133 */     if (bytes == null) {
/* 134 */       return null;
/*     */     }
/* 136 */     byte[] data = QuotedPrintableCodec.encodeQuotedPrintable(PRINTABLE_CHARS, bytes);
/* 137 */     if (this.encodeBlanks) {
/* 138 */       for (int i = 0; i < data.length; i++) {
/* 139 */         if (data[i] == BLANK) {
/* 140 */           data[i] = UNDERSCORE;
/*     */         }
/*     */       } 
/*     */     }
/* 144 */     return data;
/*     */   }
/*     */   
/*     */   protected byte[] doDecoding(byte[] bytes) throws DecoderException {
/* 148 */     if (bytes == null) {
/* 149 */       return null;
/*     */     }
/* 151 */     boolean hasUnderscores = false;
/* 152 */     for (int i = 0; i < bytes.length; i++) {
/* 153 */       if (bytes[i] == UNDERSCORE) {
/* 154 */         hasUnderscores = true;
/*     */         break;
/*     */       } 
/*     */     } 
/* 158 */     if (hasUnderscores) {
/* 159 */       byte[] tmp = new byte[bytes.length];
/* 160 */       for (int j = 0; j < bytes.length; j++) {
/* 161 */         byte b = bytes[j];
/* 162 */         if (b != UNDERSCORE) {
/* 163 */           tmp[j] = b;
/*     */         } else {
/* 165 */           tmp[j] = BLANK;
/*     */         } 
/*     */       } 
/* 168 */       return QuotedPrintableCodec.decodeQuotedPrintable(tmp);
/*     */     } 
/* 170 */     return QuotedPrintableCodec.decodeQuotedPrintable(bytes);
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
/*     */   public String encode(String pString, String charset) throws EncoderException {
/* 186 */     if (pString == null) {
/* 187 */       return null;
/*     */     }
/*     */     try {
/* 190 */       return encodeText(pString, charset);
/* 191 */     } catch (UnsupportedEncodingException e) {
/* 192 */       throw new EncoderException(e.getMessage());
/*     */     } 
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
/*     */   public String encode(String pString) throws EncoderException {
/* 207 */     if (pString == null) {
/* 208 */       return null;
/*     */     }
/* 210 */     return encode(pString, getDefaultCharset());
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
/*     */   public String decode(String pString) throws DecoderException {
/* 226 */     if (pString == null) {
/* 227 */       return null;
/*     */     }
/*     */     try {
/* 230 */       return decodeText(pString);
/* 231 */     } catch (UnsupportedEncodingException e) {
/* 232 */       throw new DecoderException(e.getMessage());
/*     */     } 
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
/*     */   public Object encode(Object pObject) throws EncoderException {
/* 247 */     if (pObject == null)
/* 248 */       return null; 
/* 249 */     if (pObject instanceof String) {
/* 250 */       return encode((String)pObject);
/*     */     }
/* 252 */     throw new EncoderException("Objects of type " + pObject.getClass().getName() + " cannot be encoded using Q codec");
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
/*     */   public Object decode(Object pObject) throws DecoderException {
/* 271 */     if (pObject == null)
/* 272 */       return null; 
/* 273 */     if (pObject instanceof String) {
/* 274 */       return decode((String)pObject);
/*     */     }
/* 276 */     throw new DecoderException("Objects of type " + pObject.getClass().getName() + " cannot be decoded using Q codec");
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
/*     */   public String getDefaultCharset() {
/* 288 */     return this.charset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEncodeBlanks() {
/* 297 */     return this.encodeBlanks;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEncodeBlanks(boolean b) {
/* 307 */     this.encodeBlanks = b;
/*     */   }
/*     */   
/*     */   public QCodec() {}
/*     */ }


/* Location:              D:\safari downloads\apache-commons-codec-1.3.jar!\org\apache\commons\codec\net\QCodec.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */