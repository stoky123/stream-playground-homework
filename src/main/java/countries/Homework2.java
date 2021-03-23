package countries;

import java.util.function.Function;

import java.io.IOException;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.*;

import java.time.ZoneId;

public class Homework2 {

    private List<Country> countries;

    public Homework2() {
        countries = new CountryRepository().getAll();
    }

    /**
     * Returns the longest country name translation.
     */
    public Optional<String> streamPipeline1() {
        return countries.stream().flatMap(country -> country.getTranslations().values().stream()).max(Comparator.comparing(translation -> translation.length()));
    }

    /**
     * Returns the longest Italian (i.e., {@code "it"}) country name translation.
     */
    public Optional<String> streamPipeline2() {
        return countries.stream().flatMap(country -> country.getTranslations().entrySet().stream()).filter(country -> country.getKey() == "it").max(Comparator.comparing(translation -> translation.getValue().length())).map(translation -> translation.getValue());
    }

    /**
     * Prints the longest country name translation together with its language code in the form language=translation.
     */
    public void streamPipeline3() {
        System.out.println(countries.stream().flatMap(c -> c.getTranslations().entrySet().stream()).max(Comparator.comparing(translation -> translation.getValue().length())).get());
    }

    /**
     * Prints single word country names (i.e., country names that do not contain any space characters).
     */
    public void streamPipeline4() {
        countries.stream().map(c -> c.getName()).filter(c -> !c.contains(" ")).forEach(System.out::println);
    }

    /**
     * Returns the country name with the most number of words.
     */
    public Optional<String> streamPipeline5() {
        return countries.stream().max(Comparator.comparingInt(c -> c.getName().split(" ").length)).map(Country::getName);
    }

    /**
     * Returns whether there exists at least one capital that is a palindrome.
     */
    public boolean streamPipeline6() {
        return countries.stream().map(Country::getCapital).anyMatch(lower -> lower.toLowerCase().equals(new StringBuilder(lower).reverse().toString()));
    }
    
    int charCount(String s, char c) {
    	int count = 0;
    	for(int i = 0; i < s.length(); i++) {
    		if (s.charAt(i) == c)
    			count++;
    	}
    	return count;
    }

    /**
     * Returns the country name with the most number of {@code 'e'} characters ignoring case.
     */
    public Optional<String> streamPipeline7() {
        return countries.stream().map(Country::getName).max(Comparator.comparingInt(n -> charCount(n, 'e')));
    }
    
    int vowelCount(String s) {
    	return charCount(s, 'a') + charCount(s, 'e') + charCount(s, 'i') + charCount(s, 'o') + charCount(s, 'u');
        }

    /**
     *  Returns the capital with the most number of English vowels (i.e., {@code 'a'}, {@code 'e'}, {@code 'i'}, {@code 'o'}, {@code 'u'}).
     */
    public Optional<String> streamPipeline8() {
        return countries.stream().map(Country::getCapital).max(Comparator.comparingInt(n -> vowelCount(n)));
    }

    /**
     * Returns a map that contains for each character the number of occurrences in country names ignoring case.
     */
    public Map<Character, Long> streamPipeline9() {
        return countries.stream().map(Country::getName).flatMap(name -> name.toLowerCase().chars().mapToObj(c -> (char)c)).collect(groupingBy(Function.identity(), counting()));
    }

    /**
     * Returns a map that contains the number of countries for each possible timezone.
     */
    public Map<ZoneId, Long> streamPipeline10() {
        return countries.stream().map(Country::getTimezones).flatMap(c -> c.stream()).collect(groupingBy(Function.identity(), counting()));
    }

    /**
     * Returns the number of country names by region that starts with their two-letter country code ignoring case.
     */
    public Map<Region, Long> streamPipeline11() {
        return countries.stream().filter(c -> c.getName().toLowerCase().startsWith(c.getCode().toLowerCase())).collect(groupingBy(c -> c.getRegion(), counting()));
    }

    /**
     * Returns a map that contains the number of countries whose population is greater or equal than the population average versus the the number of number of countries with population below the average.
     */
    public Map<Boolean, Long> streamPipeline12() {
        return countries.stream().collect(groupingBy(c -> c.getPopulation() >= countries.stream().mapToLong(Country::getPopulation).average().getAsDouble(), counting()));
    }

    /**
     * Returns a map that contains for each country code the name of the corresponding country in Portuguese ({@code "pt"}).
     */
    public Map<String, String> streamPipeline13() {
        // TODO
        return null;
    }

    /**
     * Returns the list of capitals by region whose name is the same is the same as the name of their country.
     */
    public Map<Region, List<String>> streamPipeline14() {
        return countries.stream()
                .collect(groupingBy(c -> c.getRegion(), filtering(c -> c.getCapital().equals(c.getName()), mapping(Country::getCapital, toList()))));
    }

    /**
     *  Returns a map of country name-population density pairs.
     */
    public Map<String, Double> streamPipeline15() {
        // TODO
        return null;
    }

}
