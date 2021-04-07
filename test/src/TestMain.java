import java.util.Locale;

public class TestMain {
    public static void main(String[] args) {
        Languages.Language lan =  Languages.Language.fromName("Chinese Standard");
        System.out.println(lan);
/*static public final Locale SIMPLIFIED_CHINESE = createConstant("zh", "CN");
 */
        Languages.Language lan2 = Languages.Language.fromLocale("zh");
        System.out.println(lan2);
    }
}
