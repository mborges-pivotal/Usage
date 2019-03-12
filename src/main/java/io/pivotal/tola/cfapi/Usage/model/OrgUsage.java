package io.pivotal.tola.cfapi.usage.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrgUsage {

    private String orgGuid;
    private int year;
    private int quarter;

    private int totalApps;
    private int totalAis;
    private int totalMbPerAis;
    private long totalDurationInSecs;

    public void addAiCount(int c) {
        totalAis += c;
    }

    public void addMb(int c) {
        totalMbPerAis += c;
    }

    public void addlDurationInSecs(long c) {
        totalDurationInSecs += c;
    }

}