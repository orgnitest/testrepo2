package kr.ac.jbnu.sq.methods.miner.ast;

import java.util.HashMap;
import java.util.List;

import kr.ac.jbnu.sq.methods.miner.MethodRepository;
import kr.ac.jbnu.sq.methods.miner.model.ClassMethodObject;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class MethodVisitor extends ASTVisitor {
	
	private MethodRepository repository;
	
	private HashMap<String, Type> fields = new HashMap<String, Type>();
	
	
	public MethodVisitor(MethodRepository repository)
	{
		this.repository = repository;
	}
	
	public boolean visit(FieldDeclaration node)
	{
		Type fieldType = node.getType();
		String fieldTypeAsString = fieldType.toString();
		
		System.out.println("fieldTypeAsString:"+ fieldTypeAsString);
		List fragments = node.fragments();
		for (Object object : fragments)
		{
			if( object instanceof VariableDeclarationFragment)
			{
				VariableDeclarationFragment vds = (VariableDeclarationFragment)object;
				String variable = vds.toString();
				System.out.println("variable:" + variable);
				fields.put(variable, fieldType);
			}
		}
		return true;
	}
	
	
	@Override
	public boolean visit(MethodDeclaration node) {
		// If it's a constructor, it's passed. 
		if(node.isConstructor()) return true;
		
		ClassMethodObject mo = new ClassMethodObject(node, fields);
		if( repository != null)
		{
			if( !(mo == null || mo.isSuccessfulParsing() == false))
			{
				repository.addMethodObject(mo);	
			}
		}
		
		return true;
	}
}
