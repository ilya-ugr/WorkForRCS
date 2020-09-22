package nalogRf.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class NalogTable {
    private String row;
    private String col;
    private long value;
}
