package apeg.util.lang.java;

import apeg.util.lang.ExternalParser;
import apeg.util.lang.LangInfo;

public class JavaInfo implements LangInfo {

	@Override
	public String languageName() {
		return "Java";
	}

	@Override
	public String extension() {
		return "java";
	}

	/*@Override
	public boolean isModuleName(String module) {
		// TODO Auto-generated method stub
		return false;
	}*/

	@Override
	public String moduleToDir(String module) {
		return module.replace('.', '/');
	}

	@Override
	public ExternalParser parser() {
		// TODO Auto-generated method stub
		return null;
	}

}
