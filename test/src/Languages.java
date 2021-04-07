import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Locale;

//import com.reflexit.magiccards.core.locale.LocalizedText;

public class Languages/* implements ISearchableProperty*/ {
	public static enum Language {
		ENGLISH(Locale.ENGLISH),
//		RUSSIAN(Locale.RUSSIAN),
		FRENCH(Locale.FRENCH),
//		SPANISH(Locale.SPANISH),
		GERMAN(Locale.GERMAN),
		ITALIAN(Locale.ITALIAN),
//		PORTUGUESE(Locale.PORTUGUESE),
		JAPANESE(Locale.JAPANESE),
		CHINESE_SIMPLIFIED("Chinese Simplified", Locale.SIMPLIFIED_CHINESE),
		CHINESE_TRADITIONAL("Chinese Traditional", Locale.TRADITIONAL_CHINESE),
		KOREAN(Locale.KOREAN);

		private String lang;
		private Locale locale;

		Language(Locale locale) {
			this.lang = name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
			this.locale = locale;
		}

		Language(String name, Locale locale) {
			this.lang = name;
			this.locale = locale;
		}

		public String getLang() {
			return lang;
		}

		public Locale getLocale() {
			return locale;
		}

		public static Language fromName(String s) {
			Language[] values = Language.values();
			for (int i = 0; i < values.length; i++) {
				Language language = values[i];
				if (language.getLang().equals(s))
					return language;
			}
			if (s.equals("Chinese Standard"))
				return CHINESE_SIMPLIFIED;
			return null;
		}

		public static Language fromLocale(String s) {
			Language[] values = Language.values();
			for (int i = 0; i < values.length; i++) {
				Language language = values[i];
				if (language.getLocale().getLanguage().equals(s))
					return language;
			}
			return null;
		}
	}



}