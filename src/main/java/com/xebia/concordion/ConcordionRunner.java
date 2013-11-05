package com.xebia.concordion;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

/**
 * Run a Concordion test specified in a HTML file. 
 */
public class ConcordionRunner {

	private String targetPath;
	private String templateClassName;

	/*
	 * @param targetPath location for generated classes
	 * @param templateClassName name of the class to be used as a template to generate test classes
	 */
	public ConcordionRunner (String targetPath, String templateClassName) {
		this.targetPath = targetPath;
		this.templateClassName = templateClassName;
	}
	
	/*
	 * Generate a class for a given fixture.
	 * @param fixtureName name of the fixture class to be generated
	 */
	protected CtClass generate(String fixtureName) throws Exception {
		ClassPool pool = ClassPool.getDefault();
		CtClass cc = pool.get(templateClassName);
		cc.setName(fixtureName);
		return cc;
	}

	/*
	 * Export generated fixture class to a file on disk.
	 * @param generatedClass class that was generated
	 * 
	 */
	protected void exportToClassFile(CtClass generatedClass) throws IOException, CannotCompileException {
		String fixtureName = generatedClass.getName();
		OutputStream out = new FileOutputStream(new File(getOutputClassFileName(fixtureName)));
		out.write(generatedClass.toBytecode());
		out.close();
	}

	/*
	 * Run a generated unit test.
	 * @param generatedClass class that was generated
	 */
	protected Result runTest(CtClass generatedClass) throws CannotCompileException {
		return JUnitCore.runClasses(generatedClass.toClass());
	}

	private String getOutputClassFileName(String fixtureName) {
		return targetPath + "/" + fixtureName + ".class";
	}
}
