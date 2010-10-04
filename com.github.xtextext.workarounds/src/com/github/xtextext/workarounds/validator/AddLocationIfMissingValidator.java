/*
 * Project: com.github.xtextext.workarounds
 */
package com.github.xtextext.workarounds.validator;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.mwe.core.issues.MWEDiagnostic;
import org.eclipse.xtext.mwe.Validator;
import org.eclipse.xtext.validation.Issue.IssueImpl;

import com.google.common.collect.Multimap;

/**
 * A workaround for bug 322645 . If location information is missing, this validator adds
 * default location information to avoid running into an NPE lateron.
 *
 * @author DaWeber
 */
public class AddLocationIfMissingValidator extends Validator
{
   @Override
   protected Multimap<URI, MWEDiagnostic> groupByURI(MWEDiagnostic[] diagnostic)
   {
      for(MWEDiagnostic diag : diagnostic)
      {
         IssueImpl issue = (IssueImpl)diag.getElement();
         if(issue.getLineNumber() == null)
         {
            issue.setLineNumber(Integer.valueOf(-1));
         }
         if(issue.getOffset() == null)
         {
            issue.setOffset(Integer.valueOf(-1));
         }
         if(issue.getMessage() == null)
         {
            issue.setMessage("<null>");
         }
      }
      return super.groupByURI(diagnostic);
   }
}
