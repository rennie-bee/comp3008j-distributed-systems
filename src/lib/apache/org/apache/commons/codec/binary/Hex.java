/*     */ package lib.apache.org.apache.commons.codec.binary;
/*     */ 
/*     */ import lib.apache.org.apache.commons.codec.BinaryDecoder;
/*     */ import lib.apache.org.apache.commons.codec.BinaryEncoder;
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
/*     */ public class Hex
/*     */   implements BinaryEncoder, BinaryDecoder
/*     */ {
/*  36 */   private static final char[] DIGITS = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] decodeHex(char[] data) throws DecoderException {
/*  56 */     int len = data.length;
/*     */     
/*  58 */     if ((len & 0x1) != 0) {
/*  59 */       throw new DecoderException("Odd number of characters.");
/*     */     }
/*     */     
/*  62 */     byte[] out = new byte[len >> 1];
/*     */ 
/*     */     
/*  65 */     for (int i = 0, j = 0; j < len; i++) {
/*  66 */       int f = toDigit(data[j], j) << 4;
/*  67 */       j++;
/*  68 */       f |= toDigit(data[j], j);
/*  69 */       j++;
/*  70 */       out[i] = (byte)(f & 0xFF);
/*     */     } 
/*     */     
/*  73 */     return out;
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
/*     */   protected static int toDigit(char ch, int index) throws DecoderException {
/*  85 */     int digit = Character.digit(ch, 16);
/*  86 */     if (digit == -1) {
/*  87 */       throw new DecoderException("Illegal hexadecimal charcter " + ch + " at index " + index);
/*     */     }
/*  89 */     return digit;
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
/*     */   public static char[] encodeHex(byte[] data) {
/* 103 */     int l = data.length;
/*     */     
/* 105 */     char[] out = new char[l << 1];
/*     */ 
/*     */     
/* 108 */     for (int i = 0, j = 0; i < l; i++) {
/* 109 */       out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
/* 110 */       out[j++] = DIGITS[0xF & data[i]];
/*     */     } 
/*     */     
/* 113 */     return out;
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
/*     */   public byte[] decode(byte[] array) throws DecoderException {
/* 131 */     return decodeHex((new String(array)).toCharArray());
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
/*     */   public Object decode(Object object) throws DecoderException {
/*     */     try {
/* 150 */       char[] charArray = (object instanceof String) ? ((String)object).toCharArray() : (char[])object;
/* 151 */       return decodeHex(charArray);
/* 152 */     } catch (ClassCastException e) {
/* 153 */       throw new DecoderException(e.getMessage());
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
/*     */   public byte[] encode(byte[] array) {
/* 168 */     return (new String(encodeHex(array))).getBytes();
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
/*     */   public Object encode(Object object) throws EncoderException {
/*     */     try {
/* 184 */       byte[] byteArray = (object instanceof String) ? ((String)object).getBytes() : (byte[])object;
/* 185 */       return encodeHex(byteArray);
/* 186 */     } catch (ClassCastException e) {
/* 187 */       throw new EncoderException(e.getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\safari downloads\apache-commons-codec-1.3.jar!\org\apache\commons\codec\binary\Hex.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */