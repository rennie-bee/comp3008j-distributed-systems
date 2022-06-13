/*     */ package lib.apache.org.apache.commons.codec.net;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import lib.apache.org.apache.commons.codec.DecoderException;
/*     */ import lib.apache.org.apache.commons.codec.EncoderException;
/*     */ import lib.apache.org.apache.commons.codec.StringDecoder;
/*     */ import lib.apache.org.apache.commons.codec.StringEncoder;
/*     */ import lib.apache.org.apache.commons.codec.binary.Base64;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BCodec
/*     */   extends RFC1522Codec
/*     */   implements StringEncoder, StringDecoder
/*     */ {
/*  49 */   private String charset = "UTF-8";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BCodec() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BCodec(String charset) {
/*  69 */     this.charset = charset;
/*     */   }
/*     */   
/*     */   protected String getEncoding() {
/*  73 */     return "B";
/*     */   }
/*     */   
/*     */   protected byte[] doEncoding(byte[] bytes) throws EncoderException {
/*  77 */     if (bytes == null) {
/*  78 */       return null;
/*     */     }
/*  80 */     return Base64.encodeBase64(bytes);
/*     */   }
/*     */   
/*     */   protected byte[] doDecoding(byte[] bytes) throws DecoderException {
/*  84 */     if (bytes == null) {
/*  85 */       return null;
/*     */     }
/*  87 */     return Base64.decodeBase64(bytes);
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
/*     */   public String encode(String value, String charset) throws EncoderException {
/* 103 */     if (value == null) {
/* 104 */       return null;
/*     */     }
/*     */     try {
/* 107 */       return encodeText(value, charset);
/* 108 */     } catch (UnsupportedEncodingException e) {
/* 109 */       throw new EncoderException(e.getMessage());
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
/*     */   public String encode(String value) throws EncoderException {
/* 124 */     if (value == null) {
/* 125 */       return null;
/*     */     }
/* 127 */     return encode(value, getDefaultCharset());
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
/*     */   public String decode(String value) throws DecoderException {
/* 143 */     if (value == null) {
/* 144 */       return null;
/*     */     }
/*     */     try {
/* 147 */       return decodeText(value);
/* 148 */     } catch (UnsupportedEncodingException e) {
/* 149 */       throw new DecoderException(e.getMessage());
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
/*     */   public Object encode(Object value) throws EncoderException {
/* 164 */     if (value == null)
/* 165 */       return null; 
/* 166 */     if (value instanceof String) {
/* 167 */       return encode((String)value);
/*     */     }
/* 169 */     throw new EncoderException("Objects of type " + value.getClass().getName() + " cannot be encoded using BCodec");
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
/*     */   public Object decode(Object value) throws DecoderException {
/* 188 */     if (value == null)
/* 189 */       return null; 
/* 190 */     if (value instanceof String) {
/* 191 */       return decode((String)value);
/*     */     }
/* 193 */     throw new DecoderException("Objects of type " + value.getClass().getName() + " cannot be decoded using BCodec");
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
/* 205 */     return this.charset;
/*     */   }
/*     */ }


/* Location:              D:\safari downloads\apache-commons-codec-1.3.jar!\org\apache\commons\codec\net\BCodec.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */