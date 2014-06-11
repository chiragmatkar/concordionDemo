package com.xebia.concordion;

import javassist.CtClass;

import org.junit.runner.Result;

/**
 * This example shows how to use <code>ConcordionRunner</code> to generate a unit test class
 * for the test in <code>HelloWorldAgain.html</code>
 */
public class Example {
	public static void main(String[] args) {
		ConcordionRunner classGenerator = new ConcordionRunner("target", "com.xebia.concordion.FixtureTemplate");
		try {
			String fixtureName = "HelloWorldAgainFixture";
			CtClass generatedClass = classGenerator.generate(fixtureName);
			classGenerator.exportToClassFile(generatedClass);
			Result testResults = classGenerator.runTest(generatedClass);
			System.out.println("Result: " + testResults.getFailures());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
