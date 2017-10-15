package otus;

import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {

        List<String> input = Resources.readLines(Resources.getResource("input"), Charsets.UTF_8);
        Map<String, Integer> frequencySnapshot = Maps.newHashMap();

        for (String element : input) {
            List<String> line = Splitter.on(CharMatcher.anyOf(" .,:;\n\t\r()[]{}")).omitEmptyStrings().splitToList(element);

            for (String word : line) {

                if (frequencySnapshot.containsKey(word)) {
                    Integer count = frequencySnapshot.get(word);
                    frequencySnapshot.put(word, ++count);
                    continue;
                }
                frequencySnapshot.put(word, 1);
            }
        }
        System.out.println(frequencySnapshot);
    }
}
