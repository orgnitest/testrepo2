package kr.ac.jbnu.sq.methods.miner.ast;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FileASTRequestor;

public class ParserRequestor extends FileASTRequestor 
{
	private MethodVisitor visitor;

	public ParserRequestor(MethodVisitor v) {
		this.visitor = v;
	}

	@Override
	public void acceptAST(String sourceFilePath, CompilationUnit ast) {
		ast.accept(this.visitor);
		super.acceptAST(sourceFilePath, ast);
	}

}
