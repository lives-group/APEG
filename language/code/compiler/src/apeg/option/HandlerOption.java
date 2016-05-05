package apeg.option;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;

import apeg.util.lang.java.JavaInfo;
import apeg.util.path.AbsolutePath;
import apeg.util.path.RelativePath;
import apeg.Tool;

public class HandlerOption {

	public static String[] handle(String[] args, Tool tool) {
		Options options = specifyOptions();

		try {
			CommandLine line = parseOptions(options, args);
			for(Option opt : line.getOptions()) {
				switch(opt.getLongOpt()) {
				case "help": // print the help message
					usage(options);
					break;
				case "package": // set the package/namespace
					tool.setNamespace(opt.getValue());
					break;
				case "output": // set the output directory
					String output = opt.getValue();
					if(AbsolutePath.isAbsolute(output)) {
						tool.setOutput(new AbsolutePath(output));
					} else {
						tool.setOutput(new RelativePath(new AbsolutePath(System.getProperty("user.dir")),
								           output));
					}
					break;
				case "external": // set the path of the external functions
					String external = opt.getValue();
					if(AbsolutePath.isAbsolute(external)) {
						tool.setExternalDir(new AbsolutePath(external));
					} else {
						tool.setExternalDir(new RelativePath(new AbsolutePath(System.getProperty("user.dir")),
								           external));
					}
					break;
				case "language": // set the target language
					String language = opt.getValue();
					switch(language) {
					case "Java":
						tool.setTargetLang(new JavaInfo());
						break;
					default:
						tool.addWarning("Unsupported target language " + language);
						break;
					}
					break;	
				default:
					tool.addWarning("Invalid option " + opt.getLongOpt());
				}
			}
			String[] grammars = line.getArgs();
			if(grammars.length < 1)
				throw new Error("No grammar file specified.");
			return grammars;
			
		} catch (ParseException e) {
			throw new Error(e.getMessage());
		}
	}

	private static Options specifyOptions() {
		Options options = new Options();

		options.addOption("o", "output", true, "Specific the output directory");
		options.addOption("p", "package", true,
				"The package/namespace for the generated code");
		options.addOption("l", "language", true,
				"Specific the target language for the generated code. This is also the language of the external functions.");
		options.addOption("e", "external", true, "The path location of the external functions");
		options.addOption("h", "help", false, "Print the help message");

		return options;

	}
	
	private static CommandLine parseOptions(Options options, String[] args)
			throws org.apache.commons.cli.ParseException {
		CommandLineParser parser = new GnuParser();
		return parser.parse(options, args);
	}
	
	/**
	 * TODO
	 */
	private static void usage(Options opt) {
		System.out.println(opt.toString());
	}
}
