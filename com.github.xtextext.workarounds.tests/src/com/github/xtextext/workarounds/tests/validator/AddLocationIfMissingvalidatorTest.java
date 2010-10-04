/*
 * Project: com.github.xtextext.workarounds.tests
 */
package com.github.xtextext.workarounds.tests.validator;

import org.eclipse.emf.mwe.core.issues.IssuesImpl;
import org.eclipse.xtext.validation.Issue.IssueImpl;
import org.junit.Test;

import com.github.xtextext.workarounds.validator.AddLocationIfMissingValidator;

/**
 *
 * @author DaWeber
 */
public class AddLocationIfMissingvalidatorTest
{
   @Test
   public void canCopeWithMissingLocationInfo() throws Exception
   {
      IssuesImpl issues = new IssuesImpl();
      issues.addError("Error1", new IssueImpl());
      issues.addError("Error2", new IssueImpl());
      issues.addError("Error3", new IssueImpl());
      issues.addWarning("Warning1", new IssueImpl());
      issues.addWarning("Warning2", new IssueImpl());
      issues.addWarning("Warning3", new IssueImpl());
      AddLocationIfMissingValidator testee = new AddLocationIfMissingValidator();
      // throws an NPE in 1.0.1
      testee.toString(issues);
   }
}
