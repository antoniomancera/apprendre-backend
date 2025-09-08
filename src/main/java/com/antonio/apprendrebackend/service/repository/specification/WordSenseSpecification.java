package com.antonio.apprendrebackend.service.repository.specification;

import com.antonio.apprendrebackend.service.dto.WordSenseFilterRequestDTO;
import com.antonio.apprendrebackend.service.model.Number;
import com.antonio.apprendrebackend.service.model.*;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class WordSenseSpecification {
    private WordSenseSpecification() {
    }

    public static Specification<WordSense> hasCategories(List<Category> categories) {
        return (root, query, criteriaBuilder) -> {
            if (categories == null || categories.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            Join<WordSense, WordSenseCategory> categoryJoin = root.join("categories");
            return categoryJoin.get("category").in(categories);
        };
    }

    public static Specification<WordSense> hasPersons(List<Person> persons) {
        return (root, query, criteriaBuilder) -> {
            if (persons == null || persons.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            Join<Word, WordSense> wordSenseJoin = root.join("wordSenses");
            return wordSenseJoin.get("person").in(persons);
        };
    }

    public static Specification<WordSense> hasGenders(List<Gender> genders) {
        return (root, query, criteriaBuilder) -> {
            if (genders == null || genders.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            Join<Word, WordSense> wordSenseJoin = root.join("wordSenses");
            return wordSenseJoin.get("gender").in(genders);
        };
    }

    public static Specification<WordSense> hasNumbers(List<Number> numbers) {
        return (root, query, criteriaBuilder) -> {
            if (numbers == null || numbers.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            Join<Word, WordSense> wordSenseJoin = root.join("wordSenses");
            return wordSenseJoin.get("number").in(numbers);
        };
    }

    public static Specification<WordSense> hasTenses(List<Tense> tenses) {
        return (root, query, criteriaBuilder) -> {
            if (tenses == null || tenses.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            Join<Word, WordSense> wordSenseJoin = root.join("wordSenses");
            return wordSenseJoin.get("tense").in(tenses);
        };
    }

    public static Specification<WordSense> hasMinAccuracy(double minAccuracy) {
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


            Join<UserHistorial, Success> successJoin =
                    historialRoot.join("success");

            accuracySubquery.select(criteriaBuilder.avg(successJoin.get("score")))
                    .where(criteriaBuilder.and(
                            criteriaBuilder.equal(wsFrJoin.get("id"), root.get("id")),
                            criteriaBuilder.isNotNull(successJoin.get("score"))
                    ));

            double minScore = minAccuracy / 100.0;

            return criteriaBuilder.greaterThanOrEqualTo(
                    accuracySubquery,
                    minScore
            );
        };
    }

    public static Specification<WordSense> hasMaxAccuracy(double maxAccuracy) {
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

            Join<UserHistorial, Success> successJoin =
                    historialRoot.join("success");

            accuracySubquery.select(criteriaBuilder.avg(successJoin.get("score")))
                    .where(criteriaBuilder.and(
                            criteriaBuilder.equal(wsFrJoin.get("id"), root.get("id")),
                            criteriaBuilder.isNotNull(successJoin.get("score"))
                    ));

            double maxScore = maxAccuracy / 100.0;

            return criteriaBuilder.lessThanOrEqualTo(
                    accuracySubquery,
                    maxScore
            );
        };
    }


    public static Specification<WordSense> hasAccuracyBetween(double minAccuracy, double maxAccuracy) {
        return Specification.where(hasMinAccuracy(minAccuracy))
                .and(hasMaxAccuracy(maxAccuracy));
    }

    public static Specification<WordSense> withFilters(WordSenseFilterRequestDTO filter) {
        return Specification.where(hasCategories(filter.getCategories()))
                .and(hasPersons(filter.getPersons()))
                .and(hasGenders(filter.getGenders()))
                .and(hasNumbers(filter.getNumbers()))
                .and(hasTenses(filter.getTenses()))
                .and(hasAccuracyBetween(filter.getMinAccuracy(), filter.getMaxAccuracy()));
    }
}
