/*
 * Project: com.github.xtextext.workarounds.tests
 */
package com.github.xtextext.workarounds.tests.parser;

import junit.framework.Assert;

import org.antlr.runtime.BitSet;
import org.antlr.runtime.TokenStream;
import org.eclipse.xtext.Grammar;
import org.junit.Test;

import com.github.xtextext.workarounds.parser.PatchedAbstractInternalContentAssistParser;

/**
 * See https://bugs.eclipse.org/bugs/show_bug.cgi?id=326509
 * @author DaWeber
 */
public class PatchedAbstractInternalContentAssistParserTest
{
   private final class TestableParser extends PatchedAbstractInternalContentAssistParser
   {
      private TestableParser(TokenStream input)
      {
         super(input);
      }

      @Override
      protected Grammar getGrammar()
      {
         return null;
      }

      @Override
      public void pushFollow(BitSet fset)
      {
         super.pushFollow(fset);
      }

      public BitSet[] getFollowing()
      {
         return following;
      }

   }

   @Test
   public void stackGrowth()
   {
      TestableParser parser = new TestableParser(null);
      for(int i = 0; i < PatchedAbstractInternalContentAssistParser.INITIAL_FOLLOW_STACK_SIZE * 3; ++i)
      {
         parser.pushFollow(new BitSet(1));
      }
      BitSet[] following = parser.getFollowing();
      for(int i = 0; i < PatchedAbstractInternalContentAssistParser.INITIAL_FOLLOW_STACK_SIZE * 3; ++i)
      {
         Assert.assertNotNull("" + i, following[i]);
      }
   }

}
