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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Base64
/*     */   implements BinaryEncoder, BinaryDecoder
/*     */ {
/*     */   static final int CHUNK_SIZE = 76;
/*  53 */   static final byte[] CHUNK_SEPARATOR = "\r\n".getBytes();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int BASELENGTH = 255;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int LOOKUPLENGTH = 64;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int EIGHTBIT = 8;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int SIXTEENBIT = 16;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int TWENTYFOURBITGROUP = 24;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int FOURBYTE = 4;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int SIGN = -128;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final byte PAD = 61;
/*     */ 
/*     */ 
/*     */   
/*  97 */   private static byte[] base64Alphabet = new byte[255];
/*  98 */   private static byte[] lookUpBase64Alphabet = new byte[64];
/*     */   
/*     */   static {
/*     */     int i;
/* 102 */     for (i = 0; i < 255; i++) {
/* 103 */       base64Alphabet[i] = -1;
/*     */     }
/* 105 */     for (i = 90; i >= 65; i--) {
/* 106 */       base64Alphabet[i] = (byte)(i - 65);
/*     */     }
/* 108 */     for (i = 122; i >= 97; i--) {
/* 109 */       base64Alphabet[i] = (byte)(i - 97 + 26);
/*     */     }
/* 111 */     for (i = 57; i >= 48; i--) {
/* 112 */       base64Alphabet[i] = (byte)(i - 48 + 52);
/*     */     }
/*     */     
/* 115 */     base64Alphabet[43] = 62;
/* 116 */     base64Alphabet[47] = 63;
/*     */     
/* 118 */     for (i = 0; i <= 25; i++) {
/* 119 */       lookUpBase64Alphabet[i] = (byte)(65 + i);
/*     */     }
/*     */     int j;
/* 122 */     for (i = 26, j = 0; i <= 51; i++, j++) {
/* 123 */       lookUpBase64Alphabet[i] = (byte)(97 + j);
/*     */     }
/*     */     
/* 126 */     for (i = 52, j = 0; i <= 61; i++, j++) {
/* 127 */       lookUpBase64Alphabet[i] = (byte)(48 + j);
/*     */     }
/*     */     
/* 130 */     lookUpBase64Alphabet[62] = 43;
/* 131 */     lookUpBase64Alphabet[63] = 47;
/*     */   }
/*     */   
/*     */   private static boolean isBase64(byte octect) {
/* 135 */     if (octect == 61)
/* 136 */       return true; 
/* 137 */     if (base64Alphabet[octect] == -1) {
/* 138 */       return false;
/*     */     }
/* 140 */     return true;
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
/*     */   public static boolean isArrayByteBase64(byte[] arrayOctect) {
/* 154 */     arrayOctect = discardWhitespace(arrayOctect);
/*     */     
/* 156 */     int length = arrayOctect.length;
/* 157 */     if (length == 0)
/*     */     {
/*     */       
/* 160 */       return true;
/*     */     }
/* 162 */     for (int i = 0; i < length; i++) {
/* 163 */       if (!isBase64(arrayOctect[i])) {
/* 164 */         return false;
/*     */       }
/*     */     } 
/* 167 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] encodeBase64(byte[] binaryData) {
/* 178 */     return encodeBase64(binaryData, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] encodeBase64Chunked(byte[] binaryData) {
/* 189 */     return encodeBase64(binaryData, true);
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
/* 206 */     if (!(pObject instanceof byte[])) {
/* 207 */       throw new DecoderException("Parameter supplied to Base64 decode is not a byte[]");
/*     */     }
/* 209 */     return decode((byte[])pObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] decode(byte[] pArray) {
/* 220 */     return decodeBase64(pArray);
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
/*     */   public static byte[] encodeBase64(byte[] binaryData, boolean isChunked) {
/* 233 */     int lengthDataBits = binaryData.length * 8;
/* 234 */     int fewerThan24bits = lengthDataBits % 24;
/* 235 */     int numberTriplets = lengthDataBits / 24;
/* 236 */     byte[] encodedData = null;
/* 237 */     int encodedDataLength = 0;
/* 238 */     int nbrChunks = 0;
/*     */     
/* 240 */     if (fewerThan24bits != 0) {
/*     */       
/* 242 */       encodedDataLength = (numberTriplets + 1) * 4;
/*     */     } else {
/*     */       
/* 245 */       encodedDataLength = numberTriplets * 4;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 251 */     if (isChunked) {
/*     */       
/* 253 */       nbrChunks = (CHUNK_SEPARATOR.length == 0) ? 0 : (int)Math.ceil((encodedDataLength / 76.0F));
/*     */       
/* 255 */       encodedDataLength += nbrChunks * CHUNK_SEPARATOR.length;
/*     */     } 
/*     */     
/* 258 */     encodedData = new byte[encodedDataLength];
/*     */     
/* 260 */     byte k = 0, l = 0, b1 = 0, b2 = 0, b3 = 0;
/*     */     
/* 262 */     int encodedIndex = 0;
/* 263 */     int dataIndex = 0;
/* 264 */     int i = 0;
/* 265 */     int nextSeparatorIndex = 76;
/* 266 */     int chunksSoFar = 0;
/*     */ 
/*     */     
/* 269 */     for (i = 0; i < numberTriplets; i++) {
/* 270 */       dataIndex = i * 3;
/* 271 */       b1 = binaryData[dataIndex];
/* 272 */       b2 = binaryData[dataIndex + 1];
/* 273 */       b3 = binaryData[dataIndex + 2];
/*     */ 
/*     */ 
/*     */       
/* 277 */       l = (byte)(b2 & 0xF);
/* 278 */       k = (byte)(b1 & 0x3);
/*     */       
/* 280 */       byte val1 = ((b1 & Byte.MIN_VALUE) == 0) ? (byte)(b1 >> 2) : (byte)(b1 >> 2 ^ 0xC0);
/*     */       
/* 282 */       byte val2 = ((b2 & Byte.MIN_VALUE) == 0) ? (byte)(b2 >> 4) : (byte)(b2 >> 4 ^ 0xF0);
/*     */       
/* 284 */       byte val3 = ((b3 & Byte.MIN_VALUE) == 0) ? (byte)(b3 >> 6) : (byte)(b3 >> 6 ^ 0xFC);
/*     */ 
/*     */       
/* 287 */       encodedData[encodedIndex] = lookUpBase64Alphabet[val1];
/*     */ 
/*     */ 
/*     */       
/* 291 */       encodedData[encodedIndex + 1] = lookUpBase64Alphabet[val2 | k << 4];
/*     */       
/* 293 */       encodedData[encodedIndex + 2] = lookUpBase64Alphabet[l << 2 | val3];
/*     */       
/* 295 */       encodedData[encodedIndex + 3] = lookUpBase64Alphabet[b3 & 0x3F];
/*     */       
/* 297 */       encodedIndex += 4;
/*     */ 
/*     */       
/* 300 */       if (isChunked)
/*     */       {
/* 302 */         if (encodedIndex == nextSeparatorIndex) {
/* 303 */           System.arraycopy(CHUNK_SEPARATOR, 0, encodedData, encodedIndex, CHUNK_SEPARATOR.length);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 309 */           chunksSoFar++;
/* 310 */           nextSeparatorIndex = 76 * (chunksSoFar + 1) + chunksSoFar * CHUNK_SEPARATOR.length;
/*     */ 
/*     */           
/* 313 */           encodedIndex += CHUNK_SEPARATOR.length;
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 319 */     dataIndex = i * 3;
/*     */     
/* 321 */     if (fewerThan24bits == 8) {
/* 322 */       b1 = binaryData[dataIndex];
/* 323 */       k = (byte)(b1 & 0x3);
/*     */ 
/*     */       
/* 326 */       byte val1 = ((b1 & Byte.MIN_VALUE) == 0) ? (byte)(b1 >> 2) : (byte)(b1 >> 2 ^ 0xC0);
/*     */       
/* 328 */       encodedData[encodedIndex] = lookUpBase64Alphabet[val1];
/* 329 */       encodedData[encodedIndex + 1] = lookUpBase64Alphabet[k << 4];
/* 330 */       encodedData[encodedIndex + 2] = 61;
/* 331 */       encodedData[encodedIndex + 3] = 61;
/* 332 */     } else if (fewerThan24bits == 16) {
/*     */       
/* 334 */       b1 = binaryData[dataIndex];
/* 335 */       b2 = binaryData[dataIndex + 1];
/* 336 */       l = (byte)(b2 & 0xF);
/* 337 */       k = (byte)(b1 & 0x3);
/*     */       
/* 339 */       byte val1 = ((b1 & Byte.MIN_VALUE) == 0) ? (byte)(b1 >> 2) : (byte)(b1 >> 2 ^ 0xC0);
/*     */       
/* 341 */       byte val2 = ((b2 & Byte.MIN_VALUE) == 0) ? (byte)(b2 >> 4) : (byte)(b2 >> 4 ^ 0xF0);
/*     */ 
/*     */       
/* 344 */       encodedData[encodedIndex] = lookUpBase64Alphabet[val1];
/* 345 */       encodedData[encodedIndex + 1] = lookUpBase64Alphabet[val2 | k << 4];
/*     */       
/* 347 */       encodedData[encodedIndex + 2] = lookUpBase64Alphabet[l << 2];
/* 348 */       encodedData[encodedIndex + 3] = 61;
/*     */     } 
/*     */     
/* 351 */     if (isChunked)
/*     */     {
/* 353 */       if (chunksSoFar < nbrChunks) {
/* 354 */         System.arraycopy(CHUNK_SEPARATOR, 0, encodedData, encodedDataLength - CHUNK_SEPARATOR.length, CHUNK_SEPARATOR.length);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 363 */     return encodedData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] decodeBase64(byte[] base64Data) {
/* 374 */     base64Data = discardNonBase64(base64Data);
/*     */ 
/*     */     
/* 377 */     if (base64Data.length == 0) {
/* 378 */       return new byte[0];
/*     */     }
/*     */     
/* 381 */     int numberQuadruple = base64Data.length / 4;
/* 382 */     byte[] decodedData = null;
/* 383 */     byte b1 = 0, b2 = 0, b3 = 0, b4 = 0, marker0 = 0, marker1 = 0;
/*     */ 
/*     */ 
/*     */     
/* 387 */     int encodedIndex = 0;
/* 388 */     int dataIndex = 0;
/*     */ 
/*     */     
/* 391 */     int lastData = base64Data.length;
/*     */     
/* 393 */     while (base64Data[lastData - 1] == 61) {
/* 394 */       if (--lastData == 0) {
/* 395 */         return new byte[0];
/*     */       }
/*     */     } 
/* 398 */     decodedData = new byte[lastData - numberQuadruple];
/*     */ 
/*     */     
/* 401 */     for (int i = 0; i < numberQuadruple; i++) {
/* 402 */       dataIndex = i * 4;
/* 403 */       marker0 = base64Data[dataIndex + 2];
/* 404 */       marker1 = base64Data[dataIndex + 3];
/*     */       
/* 406 */       b1 = base64Alphabet[base64Data[dataIndex]];
/* 407 */       b2 = base64Alphabet[base64Data[dataIndex + 1]];
/*     */       
/* 409 */       if (marker0 != 61 && marker1 != 61) {
/*     */         
/* 411 */         b3 = base64Alphabet[marker0];
/* 412 */         b4 = base64Alphabet[marker1];
/*     */         
/* 414 */         decodedData[encodedIndex] = (byte)(b1 << 2 | b2 >> 4);
/* 415 */         decodedData[encodedIndex + 1] = (byte)((b2 & 0xF) << 4 | b3 >> 2 & 0xF);
/*     */         
/* 417 */         decodedData[encodedIndex + 2] = (byte)(b3 << 6 | b4);
/* 418 */       } else if (marker0 == 61) {
/*     */         
/* 420 */         decodedData[encodedIndex] = (byte)(b1 << 2 | b2 >> 4);
/* 421 */       } else if (marker1 == 61) {
/*     */         
/* 423 */         b3 = base64Alphabet[marker0];
/*     */         
/* 425 */         decodedData[encodedIndex] = (byte)(b1 << 2 | b2 >> 4);
/* 426 */         decodedData[encodedIndex + 1] = (byte)((b2 & 0xF) << 4 | b3 >> 2 & 0xF);
/*     */       } 
/*     */       
/* 429 */       encodedIndex += 3;
/*     */     } 
/* 431 */     return decodedData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static byte[] discardWhitespace(byte[] data) {
/* 442 */     byte[] groomedData = new byte[data.length];
/* 443 */     int bytesCopied = 0;
/*     */     
/* 445 */     for (int i = 0; i < data.length; i++) {
/* 446 */       switch (data[i]) {
/*     */         case 9:
/*     */         case 10:
/*     */         case 13:
/*     */         case 32:
/*     */           break;
/*     */         default:
/* 453 */           groomedData[bytesCopied++] = data[i];
/*     */           break;
/*     */       } 
/*     */     } 
/* 457 */     byte[] packedData = new byte[bytesCopied];
/*     */     
/* 459 */     System.arraycopy(groomedData, 0, packedData, 0, bytesCopied);
/*     */     
/* 461 */     return packedData;
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
/*     */   static byte[] discardNonBase64(byte[] data) {
/* 474 */     byte[] groomedData = new byte[data.length];
/* 475 */     int bytesCopied = 0;
/*     */     
/* 477 */     for (int i = 0; i < data.length; i++) {
/* 478 */       if (isBase64(data[i])) {
/* 479 */         groomedData[bytesCopied++] = data[i];
/*     */       }
/*     */     } 
/*     */     
/* 483 */     byte[] packedData = new byte[bytesCopied];
/*     */     
/* 485 */     System.arraycopy(groomedData, 0, packedData, 0, bytesCopied);
/*     */     
/* 487 */     return packedData;
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
/*     */   public Object encode(Object pObject) throws EncoderException {
/* 506 */     if (!(pObject instanceof byte[])) {
/* 507 */       throw new EncoderException("Parameter supplied to Base64 encode is not a byte[]");
/*     */     }
/*     */     
/* 510 */     return encode((byte[])pObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encode(byte[] pArray) {
/* 521 */     return encodeBase64(pArray, false);
/*     */   }
/*     */ }


/* Location:              D:\safari downloads\apache-commons-codec-1.3.jar!\org\apache\commons\codec\binary\Base64.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */