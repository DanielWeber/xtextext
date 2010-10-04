/*
 * Project: com.github.xtextext.workarounds
 */
package com.github.xtextext.workarounds.parser;

import org.antlr.runtime.BitSet;
import org.antlr.runtime.TokenStream;
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;

/**
 * @author DaWeber
 */
public abstract class PatchedAbstractInternalContentAssistParser extends
      AbstractInternalContentAssistParser
{
   public PatchedAbstractInternalContentAssistParser(TokenStream input)
   {
      super(input);
   }

   @Override
   // see https://bugs.eclipse.org/bugs/show_bug.cgi?id=326509
   protected void pushFollow(BitSet fset)
   {
      if ( (_fsp +1)>=following.length ) {
         BitSet[] f = new BitSet[following.length*2];
         System.arraycopy(following, 0, f, 0, following.length);
         following = f;
      }
      following[++_fsp] = fset;
   }
}
