/*      */ package lib.apache.org.apache.commons.codec.language;
/*      */ 
/*      */ import lib.apache.org.apache.commons.codec.EncoderException;
/*      */ import lib.apache.org.apache.commons.codec.StringEncoder;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DoubleMetaphone
/*      */   implements StringEncoder
/*      */ {
/*      */   private static final String VOWELS = "AEIOUY";
/*   46 */   private static final String[] SILENT_START = new String[] { "GN", "KN", "PN", "WR", "PS" };
/*      */   
/*   48 */   private static final String[] L_R_N_M_B_H_F_V_W_SPACE = new String[] { "L", "R", "N", "M", "B", "H", "F", "V", "W", " " };
/*      */   
/*   50 */   private static final String[] ES_EP_EB_EL_EY_IB_IL_IN_IE_EI_ER = new String[] { "ES", "EP", "EB", "EL", "EY", "IB", "IL", "IN", "IE", "EI", "ER" };
/*      */   
/*   52 */   private static final String[] L_T_K_S_N_M_B_Z = new String[] { "L", "T", "K", "S", "N", "M", "B", "Z" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   58 */   protected int maxCodeLen = 4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String doubleMetaphone(String value) {
/*   74 */     return doubleMetaphone(value, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String doubleMetaphone(String value, boolean alternate) {
/*   86 */     value = cleanInput(value);
/*   87 */     if (value == null) {
/*   88 */       return null;
/*      */     }
/*      */     
/*   91 */     boolean slavoGermanic = isSlavoGermanic(value);
/*   92 */     int index = isSilentStart(value) ? 1 : 0;
/*      */     
/*   94 */     DoubleMetaphoneResult result = new DoubleMetaphoneResult(this, getMaxCodeLen());
/*      */     
/*   96 */     while (!result.isComplete() && index <= value.length() - 1) {
/*   97 */       switch (value.charAt(index)) {
/*      */         case 'A':
/*      */         case 'E':
/*      */         case 'I':
/*      */         case 'O':
/*      */         case 'U':
/*      */         case 'Y':
/*  104 */           index = handleAEIOUY(value, result, index);
/*      */           continue;
/*      */         case 'B':
/*  107 */           result.append('P');
/*  108 */           index = (charAt(value, index + 1) == 'B') ? (index + 2) : (index + 1);
/*      */           continue;
/*      */         
/*      */         case 'Ç':
/*  112 */           result.append('S');
/*  113 */           index++;
/*      */           continue;
/*      */         case 'C':
/*  116 */           index = handleC(value, result, index);
/*      */           continue;
/*      */         case 'D':
/*  119 */           index = handleD(value, result, index);
/*      */           continue;
/*      */         case 'F':
/*  122 */           result.append('F');
/*  123 */           index = (charAt(value, index + 1) == 'F') ? (index + 2) : (index + 1);
/*      */           continue;
/*      */         case 'G':
/*  126 */           index = handleG(value, result, index, slavoGermanic);
/*      */           continue;
/*      */         case 'H':
/*  129 */           index = handleH(value, result, index);
/*      */           continue;
/*      */         case 'J':
/*  132 */           index = handleJ(value, result, index, slavoGermanic);
/*      */           continue;
/*      */         case 'K':
/*  135 */           result.append('K');
/*  136 */           index = (charAt(value, index + 1) == 'K') ? (index + 2) : (index + 1);
/*      */           continue;
/*      */         case 'L':
/*  139 */           index = handleL(value, result, index);
/*      */           continue;
/*      */         case 'M':
/*  142 */           result.append('M');
/*  143 */           index = conditionM0(value, index) ? (index + 2) : (index + 1);
/*      */           continue;
/*      */         case 'N':
/*  146 */           result.append('N');
/*  147 */           index = (charAt(value, index + 1) == 'N') ? (index + 2) : (index + 1);
/*      */           continue;
/*      */         
/*      */         case 'Ñ':
/*  151 */           result.append('N');
/*  152 */           index++;
/*      */           continue;
/*      */         case 'P':
/*  155 */           index = handleP(value, result, index);
/*      */           continue;
/*      */         case 'Q':
/*  158 */           result.append('K');
/*  159 */           index = (charAt(value, index + 1) == 'Q') ? (index + 2) : (index + 1);
/*      */           continue;
/*      */         case 'R':
/*  162 */           index = handleR(value, result, index, slavoGermanic);
/*      */           continue;
/*      */         case 'S':
/*  165 */           index = handleS(value, result, index, slavoGermanic);
/*      */           continue;
/*      */         case 'T':
/*  168 */           index = handleT(value, result, index);
/*      */           continue;
/*      */         case 'V':
/*  171 */           result.append('F');
/*  172 */           index = (charAt(value, index + 1) == 'V') ? (index + 2) : (index + 1);
/*      */           continue;
/*      */         case 'W':
/*  175 */           index = handleW(value, result, index);
/*      */           continue;
/*      */         case 'X':
/*  178 */           index = handleX(value, result, index);
/*      */           continue;
/*      */         case 'Z':
/*  181 */           index = handleZ(value, result, index, slavoGermanic);
/*      */           continue;
/*      */       } 
/*  184 */       index++;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  189 */     return alternate ? result.getAlternate() : result.getPrimary();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object encode(Object obj) throws EncoderException {
/*  201 */     if (!(obj instanceof String)) {
/*  202 */       throw new EncoderException("DoubleMetaphone encode parameter is not of type String");
/*      */     }
/*  204 */     return doubleMetaphone((String)obj);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String encode(String value) {
/*  214 */     return doubleMetaphone(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDoubleMetaphoneEqual(String value1, String value2) {
/*  228 */     return isDoubleMetaphoneEqual(value1, value2, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDoubleMetaphoneEqual(String value1, String value2, boolean alternate) {
/*  244 */     return doubleMetaphone(value1, alternate).equals(doubleMetaphone(value2, alternate));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxCodeLen() {
/*  253 */     return this.maxCodeLen;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaxCodeLen(int maxCodeLen) {
/*  261 */     this.maxCodeLen = maxCodeLen;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int handleAEIOUY(String value, DoubleMetaphoneResult result, int index) {
/*  271 */     if (index == 0) {
/*  272 */       result.append('A');
/*      */     }
/*  274 */     return index + 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int handleC(String value, DoubleMetaphoneResult result, int index) {
/*  283 */     if (conditionC0(value, index))
/*  284 */     { result.append('K');
/*  285 */       index += 2; }
/*  286 */     else if (index == 0 && contains(value, index, 6, "CAESAR"))
/*  287 */     { result.append('S');
/*  288 */       index += 2; }
/*  289 */     else if (contains(value, index, 2, "CH"))
/*  290 */     { index = handleCH(value, result, index); }
/*  291 */     else if (contains(value, index, 2, "CZ") && !contains(value, index - 2, 4, "WICZ"))
/*      */     
/*      */     { 
/*  294 */       result.append('S', 'X');
/*  295 */       index += 2; }
/*  296 */     else if (contains(value, index + 1, 3, "CIA"))
/*      */     
/*  298 */     { result.append('X');
/*  299 */       index += 3; }
/*  300 */     else { if (contains(value, index, 2, "CC") && (index != 1 || charAt(value, 0) != 'M'))
/*      */       {
/*      */         
/*  303 */         return handleCC(value, result, index); } 
/*  304 */       if (contains(value, index, 2, "CK", "CG", "CQ")) {
/*  305 */         result.append('K');
/*  306 */         index += 2;
/*  307 */       } else if (contains(value, index, 2, "CI", "CE", "CY")) {
/*      */         
/*  309 */         if (contains(value, index, 3, "CIO", "CIE", "CIA")) {
/*  310 */           result.append('S', 'X');
/*      */         } else {
/*  312 */           result.append('S');
/*      */         } 
/*  314 */         index += 2;
/*      */       } else {
/*  316 */         result.append('K');
/*  317 */         if (contains(value, index + 1, 2, " C", " Q", " G")) {
/*      */           
/*  319 */           index += 3;
/*  320 */         } else if (contains(value, index + 1, 1, "C", "K", "Q") && !contains(value, index + 1, 2, "CE", "CI")) {
/*      */           
/*  322 */           index += 2;
/*      */         } else {
/*  324 */           index++;
/*      */         } 
/*      */       }  }
/*      */     
/*  328 */     return index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int handleCC(String value, DoubleMetaphoneResult result, int index) {
/*  337 */     if (contains(value, index + 2, 1, "I", "E", "H") && !contains(value, index + 2, 2, "HU")) {
/*      */ 
/*      */       
/*  340 */       if ((index == 1 && charAt(value, index - 1) == 'A') || contains(value, index - 1, 5, "UCCEE", "UCCES")) {
/*      */ 
/*      */         
/*  343 */         result.append("KS");
/*      */       } else {
/*      */         
/*  346 */         result.append('X');
/*      */       } 
/*  348 */       index += 3;
/*      */     } else {
/*  350 */       result.append('K');
/*  351 */       index += 2;
/*      */     } 
/*      */     
/*  354 */     return index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int handleCH(String value, DoubleMetaphoneResult result, int index) {
/*  363 */     if (index > 0 && contains(value, index, 4, "CHAE")) {
/*  364 */       result.append('K', 'X');
/*  365 */       return index + 2;
/*  366 */     }  if (conditionCH0(value, index)) {
/*      */       
/*  368 */       result.append('K');
/*  369 */       return index + 2;
/*  370 */     }  if (conditionCH1(value, index)) {
/*      */       
/*  372 */       result.append('K');
/*  373 */       return index + 2;
/*      */     } 
/*  375 */     if (index > 0) {
/*  376 */       if (contains(value, 0, 2, "MC")) {
/*  377 */         result.append('K');
/*      */       } else {
/*  379 */         result.append('X', 'K');
/*      */       } 
/*      */     } else {
/*  382 */       result.append('X');
/*      */     } 
/*  384 */     return index + 2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int handleD(String value, DoubleMetaphoneResult result, int index) {
/*  394 */     if (contains(value, index, 2, "DG")) {
/*      */       
/*  396 */       if (contains(value, index + 2, 1, "I", "E", "Y")) {
/*  397 */         result.append('J');
/*  398 */         index += 3;
/*      */       } else {
/*      */         
/*  401 */         result.append("TK");
/*  402 */         index += 2;
/*      */       } 
/*  404 */     } else if (contains(value, index, 2, "DT", "DD")) {
/*  405 */       result.append('T');
/*  406 */       index += 2;
/*      */     } else {
/*  408 */       result.append('T');
/*  409 */       index++;
/*      */     } 
/*  411 */     return index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int handleG(String value, DoubleMetaphoneResult result, int index, boolean slavoGermanic) {
/*  421 */     if (charAt(value, index + 1) == 'H') {
/*  422 */       index = handleGH(value, result, index);
/*  423 */     } else if (charAt(value, index + 1) == 'N') {
/*  424 */       if (index == 1 && isVowel(charAt(value, 0)) && !slavoGermanic) {
/*  425 */         result.append("KN", "N");
/*  426 */       } else if (!contains(value, index + 2, 2, "EY") && charAt(value, index + 1) != 'Y' && !slavoGermanic) {
/*      */         
/*  428 */         result.append("N", "KN");
/*      */       } else {
/*  430 */         result.append("KN");
/*      */       } 
/*  432 */       index += 2;
/*  433 */     } else if (contains(value, index + 1, 2, "LI") && !slavoGermanic) {
/*  434 */       result.append("KL", "L");
/*  435 */       index += 2;
/*  436 */     } else if (index == 0 && (charAt(value, index + 1) == 'Y' || contains(value, index + 1, 2, ES_EP_EB_EL_EY_IB_IL_IN_IE_EI_ER))) {
/*      */       
/*  438 */       result.append('K', 'J');
/*  439 */       index += 2;
/*  440 */     } else if ((contains(value, index + 1, 2, "ER") || charAt(value, index + 1) == 'Y') && !contains(value, 0, 6, "DANGER", "RANGER", "MANGER") && !contains(value, index - 1, 1, "E", "I") && !contains(value, index - 1, 3, "RGY", "OGY")) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  446 */       result.append('K', 'J');
/*  447 */       index += 2;
/*  448 */     } else if (contains(value, index + 1, 1, "E", "I", "Y") || contains(value, index - 1, 4, "AGGI", "OGGI")) {
/*      */ 
/*      */       
/*  451 */       if (contains(value, 0, 4, "VAN ", "VON ") || contains(value, 0, 3, "SCH") || contains(value, index + 1, 2, "ET")) {
/*      */         
/*  453 */         result.append('K');
/*  454 */       } else if (contains(value, index + 1, 4, "IER")) {
/*  455 */         result.append('J');
/*      */       } else {
/*  457 */         result.append('J', 'K');
/*      */       } 
/*  459 */       index += 2;
/*  460 */     } else if (charAt(value, index + 1) == 'G') {
/*  461 */       index += 2;
/*  462 */       result.append('K');
/*      */     } else {
/*  464 */       index++;
/*  465 */       result.append('K');
/*      */     } 
/*  467 */     return index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int handleGH(String value, DoubleMetaphoneResult result, int index) {
/*  476 */     if (index > 0 && !isVowel(charAt(value, index - 1))) {
/*  477 */       result.append('K');
/*  478 */       index += 2;
/*  479 */     } else if (index == 0) {
/*  480 */       if (charAt(value, index + 2) == 'I') {
/*  481 */         result.append('J');
/*      */       } else {
/*  483 */         result.append('K');
/*      */       } 
/*  485 */       index += 2;
/*  486 */     } else if ((index > 1 && contains(value, index - 2, 1, "B", "H", "D")) || (index > 2 && contains(value, index - 3, 1, "B", "H", "D")) || (index > 3 && contains(value, index - 4, 1, "B", "H"))) {
/*      */ 
/*      */ 
/*      */       
/*  490 */       index += 2;
/*      */     } else {
/*  492 */       if (index > 2 && charAt(value, index - 1) == 'U' && contains(value, index - 3, 1, "C", "G", "L", "R", "T")) {
/*      */ 
/*      */         
/*  495 */         result.append('F');
/*  496 */       } else if (index > 0 && charAt(value, index - 1) != 'I') {
/*  497 */         result.append('K');
/*      */       } 
/*  499 */       index += 2;
/*      */     } 
/*  501 */     return index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int handleH(String value, DoubleMetaphoneResult result, int index) {
/*  511 */     if ((index == 0 || isVowel(charAt(value, index - 1))) && isVowel(charAt(value, index + 1))) {
/*      */       
/*  513 */       result.append('H');
/*  514 */       index += 2;
/*      */     } else {
/*      */       
/*  517 */       index++;
/*      */     } 
/*  519 */     return index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int handleJ(String value, DoubleMetaphoneResult result, int index, boolean slavoGermanic) {
/*  527 */     if (contains(value, index, 4, "JOSE") || contains(value, 0, 4, "SAN ")) {
/*      */       
/*  529 */       if ((index == 0 && charAt(value, index + 4) == ' ') || value.length() == 4 || contains(value, 0, 4, "SAN ")) {
/*      */         
/*  531 */         result.append('H');
/*      */       } else {
/*  533 */         result.append('J', 'H');
/*      */       } 
/*  535 */       index++;
/*      */     } else {
/*  537 */       if (index == 0 && !contains(value, index, 4, "JOSE")) {
/*  538 */         result.append('J', 'A');
/*  539 */       } else if (isVowel(charAt(value, index - 1)) && !slavoGermanic && (charAt(value, index + 1) == 'A' || charAt(value, index + 1) == 'O')) {
/*      */         
/*  541 */         result.append('J', 'H');
/*  542 */       } else if (index == value.length() - 1) {
/*  543 */         result.append('J', ' ');
/*  544 */       } else if (!contains(value, index + 1, 1, L_T_K_S_N_M_B_Z) && !contains(value, index - 1, 1, "S", "K", "L")) {
/*  545 */         result.append('J');
/*      */       } 
/*      */       
/*  548 */       if (charAt(value, index + 1) == 'J') {
/*  549 */         index += 2;
/*      */       } else {
/*  551 */         index++;
/*      */       } 
/*      */     } 
/*  554 */     return index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int handleL(String value, DoubleMetaphoneResult result, int index) {
/*  563 */     result.append('L');
/*  564 */     if (charAt(value, index + 1) == 'L') {
/*  565 */       if (conditionL0(value, index)) {
/*  566 */         result.appendAlternate(' ');
/*      */       }
/*  568 */       index += 2;
/*      */     } else {
/*  570 */       index++;
/*      */     } 
/*  572 */     return index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int handleP(String value, DoubleMetaphoneResult result, int index) {
/*  581 */     if (charAt(value, index + 1) == 'H') {
/*  582 */       result.append('F');
/*  583 */       index += 2;
/*      */     } else {
/*  585 */       result.append('P');
/*  586 */       index = contains(value, index + 1, 1, "P", "B") ? (index + 2) : (index + 1);
/*      */     } 
/*  588 */     return index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int handleR(String value, DoubleMetaphoneResult result, int index, boolean slavoGermanic) {
/*  598 */     if (index == value.length() - 1 && !slavoGermanic && contains(value, index - 2, 2, "IE") && !contains(value, index - 4, 2, "ME", "MA")) {
/*      */ 
/*      */       
/*  601 */       result.appendAlternate('R');
/*      */     } else {
/*  603 */       result.append('R');
/*      */     } 
/*  605 */     return (charAt(value, index + 1) == 'R') ? (index + 2) : (index + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int handleS(String value, DoubleMetaphoneResult result, int index, boolean slavoGermanic) {
/*  615 */     if (contains(value, index - 1, 3, "ISL", "YSL")) {
/*      */       
/*  617 */       index++;
/*  618 */     } else if (index == 0 && contains(value, index, 5, "SUGAR")) {
/*      */       
/*  620 */       result.append('X', 'S');
/*  621 */       index++;
/*  622 */     } else if (contains(value, index, 2, "SH")) {
/*  623 */       if (contains(value, index + 1, 4, "HEIM", "HOEK", "HOLM", "HOLZ")) {
/*      */ 
/*      */         
/*  626 */         result.append('S');
/*      */       } else {
/*  628 */         result.append('X');
/*      */       } 
/*  630 */       index += 2;
/*  631 */     } else if (contains(value, index, 3, "SIO", "SIA") || contains(value, index, 4, "SIAN")) {
/*      */       
/*  633 */       if (slavoGermanic) {
/*  634 */         result.append('S');
/*      */       } else {
/*  636 */         result.append('S', 'X');
/*      */       } 
/*  638 */       index += 3;
/*  639 */     } else if ((index == 0 && contains(value, index + 1, 1, "M", "N", "L", "W")) || contains(value, index + 1, 1, "Z")) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  644 */       result.append('S', 'X');
/*  645 */       index = contains(value, index + 1, 1, "Z") ? (index + 2) : (index + 1);
/*  646 */     } else if (contains(value, index, 2, "SC")) {
/*  647 */       index = handleSC(value, result, index);
/*      */     } else {
/*  649 */       if (index == value.length() - 1 && contains(value, index - 2, 2, "AI", "OI")) {
/*      */ 
/*      */         
/*  652 */         result.appendAlternate('S');
/*      */       } else {
/*  654 */         result.append('S');
/*      */       } 
/*  656 */       index = contains(value, index + 1, 1, "S", "Z") ? (index + 2) : (index + 1);
/*      */     } 
/*  658 */     return index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int handleSC(String value, DoubleMetaphoneResult result, int index) {
/*  667 */     if (charAt(value, index + 2) == 'H') {
/*      */       
/*  669 */       if (contains(value, index + 3, 2, "OO", "ER", "EN", "UY", "ED", "EM")) {
/*      */ 
/*      */         
/*  672 */         if (contains(value, index + 3, 2, "ER", "EN")) {
/*      */           
/*  674 */           result.append("X", "SK");
/*      */         } else {
/*  676 */           result.append("SK");
/*      */         }
/*      */       
/*  679 */       } else if (index == 0 && !isVowel(charAt(value, 3)) && charAt(value, 3) != 'W') {
/*  680 */         result.append('X', 'S');
/*      */       } else {
/*  682 */         result.append('X');
/*      */       }
/*      */     
/*  685 */     } else if (contains(value, index + 2, 1, "I", "E", "Y")) {
/*  686 */       result.append('S');
/*      */     } else {
/*  688 */       result.append("SK");
/*      */     } 
/*  690 */     return index + 3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int handleT(String value, DoubleMetaphoneResult result, int index) {
/*  699 */     if (contains(value, index, 4, "TION")) {
/*  700 */       result.append('X');
/*  701 */       index += 3;
/*  702 */     } else if (contains(value, index, 3, "TIA", "TCH")) {
/*  703 */       result.append('X');
/*  704 */       index += 3;
/*  705 */     } else if (contains(value, index, 2, "TH") || contains(value, index, 3, "TTH")) {
/*      */       
/*  707 */       if (contains(value, index + 2, 2, "OM", "AM") || contains(value, 0, 4, "VAN ", "VON ") || contains(value, 0, 3, "SCH")) {
/*      */ 
/*      */ 
/*      */         
/*  711 */         result.append('T');
/*      */       } else {
/*  713 */         result.append('0', 'T');
/*      */       } 
/*  715 */       index += 2;
/*      */     } else {
/*  717 */       result.append('T');
/*  718 */       index = contains(value, index + 1, 1, "T", "D") ? (index + 2) : (index + 1);
/*      */     } 
/*  720 */     return index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int handleW(String value, DoubleMetaphoneResult result, int index) {
/*  729 */     if (contains(value, index, 2, "WR")) {
/*      */       
/*  731 */       result.append('R');
/*  732 */       index += 2;
/*      */     }
/*  734 */     else if (index == 0 && (isVowel(charAt(value, index + 1)) || contains(value, index, 2, "WH"))) {
/*      */       
/*  736 */       if (isVowel(charAt(value, index + 1))) {
/*      */         
/*  738 */         result.append('A', 'F');
/*      */       } else {
/*      */         
/*  741 */         result.append('A');
/*      */       } 
/*  743 */       index++;
/*  744 */     } else if ((index == value.length() - 1 && isVowel(charAt(value, index - 1))) || contains(value, index - 1, 5, "EWSKI", "EWSKY", "OWSKI", "OWSKY") || contains(value, 0, 3, "SCH")) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  749 */       result.appendAlternate('F');
/*  750 */       index++;
/*  751 */     } else if (contains(value, index, 4, "WICZ", "WITZ")) {
/*      */       
/*  753 */       result.append("TS", "FX");
/*  754 */       index += 4;
/*      */     } else {
/*  756 */       index++;
/*      */     } 
/*      */     
/*  759 */     return index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int handleX(String value, DoubleMetaphoneResult result, int index) {
/*  768 */     if (index == 0) {
/*  769 */       result.append('S');
/*  770 */       index++;
/*      */     } else {
/*  772 */       if (index != value.length() - 1 || (!contains(value, index - 3, 3, "IAU", "EAU") && !contains(value, index - 2, 2, "AU", "OU")))
/*      */       {
/*      */ 
/*      */         
/*  776 */         result.append("KS");
/*      */       }
/*  778 */       index = contains(value, index + 1, 1, "C", "X") ? (index + 2) : (index + 1);
/*      */     } 
/*  780 */     return index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int handleZ(String value, DoubleMetaphoneResult result, int index, boolean slavoGermanic) {
/*  788 */     if (charAt(value, index + 1) == 'H') {
/*      */       
/*  790 */       result.append('J');
/*  791 */       index += 2;
/*      */     } else {
/*  793 */       if (contains(value, index + 1, 2, "ZO", "ZI", "ZA") || (slavoGermanic && index > 0 && charAt(value, index - 1) != 'T')) {
/*  794 */         result.append("S", "TS");
/*      */       } else {
/*  796 */         result.append('S');
/*      */       } 
/*  798 */       index = (charAt(value, index + 1) == 'Z') ? (index + 2) : (index + 1);
/*      */     } 
/*  800 */     return index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean conditionC0(String value, int index) {
/*  809 */     if (contains(value, index, 4, "CHIA"))
/*  810 */       return true; 
/*  811 */     if (index <= 1)
/*  812 */       return false; 
/*  813 */     if (isVowel(charAt(value, index - 2)))
/*  814 */       return false; 
/*  815 */     if (!contains(value, index - 1, 3, "ACH")) {
/*  816 */       return false;
/*      */     }
/*  818 */     char c = charAt(value, index + 2);
/*  819 */     return ((c != 'I' && c != 'E') || contains(value, index - 2, 6, "BACHER", "MACHER"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean conditionCH0(String value, int index) {
/*  828 */     if (index != 0)
/*  829 */       return false; 
/*  830 */     if (!contains(value, index + 1, 5, "HARAC", "HARIS") && !contains(value, index + 1, 3, "HOR", "HYM", "HIA", "HEM"))
/*      */     {
/*  832 */       return false; } 
/*  833 */     if (contains(value, 0, 5, "CHORE")) {
/*  834 */       return false;
/*      */     }
/*  836 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean conditionCH1(String value, int index) {
/*  844 */     return (contains(value, 0, 4, "VAN ", "VON ") || contains(value, 0, 3, "SCH") || contains(value, index - 2, 6, "ORCHES", "ARCHIT", "ORCHID") || contains(value, index + 2, 1, "T", "S") || ((contains(value, index - 1, 1, "A", "O", "U", "E") || index == 0) && (contains(value, index + 2, 1, L_R_N_M_B_H_F_V_W_SPACE) || index + 1 == value.length() - 1)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean conditionL0(String value, int index) {
/*  856 */     if (index == value.length() - 3 && contains(value, index - 1, 4, "ILLO", "ILLA", "ALLE"))
/*      */     {
/*  858 */       return true; } 
/*  859 */     if ((contains(value, index - 1, 2, "AS", "OS") || contains(value, value.length() - 1, 1, "A", "O")) && contains(value, index - 1, 4, "ALLE"))
/*      */     {
/*      */       
/*  862 */       return true;
/*      */     }
/*  864 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean conditionM0(String value, int index) {
/*  872 */     if (charAt(value, index + 1) == 'M') {
/*  873 */       return true;
/*      */     }
/*  875 */     return (contains(value, index - 1, 3, "UMB") && (index + 1 == value.length() - 1 || contains(value, index + 2, 2, "ER")));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isSlavoGermanic(String value) {
/*  887 */     return (value.indexOf('W') > -1 || value.indexOf('K') > -1 || value.indexOf("CZ") > -1 || value.indexOf("WITZ") > -1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isVowel(char ch) {
/*  895 */     return ("AEIOUY".indexOf(ch) != -1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isSilentStart(String value) {
/*  904 */     boolean result = false;
/*  905 */     for (int i = 0; i < SILENT_START.length; i++) {
/*  906 */       if (value.startsWith(SILENT_START[i])) {
/*  907 */         result = true;
/*      */         break;
/*      */       } 
/*      */     } 
/*  911 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String cleanInput(String input) {
/*  918 */     if (input == null) {
/*  919 */       return null;
/*      */     }
/*  921 */     input = input.trim();
/*  922 */     if (input.length() == 0) {
/*  923 */       return null;
/*      */     }
/*  925 */     return input.toUpperCase();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected char charAt(String value, int index) {
/*  934 */     if (index < 0 || index >= value.length()) {
/*  935 */       return Character.MIN_VALUE;
/*      */     }
/*  937 */     return value.charAt(index);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean contains(String value, int start, int length, String criteria) {
/*  945 */     return contains(value, start, length, new String[] { criteria });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean contains(String value, int start, int length, String criteria1, String criteria2) {
/*  954 */     return contains(value, start, length, new String[] { criteria1, criteria2 });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean contains(String value, int start, int length, String criteria1, String criteria2, String criteria3) {
/*  964 */     return contains(value, start, length, new String[] { criteria1, criteria2, criteria3 });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean contains(String value, int start, int length, String criteria1, String criteria2, String criteria3, String criteria4) {
/*  974 */     return contains(value, start, length, new String[] { criteria1, criteria2, criteria3, criteria4 });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean contains(String value, int start, int length, String criteria1, String criteria2, String criteria3, String criteria4, String criteria5) {
/*  986 */     return contains(value, start, length, new String[] { criteria1, criteria2, criteria3, criteria4, criteria5 });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean contains(String value, int start, int length, String criteria1, String criteria2, String criteria3, String criteria4, String criteria5, String criteria6) {
/*  998 */     return contains(value, start, length, new String[] { criteria1, criteria2, criteria3, criteria4, criteria5, criteria6 });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static boolean contains(String value, int start, int length, String[] criteria) {
/* 1010 */     boolean result = false;
/* 1011 */     if (start >= 0 && start + length <= value.length()) {
/* 1012 */       String target = value.substring(start, start + length);
/*      */       
/* 1014 */       for (int i = 0; i < criteria.length; i++) {
/* 1015 */         if (target.equals(criteria[i])) {
/* 1016 */           result = true;
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1021 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public class DoubleMetaphoneResult
/*      */   {
/*      */     private StringBuffer primary;
/*      */     
/*      */     private StringBuffer alternate;
/*      */     
/*      */     private int maxLength;
/*      */     
/*      */     private final DoubleMetaphone this$0;
/*      */     
/*      */     public DoubleMetaphoneResult(DoubleMetaphone this$0, int maxLength) {
/* 1036 */       this.this$0 = this$0; this.primary = new StringBuffer(this.this$0.getMaxCodeLen()); this.alternate = new StringBuffer(this.this$0.getMaxCodeLen());
/* 1037 */       this.maxLength = maxLength;
/*      */     }
/*      */     
/*      */     public void append(char value) {
/* 1041 */       appendPrimary(value);
/* 1042 */       appendAlternate(value);
/*      */     }
/*      */     
/*      */     public void append(char primary, char alternate) {
/* 1046 */       appendPrimary(primary);
/* 1047 */       appendAlternate(alternate);
/*      */     }
/*      */     
/*      */     public void appendPrimary(char value) {
/* 1051 */       if (this.primary.length() < this.maxLength) {
/* 1052 */         this.primary.append(value);
/*      */       }
/*      */     }
/*      */     
/*      */     public void appendAlternate(char value) {
/* 1057 */       if (this.alternate.length() < this.maxLength) {
/* 1058 */         this.alternate.append(value);
/*      */       }
/*      */     }
/*      */     
/*      */     public void append(String value) {
/* 1063 */       appendPrimary(value);
/* 1064 */       appendAlternate(value);
/*      */     }
/*      */     
/*      */     public void append(String primary, String alternate) {
/* 1068 */       appendPrimary(primary);
/* 1069 */       appendAlternate(alternate);
/*      */     }
/*      */     
/*      */     public void appendPrimary(String value) {
/* 1073 */       int addChars = this.maxLength - this.primary.length();
/* 1074 */       if (value.length() <= addChars) {
/* 1075 */         this.primary.append(value);
/*      */       } else {
/* 1077 */         this.primary.append(value.substring(0, addChars));
/*      */       } 
/*      */     }
/*      */     
/*      */     public void appendAlternate(String value) {
/* 1082 */       int addChars = this.maxLength - this.alternate.length();
/* 1083 */       if (value.length() <= addChars) {
/* 1084 */         this.alternate.append(value);
/*      */       } else {
/* 1086 */         this.alternate.append(value.substring(0, addChars));
/*      */       } 
/*      */     }
/*      */     
/*      */     public String getPrimary() {
/* 1091 */       return this.primary.toString();
/*      */     }
/*      */     
/*      */     public String getAlternate() {
/* 1095 */       return this.alternate.toString();
/*      */     }
/*      */     
/*      */     public boolean isComplete() {
/* 1099 */       return (this.primary.length() >= this.maxLength && this.alternate.length() >= this.maxLength);
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\safari downloads\apache-commons-codec-1.3.jar!\org\apache\commons\codec\language\DoubleMetaphone.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */