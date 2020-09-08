package uk.nhs.digital.apispecs.swagger.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import uk.nhs.digital.apispecs.swagger.request.bodyextractor.ToPrettyJsonStringDeserializer;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ParamExample {

    private String summary;

    private String description;

    @JsonDeserialize(using = ToPrettyJsonStringDeserializer.class)
    private String value;

    public String getSummary() {
        return summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    @Override public String toString() {
        return new ToStringBuilder(this)
            .append("summary", summary)
            .append("description", description)
            .append("value", value)
            .toString();
    }

    @Override public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ParamExample that = (ParamExample) o;

        return new EqualsBuilder()
            .append(summary, that.summary)
            .append(description, that.description)
            .append(value, that.value)
            .isEquals();
    }

    @Override public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(summary)
            .append(description)
            .append(value)
            .toHashCode();
    }
}
