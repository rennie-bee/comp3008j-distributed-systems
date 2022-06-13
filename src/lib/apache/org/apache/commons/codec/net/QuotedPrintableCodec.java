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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class QuotedPrintableCodec
/*     */   implements BinaryEncoder, BinaryDecoder, StringEncoder, StringDecoder
/*     */ {
/*  63 */   private String charset = "UTF-8";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   private static final BitSet PRINTABLE_CHARS = new BitSet(256);
/*     */   
/*  70 */   private static byte ESCAPE_CHAR = 61;
/*     */   
/*  72 */   private static byte TAB = 9;
/*     */   
/*  74 */   private static byte SPACE = 32;
/*     */   
/*     */   static {
/*     */     int i;
/*  78 */     for (i = 33; i <= 60; i++) {
/*  79 */       PRINTABLE_CHARS.set(i);
/*     */     }
/*  81 */     for (i = 62; i <= 126; i++) {
/*  82 */       PRINTABLE_CHARS.set(i);
/*     */     }
/*  84 */     PRINTABLE_CHARS.set(TAB);
/*  85 */     PRINTABLE_CHARS.set(SPACE);
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
/*     */   public QuotedPrintableCodec(String charset) {
/* 103 */     this.charset = charset;
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
/*     */   private static final void encodeQuotedPrintable(int b, ByteArrayOutputStream buffer) {
/* 115 */     buffer.write(ESCAPE_CHAR);
/* 116 */     char hex1 = Character.toUpperCase(Character.forDigit(b >> 4 & 0xF, 16));
/* 117 */     char hex2 = Character.toUpperCase(Character.forDigit(b & 0xF, 16));
/* 118 */     buffer.write(hex1);
/* 119 */     buffer.write(hex2);
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
/*     */   public static final byte[] encodeQuotedPrintable(BitSet printable, byte[] bytes) {
/* 137 */     if (bytes == null) {
/* 138 */       return null;
/*     */     }
/* 140 */     if (printable == null) {
/* 141 */       printable = PRINTABLE_CHARS;
/*     */     }
/* 143 */     ByteArrayOutputStream buffer = new ByteArrayOutputStream();
/* 144 */     for (int i = 0; i < bytes.length; i++) {
/* 145 */       int b = bytes[i];
/* 146 */       if (b < 0) {
/* 147 */         b = 256 + b;
/*     */       }
/* 149 */       if (printable.get(b)) {
/* 150 */         buffer.write(b);
/*     */       } else {
/* 152 */         encodeQuotedPrintable(b, buffer);
/*     */       } 
/*     */     } 
/* 155 */     return buffer.toByteArray();
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
/*     */   public static final byte[] decodeQuotedPrintable(byte[] bytes) throws DecoderException {
/* 174 */     if (bytes == null) {
/* 175 */       return null;
/*     */     }
/* 177 */     ByteArrayOutputStream buffer = new ByteArrayOutputStream();
/* 178 */     for (int i = 0; i < bytes.length; i++) {
/* 179 */       int b = bytes[i];
/* 180 */       if (b == ESCAPE_CHAR) {
/*     */         try {
/* 182 */           int u = Character.digit((char)bytes[++i], 16);
/* 183 */           int l = Character.digit((char)bytes[++i], 16);
/* 184 */           if (u == -1 || l == -1) {
/* 185 */             throw new DecoderException("Invalid quoted-printable encoding");
/*     */           }
/* 187 */           buffer.write((char)((u << 4) + l));
/* 188 */         } catch (ArrayIndexOutOfBoundsException e) {
/* 189 */           throw new DecoderException("Invalid quoted-printable encoding");
/*     */         } 
/*     */       } else {
/* 192 */         buffer.write(b);
/*     */       } 
/*     */     } 
/* 195 */     return buffer.toByteArray();
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
/*     */   public byte[] encode(byte[] bytes) {
/* 211 */     return encodeQuotedPrintable(PRINTABLE_CHARS, bytes);
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
/*     */   public byte[] decode(byte[] bytes) throws DecoderException {
/* 230 */     return decodeQuotedPrintable(bytes);
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
/*     */   public String encode(String pString) throws EncoderException {
/* 251 */     if (pString == null) {
/* 252 */       return null;
/*     */     }
/*     */     try {
/* 255 */       return encode(pString, getDefaultCharset());
/* 256 */     } catch (UnsupportedEncodingException e) {
/* 257 */       throw new EncoderException(e.getMessage());
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
/*     */   public String decode(String pString, String charset) throws DecoderException, UnsupportedEncodingException {
/* 276 */     if (pString == null) {
/* 277 */       return null;
/*     */     }
/* 279 */     return new String(decode(pString.getBytes("US-ASCII")), charset);
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
/*     */   public String decode(String pString) throws DecoderException {
/* 296 */     if (pString == null) {
/* 297 */       return null;
/*     */     }
/*     */     try {
/* 300 */       return decode(pString, getDefaultCharset());
/* 301 */     } catch (UnsupportedEncodingException e) {
/* 302 */       throw new DecoderException(e.getMessage());
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
/* 317 */     if (pObject == null)
/* 318 */       return null; 
/* 319 */     if (pObject instanceof byte[])
/* 320 */       return encode((byte[])pObject); 
/* 321 */     if (pObject instanceof String) {
/* 322 */       return encode((String)pObject);
/*     */     }
/* 324 */     throw new EncoderException("Objects of type " + pObject.getClass().getName() + " cannot be quoted-printable encoded");
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
/*     */   public Object decode(Object pObject) throws DecoderException {
/* 342 */     if (pObject == null)
/* 343 */       return null; 
/* 344 */     if (pObject instanceof byte[])
/* 345 */       return decode((byte[])pObject); 
/* 346 */     if (pObject instanceof String) {
/* 347 */       return decode((String)pObject);
/*     */     }
/* 349 */     throw new DecoderException("Objects of type " + pObject.getClass().getName() + " cannot be quoted-printable decoded");
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
/* 361 */     return this.charset;
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
/*     */   public String encode(String pString, String charset) throws UnsupportedEncodingException {
/* 382 */     if (pString == null) {
/* 383 */       return null;
/*     */     }
/* 385 */     return new String(encode(pString.getBytes(charset)), "US-ASCII");
/*     */   }
/*     */   
/*     */   public QuotedPrintableCodec() {}
/*     */ }


/* Location:              D:\safari downloads\apache-commons-codec-1.3.jar!\org\apache\commons\codec\net\QuotedPrintableCodec.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */