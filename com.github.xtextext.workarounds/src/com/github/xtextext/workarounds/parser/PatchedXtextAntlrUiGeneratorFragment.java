/*
 * Project: com.github.xtextext.workarounds
 */
package com.github.xtextext.workarounds.parser;

import org.eclipse.xpand2.XpandExecutionContext;
import org.eclipse.xpand2.output.FileHandle;
import org.eclipse.xpand2.output.PostProcessor;
import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.generator.Generator;

import com.google.common.collect.ImmutableList;

/**
 * Patches the generated antlr grammar to use
 * {@link PatchedAbstractInternalContentAssistParser} as the parser's base class.
 *
 * See bug 326509
 *
 * @author DaWeber
 */
public class PatchedXtextAntlrUiGeneratorFragment extends
      org.eclipse.xtext.generator.parser.antlr.XtextAntlrUiGeneratorFragment
{
   private static final class AntlrGrammarPatchingPostProcessor implements PostProcessor
   {
      public void beforeWriteAndClose(FileHandle impl)
      {
         if(impl.getAbsolutePath().endsWith(".g"))
         {
            String grammarBlob = impl.getBuffer().toString();
            grammarBlob = grammarBlob
                  .replace(
                        "org.eclipse.xtext.ui.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser",
                        "com.github.xtextext.workarounds.parser.PatchedAbstractInternalContentAssistParser")
                  .replace("superClass=AbstractInternalContentAssistParser",
                        "superClass=PatchedAbstractInternalContentAssistParser");
            impl.setBuffer(grammarBlob);
         }
      }

      public void afterClose(FileHandle impl)
      {
         // nothing to do
      }
   }

   private static final String[] IMPORTED_PACKAGES = new String[] { "com.github.xtextext.workarounds.caparserbug" };

   @Override
   public String[] getImportedPackagesUi(Grammar grammar)
   {
      String[] basePackages = super.getImportedPackagesUi(grammar);
      if(null != basePackages)
      {
         return ImmutableList.of(basePackages, IMPORTED_PACKAGES)
               .toArray(new String[] {});
      }
      return IMPORTED_PACKAGES;
   }

   @Override
   public void generate(Grammar grammar, XpandExecutionContext ctx)
   {
      // install our postprocessor, see above
      ctx.getOutput().getOutlet(Generator.SRC_GEN_UI)
            .addPostprocessor(new AntlrGrammarPatchingPostProcessor());
      super.generate(grammar, ctx);
   }

   @Override
   // Have to override this so that the Xtext generator will still find the base class'
   // templates
   protected String getTemplate()
   {
      return org.eclipse.xtext.generator.parser.antlr.XtextAntlrUiGeneratorFragment.class
            .getName().replaceAll("\\.", "::");
   }
}
