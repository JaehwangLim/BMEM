package com.gmail.assamoa.MemGraph;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BMemParser {
	public static final String SAMPLE = "847:04:720Total Size: 71303168, Allocated: 46604288, Free: 24698880 test";
	public static final String TIME_PATTERN = "\\d+:\\d{2}:\\d{1,}"; // xxx:yy:zzz
																		// =>
																		// xxx분
																		// yy초
																		// zzz밀리초
	public static final String BMEM_PATTERN = "Total Size: \\d+, Allocated: \\d+, Free: \\d+";

	public String getFreeBMEM(String log) {
		Pattern pattern = Pattern.compile(BMEM_PATTERN);
		Matcher matcher = pattern.matcher(log);
		if (matcher.find()) {
			Pattern number = Pattern.compile("Free: \\d+");
			Matcher matcherMem = number.matcher(log);

			if (matcherMem.find()) {
				String freeMem = matcherMem.group();
				Matcher mem = Pattern.compile("\\d+").matcher(freeMem);
				if (mem.find()) {
					String freeMemory = mem.group();
					return freeMemory;
				}
			}
		}
		return null;
	}
}
