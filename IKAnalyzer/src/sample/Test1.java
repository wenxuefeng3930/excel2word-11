package sample;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

public class Test1 {

	public static void main(String[] args) throws IOException {
		String text = "we are fimaly! i love you ! i loved you !";
		Configuration configuration = DefaultConfig.getInstance();  
	    configuration.setUseSmart(true);  
		StringReader sr = new StringReader(text);
		InputStreamReader isr = new InputStreamReader(new FileInputStream(new File("stopword.dic")));  
		IKSegmenter ik = new IKSegmenter(isr, true);
		Lexeme lex = null;
		while ((lex = ik.next()) != null) {
			System.out.print(lex.getLexemeText() + "|");
		}
	}
}