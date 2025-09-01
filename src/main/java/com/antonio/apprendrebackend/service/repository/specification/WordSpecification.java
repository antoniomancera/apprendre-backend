package com.antonio.apprendrebackend.service.repository.specification;

import com.antonio.apprendrebackend.service.dto.WordFilterRequestDTO;
import com.antonio.apprendrebackend.service.model.*;
import com.antonio.apprendrebackend.service.model.Number;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class WordSpecification {
    private WordSpecification() {
    }

    public static Specification<Word> hasTextFiltered(List<String> textFiltered) {
        return (root, query, criteriaBuilder) ->
                textFiltered == null || textFiltered.isEmpty() ?
                        criteriaBuilder.conjunction() :
                        root.get("name").in(textFiltered);
    }

    public static Specification<Word> hasPartSpeeches(List<PartSpeech> partSpeeches) {
        return (root, query, criteriaBuilder) ->
                partSpeeches == null || partSpeeches.isEmpty() ?
                        criteriaBuilder.conjunction() :
                        root.get("partSpeech").in(partSpeeches);
    }


    public static Specification<Word> hasLevels(List<Level> levels) {
        return (root, query, criteriaBuilder) ->
                levels == null || levels.isEmpty() ?
                        criteriaBuilder.conjunction() :
                        root.get("level").in(levels);
    }

    public static Specification<Word> hasCategories(List<Category> categories) {
        return (root, query, criteriaBuilder) -> {
            if (categories == null || categories.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            Join<Word, WordSense> wordSenseJoin = root.join("wordSenses");
            Join<WordSense, WordSenseCategory> categoryJoin = wordSenseJoin.join("categories");
            return categoryJoin.get("category").in(categories);
        };
    }

    public static Specification<Word> hasPersons(List<Person> persons) {
        return (root, query, criteriaBuilder) -> {
            if (persons == null || persons.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            Join<Word, WordSense> wordSenseJoin = root.join("wordSenses");
            return wordSenseJoin.get("person").in(persons);
        };
    }

    public static Specification<Word> hasGenders(List<Gender> genders) {
        return (root, query, criteriaBuilder) -> {
            if (genders == null || genders.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            Join<Word, WordSense> wordSenseJoin = root.join("wordSenses");
            return wordSenseJoin.get("gender").in(genders);
        };
    }

    public static Specification<Word> hasNumbers(List<Number> numbers) {
        return (root, query, criteriaBuilder) -> {
            if (numbers == null || numbers.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            Join<Word, WordSense> wordSenseJoin = root.join("wordSenses");
            return wordSenseJoin.get("number").in(numbers);
        };
    }

    public static Specification<Word> hasTenses(List<Tense> tenses) {
        return (root, query, criteriaBuilder) -> {
            if (tenses == null || tenses.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            Join<Word, WordSense> wordSenseJoin = root.join("wordSenses");
            return wordSenseJoin.get("tense").in(tenses);
        };
    }

    public static Specification<Word> hasMinAccuracy(double minAccuracy) {
        return (root, query, criteriaBuilder) -> {
            if (minAccuracy <= 0) {
                return criteriaBuilder.conjunction();
            }

            Subquery<Double> accuracySubquery = query.subquery(Double.class);
            Root<UserHistorial> historialRoot = accuracySubquery.from(UserHistorial.class);

            Join<UserHistorial, DeckWordPhraseTranslation> dwptJoin =
                    historialRoot.join("deckWordPhraseTranslation");
            Join<DeckWordPhraseTranslation, WordPhraseTranslation> wptJoin =
                    dwptJoin.join("wordPhraseTranslation");
            Join<WordPhraseTranslation, WordTranslation> wtJoin =
                    wptJoin.join("wordTranslation");
            Join<WordTranslation, WordSense> wsFrJoin =
                    wtJoin.join("wordSenseFr");
            Join<WordSense, Word> wordJoin =
                    wsFrJoin.join("word");

            Join<UserHistorial, Success> successJoin =
                    historialRoot.join("success");

            accuracySubquery.select(criteriaBuilder.avg(successJoin.get("score")))
                    .where(criteriaBuilder.and(
                            criteriaBuilder.equal(wordJoin.get("id"), root.get("id")),
                            criteriaBuilder.isNotNull(successJoin.get("score"))
                    ));

            double minScore = minAccuracy / 100.0;

            return criteriaBuilder.greaterThanOrEqualTo(
                    accuracySubquery,
                    minScore
            );
        };
    }

    public static Specification<Word> hasMaxAccuracy(double maxAccuracy) {
        return (root, query, criteriaBuilder) -> {
            if (maxAccuracy <= 0) {
                return criteriaBuilder.conjunction();
            }

            Subquery<Double> accuracySubquery = query.subquery(Double.class);
            Root<UserHistorial> historialRoot = accuracySubquery.from(UserHistorial.class);

            Join<UserHistorial, DeckWordPhraseTranslation> dwptJoin =
                    historialRoot.join("deckWordPhraseTranslation");
            Join<DeckWordPhraseTranslation, WordPhraseTranslation> wptJoin =
                    dwptJoin.join("wordPhraseTranslation");
            Join<WordPhraseTranslation, WordTranslation> wtJoin =
                    wptJoin.join("wordTranslation");
            Join<WordTranslation, WordSense> wsFrJoin =
                    wtJoin.join("wordSenseFr");
            Join<WordSense, Word> wordJoin =
                    wsFrJoin.join("word");

            Join<UserHistorial, Success> successJoin =
                    historialRoot.join("success");

            accuracySubquery.select(criteriaBuilder.avg(successJoin.get("score")))
                    .where(criteriaBuilder.and(
                            criteriaBuilder.equal(wordJoin.get("id"), root.get("id")),
                            criteriaBuilder.isNotNull(successJoin.get("score"))
                    ));

            double maxScore = maxAccuracy / 100.0;

            return criteriaBuilder.lessThanOrEqualTo(
                    accuracySubquery,
                    maxScore
            );
        };
    }

    public static Specification<Word> hasAccuracyBetween(double minAccuracy, double maxAccuracy) {
        return Specification.where(hasMinAccuracy(minAccuracy))
                .and(hasMaxAccuracy(maxAccuracy));
    }

    public static Specification<Word> withFilters(WordFilterRequestDTO filter) {
        return Specification.where(hasTextFiltered(filter.getTextFiltered()))
                .and(hasPartSpeeches(filter.getPartSpeeches()))
                .and(hasLevels(filter.getLevels()))
                .and(hasCategories(filter.getCategories()))
                .and(hasPersons(filter.getPersons()))
                .and(hasGenders(filter.getGenders()))
                .and(hasNumbers(filter.getNumbers()))
                .and(hasTenses(filter.getTenses()))
                .and(hasAccuracyBetween(filter.getMinAccuracy(), filter.getMaxAccuracy()));
    }
}
