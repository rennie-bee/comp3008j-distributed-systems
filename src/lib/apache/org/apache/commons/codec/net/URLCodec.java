/*     */ package lib.apache.org.apache.commons.codec.net;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.BitSet;
/*     */ import lib.apache.org.apache.commons.codec.BinaryDecoder;
/*     */ import lib.apache.org.apache.commons.codec.BinaryEncoder;
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
/*     */ 
/*     */ 
/*     */ public class URLCodec
/*     */   implements BinaryEncoder, BinaryDecoder, StringEncoder, StringDecoder
/*     */ {
/*  55 */   protected String charset = "UTF-8";
/*     */   
/*  57 */   protected static byte ESCAPE_CHAR = 37;
/*     */ 
/*     */ 
/*     */   
/*  61 */   protected static final BitSet WWW_FORM_URL = new BitSet(256);
/*     */ 
/*     */   
/*     */   static {
/*     */     int i;
/*  66 */     for (i = 97; i <= 122; i++) {
/*  67 */       WWW_FORM_URL.set(i);
/*     */     }
/*  69 */     for (i = 65; i <= 90; i++) {
/*  70 */       WWW_FORM_URL.set(i);
/*     */     }
/*     */     
/*  73 */     for (i = 48; i <= 57; i++) {
/*  74 */       WWW_FORM_URL.set(i);
/*     */     }
/*     */     
/*  77 */     WWW_FORM_URL.set(45);
/*  78 */     WWW_FORM_URL.set(95);
/*  79 */     WWW_FORM_URL.set(46);
/*  80 */     WWW_FORM_URL.set(42);
/*     */     
/*  82 */     WWW_FORM_URL.set(32);
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
/*     */   public URLCodec(String charset) {
/* 100 */     this.charset = charset;
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
/*     */   public static final byte[] encodeUrl(BitSet urlsafe, byte[] bytes) {
/* 113 */     if (bytes == null) {
/* 114 */       return null;
/*     */     }
/* 116 */     if (urlsafe == null) {
/* 117 */       urlsafe = WWW_FORM_URL;
/*     */     }
/*     */     
/* 120 */     ByteArrayOutputStream buffer = new ByteArrayOutputStream();
/* 121 */     for (int i = 0; i < bytes.length; i++) {
/* 122 */       int b = bytes[i];
/* 123 */       if (b < 0) {
/* 124 */         b = 256 + b;
/*     */       }
/* 126 */       if (urlsafe.get(b)) {
/* 127 */         if (b == 32) {
/* 128 */           b = 43;
/*     */         }
/* 130 */         buffer.write(b);
/*     */       } else {
/* 132 */         buffer.write(37);
/* 133 */         char hex1 = Character.toUpperCase(Character.forDigit(b >> 4 & 0xF, 16));
/*     */         
/* 135 */         char hex2 = Character.toUpperCase(Character.forDigit(b & 0xF, 16));
/*     */         
/* 137 */         buffer.write(hex1);
/* 138 */         buffer.write(hex2);
/*     */       } 
/*     */     } 
/* 141 */     return buffer.toByteArray();
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
/*     */   public static final byte[] decodeUrl(byte[] bytes) throws DecoderException {
/* 157 */     if (bytes == null) {
/* 158 */       return null;
/*     */     }
/* 160 */     ByteArrayOutputStream buffer = new ByteArrayOutputStream();
/* 161 */     for (int i = 0; i < bytes.length; i++) {
/* 162 */       int b = bytes[i];
/* 163 */       if (b == 43) {
/* 164 */         buffer.write(32);
/* 165 */       } else if (b == 37) {
/*     */         try {
/* 167 */           int u = Character.digit((char)bytes[++i], 16);
/* 168 */           int l = Character.digit((char)bytes[++i], 16);
/* 169 */           if (u == -1 || l == -1) {
/* 170 */             throw new DecoderException("Invalid URL encoding");
/*     */           }
/* 172 */           buffer.write((char)((u << 4) + l));
/* 173 */         } catch (ArrayIndexOutOfBoundsException e) {
/* 174 */           throw new DecoderException("Invalid URL encoding");
/*     */         } 
/*     */       } else {
/* 177 */         buffer.write(b);
/*     */       } 
/*     */     } 
/* 180 */     return buffer.toByteArray();
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
/*     */   public byte[] encode(byte[] bytes) {
/* 192 */     return encodeUrl(WWW_FORM_URL, bytes);
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
/*     */   public byte[] decode(byte[] bytes) throws DecoderException {
/* 206 */     return decodeUrl(bytes);
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
/*     */   public String encode(String pString, String charset) throws UnsupportedEncodingException {
/* 223 */     if (pString == null) {
/* 224 */       return null;
/*     */     }
/* 226 */     return new String(encode(pString.getBytes(charset)), "US-ASCII");
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
/*     */   public String encode(String pString) throws EncoderException {
/* 241 */     if (pString == null) {
/* 242 */       return null;
/*     */     }
/*     */     try {
/* 245 */       return encode(pString, getDefaultCharset());
/* 246 */     } catch (UnsupportedEncodingException e) {
/* 247 */       throw new EncoderException(e.getMessage());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String decode(String pString, String charset) throws DecoderException, UnsupportedEncodingException {
/* 267 */     if (pString == null) {
/* 268 */       return null;
/*     */     }
/* 270 */     return new String(decode(pString.getBytes("US-ASCII")), charset);
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
/* 286 */     if (pString == null) {
/* 287 */       return null;
/*     */     }
/*     */     try {
/* 290 */       return decode(pString, getDefaultCharset());
/* 291 */     } catch (UnsupportedEncodingException e) {
/* 292 */       throw new DecoderException(e.getMessage());
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
/* 307 */     if (pObject == null)
/* 308 */       return null; 
/* 309 */     if (pObject instanceof byte[])
/* 310 */       return encode((byte[])pObject); 
/* 311 */     if (pObject instanceof String) {
/* 312 */       return encode((String)pObject);
/*     */     }
/* 314 */     throw new EncoderException("Objects of type " + pObject.getClass().getName() + " cannot be URL encoded");
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
/*     */   public Object decode(Object pObject) throws DecoderException {
/* 331 */     if (pObject == null)
/* 332 */       return null; 
/* 333 */     if (pObject instanceof byte[])
/* 334 */       return decode((byte[])pObject); 
/* 335 */     if (pObject instanceof String) {
/* 336 */       return decode((String)pObject);
/*     */     }
/* 338 */     throw new DecoderException("Objects of type " + pObject.getClass().getName() + " cannot be URL decoded");
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
/*     */   public String getEncoding() {
/* 352 */     return this.charset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDefaultCharset() {
/* 361 */     return this.charset;
/*     */   }
/*     */   
/*     */   public URLCodec() {}
/*     */ }


/* Location:              D:\safari downloads\apache-commons-codec-1.3.jar!\org\apache\commons\codec\net\URLCodec.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */