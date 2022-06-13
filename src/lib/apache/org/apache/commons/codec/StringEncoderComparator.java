/*    */ package lib.apache.org.apache.commons.codec;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StringEncoderComparator
/*    */   implements Comparator
/*    */ {
/*    */   private StringEncoder stringEncoder;
/*    */   
/*    */   public StringEncoderComparator() {}
/*    */   
/*    */   public StringEncoderComparator(StringEncoder stringEncoder) {
/* 52 */     this.stringEncoder = stringEncoder;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int compare(Object o1, Object o2) {
/* 70 */     int compareCode = 0;
/*    */     
/*    */     try {
/* 73 */       Comparable s1 = (Comparable)this.stringEncoder.encode(o1);
/* 74 */       Comparable s2 = (Comparable)this.stringEncoder.encode(o2);
/* 75 */       compareCode = s1.compareTo(s2);
/*    */     }
/* 77 */     catch (EncoderException ee) {
/* 78 */       compareCode = 0;
/*    */     } 
/* 80 */     return compareCode;
/*    */   }
/*    */ }


/* Location:              D:\safari downloads\apache-commons-codec-1.3.jar!\org\apache\commons\codec\StringEncoderComparator.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */