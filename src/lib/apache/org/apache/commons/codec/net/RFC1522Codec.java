/*     */ package lib.apache.org.apache.commons.codec.net;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import lib.apache.org.apache.commons.codec.DecoderException;
/*     */ import lib.apache.org.apache.commons.codec.EncoderException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class RFC1522Codec
/*     */ {
/*     */   protected String encodeText(String text, String charset) throws EncoderException, UnsupportedEncodingException {
/*  68 */     if (text == null) {
/*  69 */       return null;
/*     */     }
/*  71 */     StringBuffer buffer = new StringBuffer();
/*  72 */     buffer.append("=?");
/*  73 */     buffer.append(charset);
/*  74 */     buffer.append('?');
/*  75 */     buffer.append(getEncoding());
/*  76 */     buffer.append('?');
/*  77 */     byte[] rawdata = doEncoding(text.getBytes(charset));
/*  78 */     buffer.append(new String(rawdata, "US-ASCII"));
/*  79 */     buffer.append("?=");
/*  80 */     return buffer.toString();
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
/*     */   protected String decodeText(String text) throws DecoderException, UnsupportedEncodingException {
/*  98 */     if (text == null) {
/*  99 */       return null;
/*     */     }
/* 101 */     if (!text.startsWith("=?") || !text.endsWith("?=")) {
/* 102 */       throw new DecoderException("RFC 1522 violation: malformed encoded content");
/*     */     }
/* 104 */     int termnator = text.length() - 2;
/* 105 */     int from = 2;
/* 106 */     int to = text.indexOf("?", from);
/* 107 */     if (to == -1 || to == termnator) {
/* 108 */       throw new DecoderException("RFC 1522 violation: charset token not found");
/*     */     }
/* 110 */     String charset = text.substring(from, to);
/* 111 */     if (charset.equals("")) {
/* 112 */       throw new DecoderException("RFC 1522 violation: charset not specified");
/*     */     }
/* 114 */     from = to + 1;
/* 115 */     to = text.indexOf("?", from);
/* 116 */     if (to == -1 || to == termnator) {
/* 117 */       throw new DecoderException("RFC 1522 violation: encoding token not found");
/*     */     }
/* 119 */     String encoding = text.substring(from, to);
/* 120 */     if (!getEncoding().equalsIgnoreCase(encoding)) {
/* 121 */       throw new DecoderException("This codec cannot decode " + encoding + " encoded content");
/*     */     }
/*     */     
/* 124 */     from = to + 1;
/* 125 */     to = text.indexOf("?", from);
/* 126 */     byte[] data = text.substring(from, to).getBytes("US-ASCII");
/* 127 */     data = doDecoding(data);
/* 128 */     return new String(data, charset);
/*     */   }
/*     */   
/*     */   protected abstract String getEncoding();
/*     */   
/*     */   protected abstract byte[] doEncoding(byte[] paramArrayOfbyte) throws EncoderException;
/*     */   
/*     */   protected abstract byte[] doDecoding(byte[] paramArrayOfbyte) throws DecoderException;
/*     */ }


/* Location:              D:\safari downloads\apache-commons-codec-1.3.jar!\org\apache\commons\codec\net\RFC1522Codec.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */