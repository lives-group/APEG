package apeg.util.path;

import java.io.File;

public class RelativePath extends Path {

	private String relativePath;
	private Path base;

	public RelativePath(Path base, String relativePath) {
		this.base = base;
		if (relativePath == null || relativePath.isEmpty())
			throw new IllegalArgumentException("RelativePath constructed from an unacceptable relative path name");
		this.relativePath = relativePath;
	}

	@Override
	public String getAbsolutePath() {
		return getBasePath().getAbsolutePath() + File.separator + getRelativePath();
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setBasePath(Path base) {
		this.base = base;
	}

	public Path getBasePath() {
		return base;
	}
}
