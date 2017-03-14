package com.mailstorage.core.artifact.subject;

import com.mailstorage.core.artifact.BaseArtifactExtractor;
import com.mailstorage.data.mail.entities.Mail;
import com.mailstorage.data.mail.entities.artifact.SubjectArtifact;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author metal
 */
public class SubjectArtifactExtractor implements BaseArtifactExtractor<SubjectArtifact> {
    private final List<String> fraudEmails;
    private final List<String> suspiciousWords;

    public SubjectArtifactExtractor(
            String commaSeparatedFraudEmails,
            String commaSeparatedSuspiciousWords)
    {
        this.fraudEmails = splitByComma(commaSeparatedFraudEmails);
        this.suspiciousWords = splitByComma(commaSeparatedSuspiciousWords);
    }

    @Override
    public SubjectArtifact extract(Mail mail) {
        return new SubjectArtifact(
                mail.getTimestamp(),
                searchWords(mail.getSubject(), suspiciousWords),
                mail.getTo().stream()
                        .anyMatch(to -> {
                            List<String> matchedFraudEmails = searchWords(to, fraudEmails);
                            return matchedFraudEmails != null && !matchedFraudEmails.isEmpty();
                        }));
    }

    private List<String> searchWords(String text, List<String> words) {
        if (StringUtils.isNotEmpty(text)) {
            List<String> result = words.stream().filter(text::contains).collect(Collectors.toList());
            return result.isEmpty() ? null : result;
        }
        return null;
    }

    private List<String> splitByComma(String commaSeparatedFraudEmails) {
        return Arrays.asList(StringUtils.split(commaSeparatedFraudEmails, ", "));
    }
}
