package com.panopset.gp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.panopset.compat.Logop;
import com.panopset.compat.Stringop;

public class TextFileProcessor extends LineIterator {

	public static List<String> loadSmallTextFileToList(final File file) {
		final List<String> lines = new ArrayList<>();
		if (file == null || !file.exists()) {
			return lines;
		}
		try {
			TextFileProcessor tfp = TextFileProcessor.textFileIterator(file);
			while (tfp.hasNext()) {
				lines.add(tfp.next());
			}
		} catch (IOException e) {
			Logop.errorEx(e);

		}
		return lines;
	}

	public static String loadSmallTextFile(final File file) throws IOException {
		if (file == null) {
			throw new NullPointerException();
		}
		if (!file.exists()) {
			Logop.errorMsg("File does not exist.", file);
		}
		StringWriter sw = new StringWriter();
		boolean firstTime = true;
		TextFileProcessor tfp = TextFileProcessor.textFileIterator(file);
		while (tfp.hasNext()) {
			if (firstTime) {
				firstTime = false;
			} else {
				sw.append(Stringop.getEol());
			}
			sw.append(tfp.next());
		}
		return sw.toString();
	}

	public static boolean filesAreSame(final File file0, final File file1) {
		try {
			TextFileProcessor tfe0 = TextFileProcessor.textFileIterator(file0);
			TextFileProcessor tfe1 = TextFileProcessor.textFileIterator(file1);
			while (tfe0.hasNext() && tfe1.hasNext()) {
				String s0 = tfe0.next();
				String s1 = tfe1.next();
				if (!s1.equals(s0)) {
					return false;
				}
			}
			if (tfe0.hasNext()) {
				return false;
			}
			return !tfe1.hasNext();
		} catch (IOException ex) {
			Logop.errorEx(ex);
			return false;
		}
	}

	private TextFileProcessor(final Reader reader) {
		super(reader);
	}

	public static TextFileProcessor textFileIterator(final File file) throws IOException {
		return new TextFileProcessor(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
	}
}
