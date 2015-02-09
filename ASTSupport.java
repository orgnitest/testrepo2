package kr.ac.jbnu.sq.methods.miner.ast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnhancedForStatement;

public class ASTSupport
{

	public void parse(String sourceFile, String[] libs, MethodVisitor visitor)
	{
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setEnvironment(libs, new String[]
		{}, null, true);
		Map options = JavaCore.getOptions();
		JavaCore.setComplianceOptions(JavaCore.VERSION_1_6, options);
		parser.setCompilerOptions(options);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		parser.setStatementsRecovery(true);

		parser.createASTs(new String[]
		{ sourceFile }, null, new String[0], new ParserRequestor(visitor), null);
	}

	public void parse(String sourceCodeAsString, ASTVisitor visitor)
	{
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setEnvironment(null, new String[]{}, null, true);
		Map options = JavaCore.getOptions();
		JavaCore.setComplianceOptions(JavaCore.VERSION_1_6, options);
		parser.setCompilerOptions(options);
		
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(sourceCodeAsString.toCharArray());
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		parser.setStatementsRecovery(true);
		CompilationUnit cu = (CompilationUnit)parser.createAST(null);
		cu.accept(visitor);
	}

	public static void main(String[] args)
	{
		String file = "D:\\Google 드라이브\\3학년 겨울방학\\01. 코드\\kr.ac.jbnu.sql.soremore\\src\\kr\\ac\\jbnu\\sql\\soremore\\model\\RDML.java";

		String[] libs = null;
		ASTSupport astTest = new ASTSupport();
		astTest.parse(file, libs, new MethodVisitor(null));
		
//		String source = "public class AA{"   
//				+ "public void test(){ "  
//				+ "for (  Term term : this.terms) " 
//				+ "{ terms.add(term);} "
//				+ "}"
//				+ "}";
//		
//		System.out.println(source);
//		astTest.parseString(source, new ASTVisitor(){
//			@Override
//			public boolean visit(EnhancedForStatement node)
//			{
//				System.out.println("visited!");
//				return super.visit(node);
//			}
//		});
		
		System.out.println("done!");
	}
	
	public void parseString(String sourceAsString, ASTVisitor visitor) {
	    char[] source = sourceAsString.toCharArray();
//		char[] source = readCharFromFile(sourceAsString);
	    ASTParser parser = ASTParser.newParser(AST.JLS8);
	    parser.setKind(ASTParser.K_COMPILATION_UNIT);
	    parser.setSource(source);
	    parser.setResolveBindings(true);
	    CompilationUnit cu = (CompilationUnit) parser.createAST(null);
	    cu.accept(visitor);
	}
	
	private char[] readCharFromFile(String filePath)
	{
		try
		{
			File javaFile = new File(filePath);
			BufferedReader in = new BufferedReader(new FileReader(javaFile));
			final StringBuffer buffer = new StringBuffer();
			String line = null;
			while (null != (line = in.readLine())) {
			     buffer.append(line).append("\n");
			}
			return buffer.toString().toCharArray();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	
}
