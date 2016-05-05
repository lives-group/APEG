package apeg.util.path;

import java.io.File;

public class AbsolutePath extends Path {

	private String path;

	public AbsolutePath(String path) {
		if (!isAbsolute(path))
			throw new IllegalArgumentException(
					"AbsolutePath constructed on unacceptable argument: "
							+ path);
		this.path = path;
	}

	public static boolean isAbsolute(String path) {
		return new File(path).isAbsolute() || path.startsWith("./")
				|| path.startsWith("." + File.separator) || path.equals(".");
	}

	@Override
	public String getAbsolutePath() {
		return path;
	}

}
