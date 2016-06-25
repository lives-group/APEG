package apeg.util.path;

import java.io.File;

public abstract class Path {
	public abstract String getAbsolutePath();

	public File getFile() {
		return new File(getAbsolutePath());
	}

	public String toString() {
		return getAbsolutePath();
	}

	public int hashCode() {
		return getAbsolutePath().hashCode();
	}

	public boolean equals(Object o) {
		return o instanceof Path
				&& ((Path) o).getAbsolutePath().equals(getAbsolutePath());
	}
}
