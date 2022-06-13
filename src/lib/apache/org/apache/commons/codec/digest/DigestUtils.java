/*     */ package lib.apache.org.apache.commons.codec.digest;
/*     */ 
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import lib.apache.org.apache.commons.codec.binary.Hex;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DigestUtils
/*     */ {
/*     */   static MessageDigest getDigest(String algorithm) {
/*     */     try {
/*  41 */       return MessageDigest.getInstance(algorithm);
/*  42 */     } catch (NoSuchAlgorithmException e) {
/*  43 */       throw new RuntimeException(e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static MessageDigest getMd5Digest() {
/*  54 */     return getDigest("MD5");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static MessageDigest getShaDigest() {
/*  64 */     return getDigest("SHA");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] md5(byte[] data) {
/*  75 */     return getMd5Digest().digest(data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] md5(String data) {
/*  86 */     return md5(data.getBytes());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String md5Hex(byte[] data) {
/*  97 */     return new String(Hex.encodeHex(md5(data)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String md5Hex(String data) {
/* 108 */     return new String(Hex.encodeHex(md5(data)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] sha(byte[] data) {
/* 119 */     return getShaDigest().digest(data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] sha(String data) {
/* 130 */     return sha(data.getBytes());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String shaHex(byte[] data) {
/* 140 */     return new String(Hex.encodeHex(sha(data)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String shaHex(String data) {
/* 150 */     return new String(Hex.encodeHex(sha(data)));
/*     */   }
/*     */ }


/* Location:              D:\safari downloads\apache-commons-codec-1.3.jar!\org\apache\commons\codec\digest\DigestUtils.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */